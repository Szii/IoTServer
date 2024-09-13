/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver;

import com.irrigation.iotserver.Data.DataAccess;
import com.irrigation.iotserver.Logic.OutboundManager;
import com.irrigation.iotserver.Data.DatabaseConnector;
import com.irrigation.iotserver.Data.DatabaseManager;
import com.irrigation.iotserver.Logic.UserConnectionManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author brune
 */
public class Program {
    static DataAccess databaseManager;
    OutboundManager dataConnector;
    public Program(){
        prepareConnectionToDatabase();
        prepareConnectionToLoRaServer();
        UserConnectionManager userConnectionManager = new UserConnectionManager(databaseManager);
        userConnectionManager.start();
        dataConnector.sendMessage("11111111", "test");
    } 
    
    public static DataAccess getDatabaseManager(){
        return databaseManager;
    }
    
    private void prepareConnectionToLoRaServer(){
        dataConnector = new OutboundManager(databaseManager);
        dataConnector.start();
    }
    
    private void prepareConnectionToDatabase(){
         try {
            databaseManager = new DatabaseManager(new DatabaseConnector().connect());
            
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
