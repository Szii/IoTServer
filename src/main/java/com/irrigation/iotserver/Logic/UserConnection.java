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
public class UserConnection extends Thread{
    DataAccess databaseManager;

    public UserConnection(DataAccess databaseManager) {
        this.databaseManager = databaseManager;
    }
    
    @Override
    public void start(){
        
        //Connection estabilished, wait for messages and do proper opeation
        while (true){
            //Waiting for message will be there
            
        }
    }
    
    
}
