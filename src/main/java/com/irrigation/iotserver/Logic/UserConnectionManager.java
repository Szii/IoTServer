/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Logic;

import com.irrigation.iotserver.Data.DataAccess;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author brune
 */
public class UserConnectionManager extends Thread {
    
    DataAccess databaseManager;
    ArrayList<UserConnection> connections;
    
    ServerSocket serverSocket;
    int portNumber = 4444;
   
    

    public UserConnectionManager(DataAccess databaseManager) {
        this.databaseManager = databaseManager;
    }
    
    @Override
    public void run(){
        try {
            //when client connects, new thread is created for him
            //thread process all user requests and responses
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException ex) {
            Logger.getLogger(UserConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (true){   
            try {
                Socket clienConnectiuonSocket = serverSocket.accept();
                //Waiting for connection will be there
                UserConnection userConnection = new UserConnection(clienConnectiuonSocket,databaseManager);
                connections.add(userConnection);
                userConnection.start();
            } catch (IOException ex) {
                Logger.getLogger(UserConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void removeConnection(UserConnection userConnection){
        connections.remove(userConnection);
    }
    
    
}
