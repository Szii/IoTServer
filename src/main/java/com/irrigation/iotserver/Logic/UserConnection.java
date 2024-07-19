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
public class UserConnection {
    DataAccess databaseManager;

    public UserConnection(DataAccess databaseManager) {
        this.databaseManager = databaseManager;
    }
    
    
}
