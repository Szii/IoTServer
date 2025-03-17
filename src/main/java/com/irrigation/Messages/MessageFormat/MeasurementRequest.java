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
@Schema(name = "MeasurementRequest", description = "Payload to request measurement data for a given device, potentially within a specified time range.")
public class MeasurementRequest implements Serializable {

    @Schema(
        description = "Unique device identifier for which measurements are requested.",
        example = "otaatest",
        required = true
    )
    private String device;

    @Schema(
        description = "Start of the measurement timeframe (optional). If omitted, there's no lower bound on the date range.",
        example = "2025-01-01 00:00:00"
    )
    private String from;

    @Schema(
        description = "End of the measurement timeframe (optional). If omitted, there's no upper bound on the date range.",
        example = "2025-02-01 00:00:00"
    )
    private String to;
    
    @Schema(
        description = "Type of measurement. Currently TYPE_HUMIDITY and TYPE_TEMPERATURE types are supported",
        example = "TYPE_HUMIDITY"
    )
    private String type;

    public MeasurementRequest() {}

    public MeasurementRequest(String device) {
        this.device = device;
    }

    public MeasurementRequest(String device, String from, String to) {
        this.device = device;
        this.from = from;
        this.to = to;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
