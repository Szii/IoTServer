/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.Messages.MessageFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

/**
 *
 * @author brune
 */
@Schema(name = "DeviceRequest", description = "Payload for device-related operations.")
public class DeviceRequest implements Serializable {

    @Schema(
        description = "Unique device identifier.",
        example = "otaatest"
    )
    private String device;

    @Schema(
        description = "Irrigation time setting for this device in seconds. Zero value makes device do not water at all",
        format = "Integer",
        example = "3"
    )
    private String irrigationTime;

    @Schema(
        description = "Moisture threshold setting",
        format = "Integer",
        example = "30"
    )
    private String treshold;

    @Schema(
        description = "Human-friendly nickname for the device.",
        example = "Macešky_1"
    )
    private String deviceNickname;

    @Schema(
        description = "If set, moves the device to the specified group or removes it from any group if empty.",
        example = "Sklenik_venku"
    )
    private String newGroup;

    public DeviceRequest(String device, String irrigationTime, String treshold, String deviceNickname) {
        this.device = device;
        this.irrigationTime = irrigationTime;
        this.treshold = treshold;
        this.deviceNickname = deviceNickname;
    }
      
    public DeviceRequest() {}

    public String getNewGroup() {
        return newGroup;
    }

    public void setNewGroup(String newGroup) {
        this.newGroup = newGroup;
    }

    public DeviceRequest(String device, String deviceNickname) {
        this.device = device;
        this.deviceNickname = deviceNickname;
    }

    public DeviceRequest(String device) {
        this.device = device;
    }

  
    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getIrrigationTime() {
        return irrigationTime;
    }

    public void setIrrigationTime(String irrigationTime) {
        this.irrigationTime = irrigationTime;
    }

    public String getTreshold() {
        return treshold;
    }

    public void setTreshold(String treshold) {
        this.treshold = treshold;
    }

    public String getDeviceNickname() {
        return deviceNickname;
    }

    public void setDeviceNickname(String deviceNickname) {
        this.deviceNickname = deviceNickname;
    }
}
