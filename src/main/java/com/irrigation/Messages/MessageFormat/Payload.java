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
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author brune
 */
@Schema(
    name = "Payload",
    description = "Main wrapper for response"
)
@JsonDeserialize(builder = Payload.PayloadBuilder.class)
public class Payload implements Serializable {

    @JsonProperty("content")
    @Schema(
        description = "Array containg a content of response which do not inculde devices. Used for measurement data",
        example = """
                  {
                    30,
                    25,
                    21
                  }
                  """
    )
    private List<String> content;

    @JsonProperty("type")
    @Schema(
        description = "Message type indicating type of operation",
        example = "CONFIRM_LOGIN"
    )
    private MessageType type;

    @JsonProperty("code")
    @Schema(
        description = "Indicates success or failure of the operation. Can hold value of SUCCESS or FAILURE.",
        example = "SUCCESS"
    )
    private Code code;

    @JsonProperty("token")
    @Schema(
        description = "Auth token returned when logging sucessfully",
        example = "auth-token-example"
    )
    private String token;

    @JsonProperty("data")
    @Schema(
        description = "Optional container for device objects if retrieved through device related endpoints."
    )
    private ArrayList<Device> data;

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

    
    public ArrayList<Device> getData(){
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
        
        public PayloadBuilder setData(ArrayList<Device> data){
            this.data = data;
            return this;
        }
        
        public Payload build(){
            return new Payload(this);
        }
    }
}
