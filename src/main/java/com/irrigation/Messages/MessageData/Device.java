/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.Messages.MessageData;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;

/**
 *
 * @author brune
 */

@JsonDeserialize(builder = Device.DeviceBuilder.class)
@Schema(name = "Device", description = "Represents a device within the system, including measurement details.")
public class Device implements Serializable {

    @Schema(description = "Unique hardware or logical identifier of the device.", example = "otaatest")
    private final String ID;

    @Schema(description = "A user-defined alias for the device.", example = "Macešky_1")
    private final String nickname;

    @Schema(description = "Group or category to which this device belongs.", example = "Sklenik_venku")
    private final String group;

    @Schema(description = "Configured irrigation time for this device in seconds.", example = "3")
    private final String irrigationTime;

    @Schema(description = "Configured threshold for moisture", example = "30")
    private final String threshold;

    @Schema(description = "Timestamp of last humidity reading.", example = "2025-01-01 10:15:30")
    private final String humidity_date;

    @Schema(description = "Timestamp of last temperature reading.", example = "2025-01-17 10:15:00")
    private final String temperature_date;

    @Schema(description = "Last measured temperature value.", example = "25")
    private final String temperature_value;

    @Schema(description = "Last measured humidity value.", example = "45")
    private final String humidity_value;

    private Device(DeviceBuilder builder){
        this.ID = builder.ID;
        this.nickname = builder.nickname;
        this.group = builder.group;
        this.irrigationTime = builder.irrigationTime;
        this.threshold = builder.threshold;
        this.humidity_date = builder.humidity_date;
        this.temperature_date = builder.temperature_date;
        this.temperature_value = builder.temperature_value;
        this.humidity_value = builder.humidity_value;
    }

    public String getID() {
        return ID;
    }

    public String getNickname() {
        return nickname;
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

    public String getHumidityDate() {
        return humidity_date;
    }

    public String getTemperatureDate() {
        return temperature_date;
    }

    public String getTemperatureValue() {
        return temperature_value;
    }

    public String getHumidityValue() {
        return humidity_value;
    }

    @Override
    public String toString() {
        return nickname == null ? ID : nickname;
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

