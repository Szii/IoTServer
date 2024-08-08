/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.Messages;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author brune
 */
public class Payload implements Serializable {
    List<String> content;
    MessageType type;
    Code code;
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public Payload(){}

    private Payload(PayloadBuilder builder) {
        this.content = builder.content;
        this.type = builder.type;
        this.code = builder.code;
        this.token = builder.token;
    }
    
    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }
    
    public static class PayloadBuilder{
        private List<String> content;
        private MessageType type;
        private Code code;
        private String token;
   
    
        public PayloadBuilder(Code code) {
            this.code = code;
        }
        
        public PayloadBuilder setToken(String token){
            this.token = token;
            return this;
        }
        
        public PayloadBuilder setCode(Code code){
            this.code = code;
            return this;
        }
        
        public PayloadBuilder setContent(List<String> content){
            this.content = content;
            return this;
        }
        public PayloadBuilder setType(MessageType type){
            this.type = type;
            return this;
        }
        
        public Payload build(){
            return new Payload(this);
        }
    }
}
