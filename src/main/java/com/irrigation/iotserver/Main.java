/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver;

import java.util.Collections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 *
 * @author brune
 */
@SpringBootApplication
public class Main extends SpringBootServletInitializer{

    public static void main(String [] args){ 
      new Program();
               SpringApplication app = new SpringApplication(Main.class);
        app.setDefaultProperties(Collections
          .singletonMap("server.port", "8083"));
        app.run(args);
    }    
}
