/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Logic;

import com.irrigation.iotserver.Data.DataAccess;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author brune
 */
public class UserConnectionManager extends Thread {
    
    DataAccess databaseManager;
    ArrayList<UserConnection> connections;
    

    public UserConnectionManager(DataAccess databaseManager) {
        this.databaseManager = databaseManager;
    }
    
    @Override
    public void start(){
        //when client connects, new thread is created for him
        //thread process all user requests and responses
        while (true){   
            //Waiting for connection will be there
            UserConnection userConnection = new UserConnection(databaseManager);
            connections.add(userConnection);
            userConnection.start();
        }
    }
    
    public void removeConnection(UserConnection userConnection){
        connections.remove(userConnection);
    }
    
    
}
