/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.Messages.MessageData;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.io.Serializable;

/**
 *
 * @author brune
 */
@JsonDeserialize(builder = Device.DeviceBuilder.class)
public class Device implements Serializable  {
    
    private final String ID;
    private final String nickname;
    private final String group;
    private final String irrigationTime;
    private final String threshold;
    private final String humidity_date;
    private final String temperature_date;
    private final String temperature_value;
    private final String humidity_value;

    public String getHumidityDate() {
        return humidity_date;
    }
    
      public String getTemperatureDate() {
        return temperature_date;
    }
    
     
    private Device(DeviceBuilder builder){
        this.ID = builder.ID;
        this.nickname =  builder.nickname;
        this.humidity_value = builder.humidity_value;
        this.temperature_value = builder.temperature_value;
        this.group = builder.group;
        this.irrigationTime = builder.irrigationTime;
        this.threshold = builder.threshold;
        this.humidity_date = builder.humidity_date;
        this.temperature_date = builder.temperature_date;
    }

    public String getID() {
        return ID;
    }

    public String getNickname() {
        return nickname;
    }

    public String getHumidityValue() {
        return humidity_value;
    }
    
      public String getTemperatureValue() {
        return temperature_value;
    }


    public String getGroup() {
        return group;
    }

    public String getIrrigationTime() {
        return irrigationTime;
    }

    public String getThreshold() {
        return threshold;
    }
    
    @Override
    public String toString(){
        return nickname == null ?  ID :  nickname;
    }
    
    
   @JsonPOJOBuilder(withPrefix = "set")
   public static class DeviceBuilder{
       
    private String ID;
    private String nickname;
    private String group;
    private String irrigationTime;
    private String threshold;
    private String humidity_date;
    private String temperature_date;
    private String temperature_value;
    private String humidity_value;

        public DeviceBuilder setID(String ID) {
            this.ID = ID;
            return this;
        }

        public DeviceBuilder setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public DeviceBuilder setHumidityValue(String lastMeasuredValue) {
            this.humidity_value = lastMeasuredValue;
            return this;
        }
        
        public DeviceBuilder setTemperatureValue(String lastMeasuredValue) {
            this.temperature_value = lastMeasuredValue;
            return this;
        }

        public DeviceBuilder setGroup(String group) {
            this.group = group;
            return this;
        }

        public DeviceBuilder setIrrigationTime(String irrigationTime) {
            this.irrigationTime = irrigationTime;
            return this;
        }

        public DeviceBuilder setThreshold(String threshold) {
            this.threshold = threshold;
            return this;
        }

        public DeviceBuilder setHumidityDate(String date) {
            this.humidity_date = date;
            return this;
        }
        
        public DeviceBuilder setTemperatureDate(String date) {
            this.temperature_date = date;
            return this;
        }

    public Device build(){
        return new Device(this);
    }
       
       
   }
    
    
       
    
}

