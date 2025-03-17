/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.irrigation.Messages.MessageData;

/**
 *
 * @author brune
 */
public class Measurement {
    private String value;
    private String date;
    
    public Measurement(){}

    public Measurement(String value, String date) {
        this.value = value;
        this.date = date;
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
}
