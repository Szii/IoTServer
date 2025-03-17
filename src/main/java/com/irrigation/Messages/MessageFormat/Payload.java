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
import com.irrigation.Messages.MessageData.Measurement;
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
    
    
    @JsonProperty("measurements")
    @Schema(
        description = "Optional array containg measurements  if retrieved through measurements related endpoints"
    )
    private List<Measurement> measurements;

    @JsonProperty("groups")
    @Schema(
        description = "Optional array containg groups  if retrieved through groups related endpoints",
        example = """
                  {
                    group1,
                    group2
                  }
                  """
    )
    private List<String> groups;

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

    @JsonProperty("devices")
    @Schema(
        description = "Optional container for device objects if retrieved through device related endpoints."
    )
    private List<Device> devices;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public Payload(){}

    private Payload(PayloadBuilder builder) {
        this.groups = builder.groups;
        this.type = builder.type;
        this.code = builder.code;
        this.token =builder.token;
        this.devices = builder.devices;
        this.measurements = builder.measurements;
    }
    
    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public MessageType getType() {
        return type;
    }

    public Code getCode() {
        return code;
    }
    
    public List<Measurement> getMeasurements(){
        return measurements;
    }

    
    public List<Device> getDevices(){
        return devices;
    }
    
    @JsonPOJOBuilder(withPrefix = "set")
    public static class PayloadBuilder{
        private List<String> groups;
        private MessageType type;
        private Code code;
        private String token;
        @JsonProperty("devices")
        private List<Device> devices;
        @JsonProperty("measurements")
        private List<Measurement> measurements;
   
    
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
        
       public PayloadBuilder setMeasurements(List<Measurement> measurements){
            this.measurements = measurements;
            return this;
       }
        
        public PayloadBuilder setGroups(List<String> groups){
            this.groups = groups;
            return this;
        }
        public PayloadBuilder setType(MessageType type){
            this.type = type;
            return this;
        }
        
        public PayloadBuilder setDevices(ArrayList<Device> devices){
            this.devices = devices;
            return this;
        }
        
        public Payload build(){
            return new Payload(this);
        }
    }
}
