/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.irrigation.Messages.MessageData;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author brune
 */
@Schema(name = "Measurement", description = "Single measurement representet by value and date")
public class Measurement {
    @Schema(description = "Measured value", example = "30")
    private String value;
    @Schema(description = "When was measurement saved", example = "2025-01-01 10:15:30")
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
