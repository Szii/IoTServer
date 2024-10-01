/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Controllers;

import com.irrigation.Messages.MessageFormat.Code;
import com.irrigation.Messages.MessageFormat.CredentialsRequest;
import com.irrigation.Messages.MessageFormat.Payload;
import com.irrigation.iotserver.Security.PasswordHasher;
import com.irrigation.iotserver.Security.TokenGenerator;
import com.irrigation.iotserver.Services.DataAccess;
import java.sql.SQLException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author brune
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final DataAccess databaseManager;
    public UserController(DataAccess databaseManager){
       this.databaseManager =  databaseManager;
    }
    
       @PostMapping("/login")
    public Payload confirmLogin(@RequestBody CredentialsRequest loginRequest) {
        try {
            System.out.println("Login attempt");
            if(PasswordHasher.compareIfPassowrdMatchesWithStoredHash(loginRequest.getPassword(),
                databaseManager.getPasswordQuery(loginRequest.getUsername()))){
                String token = TokenGenerator.generateToken();
                databaseManager.setTokenQuery(loginRequest.getUsername(), token);
                return new Payload.PayloadBuilder()
                        .setCode(Code.SUCCESS)
                        .setToken(token)
                        .build();
            }
            else{
                return new Payload.PayloadBuilder()
                        .setCode(Code.FAILURE)
                        .build();
            }
        } catch (SQLException ex) {
                     return new Payload.PayloadBuilder()
                        .setCode(Code.FAILURE)
                        .build();
        }
   }
    
     @PostMapping("/register")
    public Payload confirmRegister(@RequestBody CredentialsRequest registerRequest) {
            if(databaseManager.addUserQuery(registerRequest.getUsername(),registerRequest.getPassword())){
                return new Payload.PayloadBuilder()
               .setCode(Code.SUCCESS)
               .build();  
            }
            else{
                return new Payload.PayloadBuilder()
                .setCode(Code.FAILURE)
                .build(); 
            }
    }
}
