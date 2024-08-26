/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Security;

import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

/**
 *
 * @author brune
 */
public class TokenGenerator {
    
    public static String generateToken(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[30];
        secureRandom.nextBytes(randomBytes);
        String base64Token = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        return base64Token;
    }
    
}
