/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author brune
 */
public class ParsedMessage {
    
    private int humidity = 0;
    private int temperature = 0;
    private String deviceID = "";
    
    @JsonCreator
    public ParsedMessage(@JsonProperty("deviceID") String deviceID, @JsonProperty("temperature") int temperature, @JsonProperty("humidity") int humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.deviceID = deviceID;
    }
    
    public int getHumidity() {
        return humidity;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }
    
    
    
}
