/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Logic;

import com.irrigation.iotserver.Data.DataAccess;

/**
 *
 * @author brune
 */
public class UserConnectionManager extends Thread {
    
    DataAccess databaseManager;

    public UserConnectionManager(DataAccess databaseManager) {
        this.databaseManager = databaseManager;
    }
    
    @Override
    public void start(){
        //whewn client connects, new thread is created for him
        //thread process all user requests and responses
        while (true){       
            UserConnection userConnection = new UserConnection(databaseManager);
        }
    }
    
    
}
