/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.Messages.MessageFormat;

import java.io.Serializable;

/**
 *
 * @author brune
 */
public class UserRequest{
    String token;

    public UserRequest(String token) {
        this.token = token;
    }
    
     public UserRequest(){};


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
}
