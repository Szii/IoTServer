/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.Messages.MessageFormat;

import java.io.Serializable;

/**
 *
 * @author brune
 */
public class MeasurementRequest implements Serializable{
    
    String device;
    String from;
    String to;

    public MeasurementRequest(String device) {
        this.device = device;
    }
    
     public MeasurementRequest() {}

    public MeasurementRequest(String device, String from, String to) {
        this.device = device;
        this.from = from;
        this.to = to;
    }
    
    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
    
    
}
