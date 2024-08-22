/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.Messages.MessageFormat;

/**
 *
 * @author brune
 */
public class DeviceRequest extends UserRequest {
    
    String device;
    String irrigationTime;
    String treshold;
    String deviceNickname;
    String newGroup;

    public DeviceRequest(String token, String device, String irrigationTime, String treshold, String deviceNickname) {
        super(token);
        this.device = device;
        this.irrigationTime = irrigationTime;
        this.treshold = treshold;
        this.deviceNickname = deviceNickname;
    }

    public String getNewGroup() {
        return newGroup;
    }

    public void setNewGroup(String newGroup) {
        this.newGroup = newGroup;
    }

    public DeviceRequest(String token, String device, String deviceNickname) {
        super(token);
        this.device = device;
        this.deviceNickname = deviceNickname;
    }

    public DeviceRequest(String token, String device) {
        super(token);
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
