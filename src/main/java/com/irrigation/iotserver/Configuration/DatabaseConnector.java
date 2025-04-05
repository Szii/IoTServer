/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Configuration;

import com.irrigation.iotserver.Configuration.DatabaseConfig;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;
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

          final String databaseURL = "jdbc:mysql://" +conf.address+ "/diplomova_prace_db?allowMultiQueries=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
          final String user = conf.username;
          final String password = conf.password;

         return DatabasePool.getConnection(databaseURL, user, password);
    }
    
    
    public class DatabasePool {
        private static BasicDataSource dataSource;

        public static Connection getConnection(String url, String user, String password) throws SQLException {
            configure(url, user, password);
            return dataSource.getConnection();
        }
        
        public static void configure(String url, String user, String password){
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setUrl(url);
            dataSource.setUsername(user);
            dataSource.setPassword(password);

            dataSource.setTestOnBorrow(true);
            dataSource.setValidationQuery("SELECT 1");
            dataSource.setTestWhileIdle(true);
            dataSource.setTimeBetweenEvictionRunsMillis(60000); 
            dataSource.setMinEvictableIdleTimeMillis(300000);   
        }
    
    } 
}
