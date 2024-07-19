/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Logic;

import com.irrigation.iotserver.Data.DataAccess;
import com.irrigation.iotserver.Messages.Payload;
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
        
        try {
            objectInput = new ObjectInputStream(socket.getInputStream());
            objectOutput = new ObjectOutputStream(socket.getOutputStream());
            //Connection estabilished, wait for messages and do proper opeation
            while (true){
                Payload message;
                message = ((Payload)objectInput.readObject());   //Waiting for message will be there
              
                //reacting to message
            }
        } catch (IOException ex) {
            Logger.getLogger(UserConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
