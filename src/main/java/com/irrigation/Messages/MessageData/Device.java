/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.Messages.MessageData;

import java.io.Serializable;

/**
 *
 * @author brune
 */
public class Device implements Serializable  {
    
    private final String ID;
    private final String nickname;
    private final String lastMeasuredValue;
    private final String group;
    private final String irrigationTime;
    private final String threshold;
    private final String date;

    public String getDate() {
        return date;
    }
    
    
    private Device(DeviceBuilder builder){
        this.ID = builder.ID;
        this.nickname =  builder.nickname;
        this.lastMeasuredValue = builder.lastMeasuredValue;
        this.group = builder.group;
        this.irrigationTime = builder.irrigationTime;
        this.threshold = builder.threshold;
        this.date = builder.date;
    }

    public String getID() {
        return ID;
    }

    public String getNickname() {
        return nickname;
    }

    public String getLastMeasuredValue() {
        return lastMeasuredValue;
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
    
    
    
   public static class DeviceBuilder{
       
    private String ID;
    private String nickname;
    private String lastMeasuredValue;
    private String group;
    private String irrigationTime;
    private String threshold;
    private String date;

        public DeviceBuilder setID(String ID) {
            this.ID = ID;
            return this;
        }

        public DeviceBuilder setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public DeviceBuilder setLastMeasuredValue(String lastMeasuredValue) {
            this.lastMeasuredValue = lastMeasuredValue;
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

        public DeviceBuilder setDate(String date) {
            this.date = date;
            return this;
        }

    public Device build(){
        return new Device(this);
    }
       
       
   }
    
    
       
    
}
