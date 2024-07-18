/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver;

import com.irrigation.iotserver.Data.DataAccess;
import com.irrigation.iotserver.Data.DataConnector;
import com.irrigation.iotserver.Data.DatabaseConnector;
import com.irrigation.iotserver.Data.DatabaseManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author brune
 */
public class Program {
    DataAccess databaseManager;
    DataConnector dataConnector;
    public Program(){
        prepareConnectionToLoRaServer();
        prepareConnectionToDatabase();
        try {
            addUser();
        } catch (SQLException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }

    } 
    
    private void prepareConnectionToLoRaServer(){
        dataConnector = new DataConnector();
    }
    
    private void prepareConnectionToDatabase(){
         try {
            databaseManager = new DatabaseManager(new DatabaseConnector().getConnection());
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void addUser() throws SQLException{
        databaseManager.addUserQuery("test", "12345");
    }
    
    
}
