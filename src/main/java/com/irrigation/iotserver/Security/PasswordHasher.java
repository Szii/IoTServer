/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Security;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author brune
 */
public class PasswordHasher {
    
    /**
     * One-way hash function to hash a password
     * @param password
     * @return hashed password
     */
    public static String getHash(String password){
         return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    
    public static boolean compareIfPassowrdMatchesWithStoredHash(String password, String hash){
        try{
           return BCrypt.checkpw(password, hash); 
        }
        catch(IllegalArgumentException e) {
            System.out.println(e);
            return false;
        }
        
  
    }
}
