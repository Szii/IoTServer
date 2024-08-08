/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Logic;

import com.irrigation.iotserver.Data.DataAccess;
import com.irrigation.Messages.Code;
import com.irrigation.Messages.MessageType;
import com.irrigation.Messages.Payload;
import com.irrigation.iotserver.Security.PasswordHasher;
import com.irrigation.iotserver.Security.TokenGenerator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author brune
 */
public class UserConnection extends Thread{
    DataAccess databaseManager;
    Socket socket;
    ObjectInputStream objectInput;
    ObjectOutputStream objectOutput;
    String userToken = "";
    

    public UserConnection(Socket socket, DataAccess databaseManager) {
        this.databaseManager = databaseManager;
        this.socket = socket;
    }
    
    @Override
    public void run(){
        System.out.println("user connected, thread created");
        try {
            objectInput = new ObjectInputStream(socket.getInputStream());
            objectOutput = new ObjectOutputStream(socket.getOutputStream());
            //Connection estabilished, wait for messages and do proper opeation
            while (true){
                System.out.println("waiting for message from user");
                Payload message;
                message = ((Payload)objectInput.readObject());   //Blocking method
                
                if(message.getCode().equals(Code.FAILURE) || !message.getToken().equals(userToken)){
                    this.interrupt(); //end the conection
                }
                processMessage(message); //reacting to message
                
            }
        } catch (IOException ex) {
            Logger.getLogger(UserConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void processMessage(Payload message) throws SQLException{
        switch(message.getType()){
            case PING:
                System.out.println("Ping arrived");
                sendMessage(new Payload());
            case CONFIRM_LOGIN:
                System.out.println("Login attempt");
                if(PasswordHasher.compareIfPassowrdMatchesWithStoredHash(
                        message.getContent().get(1), 
                        databaseManager.getPasswordQuery(message.getContent().get(0)))){
                    userToken = TokenGenerator.generateToken();
                    sendLoginConfirmationSuccess();
                    System.out.println(userToken);

                }
                else{
                    sendCodeAnswerToDatabaseRequest(false, message.getType());
                }
                break;
            case GET_USER:
                System.out.println("Does user exist?");
                sendCodeAnswerToDatabaseRequest(databaseManager.getUserQuery(
                        message.getContent().get(0)),message.getType());
                break;
            case ADD_USER:
                System.out.println("Adding user");
                sendCodeAnswerToDatabaseRequest(databaseManager.addUserQuery(
                        message.getContent().get(0),
                        PasswordHasher.getHash(message.getContent().get(1))), message.getType()); 
                break;
                
        }
    }
    
    
    private void sendMessage(Payload message){
        try {
            objectOutput.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(UserConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void sendLoginConfirmationSuccess(){
        sendMessage(new Payload.PayloadBuilder(Code.SUCCESS)
         .setToken(userToken)
         .setType(MessageType.CONFIRM_LOGIN)
         .build());
        
    }
    
    private void sendCodeAnswerToDatabaseRequest(boolean isRequestValid, MessageType requestType){
        if(isRequestValid){
                   System.out.println("Sending success");
                    sendMessage(new Payload.PayloadBuilder(Code.SUCCESS)
                    .setType(requestType)
                    .build());
                }
                else{
                    System.out.println("Sending failure");
                    sendMessage(new Payload.PayloadBuilder(Code.FAILURE)
                    .setType(requestType)
                    .build());
                }
        
    }
    
    
   
    
    
    
}
