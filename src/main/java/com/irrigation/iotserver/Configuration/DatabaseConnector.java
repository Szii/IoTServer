/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Configuration;

import com.irrigation.iotserver.Configuration.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 *
 * @author brune
 */

@Component
public class DatabaseConnector {
    @Autowired
    DatabaseConfig conf;
    
    //Connect to DB
    public DatabaseConnector() throws ClassNotFoundException, SQLException{
    }
    
    @Bean
    public Connection connect() throws ClassNotFoundException, SQLException{
         System.out.println(conf.username);
         System.out.println(conf.password);
         System.out.println(conf.address);

        // Specify the connection URL, username, and password
          final String databaseURL = "jdbc:mysql://" +conf.address+ "/diplomova_prace_db?allowMultiQueries=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
          final String user = conf.username;
          final String password = conf.password;

         return DriverManager.getConnection(databaseURL, user, password);
    }
    
    
}
