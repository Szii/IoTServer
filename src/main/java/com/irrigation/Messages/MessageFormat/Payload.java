/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.Messages.MessageFormat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.irrigation.Messages.MessageData.Device;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author brune
 */
@JsonDeserialize(builder = Payload.PayloadBuilder.class)
public class Payload implements Serializable {
    @JsonProperty("content")
    List<String> content;
    @JsonProperty("type")
    MessageType type;
    @JsonProperty("code")
    Code code;
    @JsonProperty("token")
    String token;
    @JsonProperty("data")
    ArrayList<Device> data;

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
        this.token =builder.token;
        this.data = builder.data;
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

    public Code getCode() {
        return code;
    }

    
    public ArrayList<Device> getObject(){
        return data;
    }
    
    @JsonPOJOBuilder(withPrefix = "set")
    public static class PayloadBuilder{
        private List<String> content;
        private MessageType type;
        private Code code;
        private String token;
        @JsonProperty("data")
        private ArrayList<Device> data;
   
    
        public PayloadBuilder() {
        }
        
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
        
        public PayloadBuilder setObject(ArrayList<Device> data){
            this.data = data;
            return this;
        }
        
        public Payload build(){
            return new Payload(this);
        }
    }
}
