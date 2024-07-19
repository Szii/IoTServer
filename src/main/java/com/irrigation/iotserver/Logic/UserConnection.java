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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
                message = ((Payload)objectInput.readObject());   //Waiting for message will be there
                if(message.getCode().equals(Code.FAILURE)){
                    this.interrupt();
                }
                processMessage(message);
                //reacting to message
            }
        } catch (IOException ex) {
            Logger.getLogger(UserConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void processMessage(Payload message){
        switch(message.getType()){
            case PING:
                System.out.println("Ping arrived");
                sendMessage(new Payload());
        }
    }
    
    private void sendMessage(Payload message){
        try {
            message.setCode(Code.SUCCESS);
            objectOutput.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(UserConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
