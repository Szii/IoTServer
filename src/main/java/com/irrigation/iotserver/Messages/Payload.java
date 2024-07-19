/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Messages;

/**
 *
 * @author brune
 */
public class Payload {
    String content;
    String type;
    
    public Payload(){}

    public Payload(String content, String type) {
        this.content = content;
        this.type = type;
    }
    
    

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}
