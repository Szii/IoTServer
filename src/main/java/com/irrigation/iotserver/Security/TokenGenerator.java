/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Security;

import java.nio.charset.Charset;
import java.util.Random;

/**
 *
 * @author brune
 */
public class TokenGenerator {
    
    public static String generateToken(){
        byte[] array = new byte[30]; 
        new Random().nextBytes(array);
        return  new String(array, Charset.forName("UTF-8"));
    }
    
}
