/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author brune
 */
public class DatabaseConnector {
    
    Connection connection;
    
    //Connect to DB
    public DatabaseConnector() throws ClassNotFoundException, SQLException{
           connect();
    }
    
    public void connect() throws ClassNotFoundException, SQLException{
 

        // Specify the connection URL, username, and password
          final String databaseURL = "jdbc:mysql://localhost/diplomova_prace_db?allowMultiQueries=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
          final String user = "root";
          final String password = "root";

        // Create the connection
         connection = DriverManager.getConnection(databaseURL, user, password);
    }

    public Connection getConnection() {
        return connection;
    }
    
    
}
