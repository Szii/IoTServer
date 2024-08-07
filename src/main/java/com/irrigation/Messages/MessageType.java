/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.Messages;

import java.io.Serializable;

/**
 *
 * @author brune
 */
public enum MessageType implements Serializable  {

    /**
     * Message which urges the client to respond witn PING MessageType
     */
    PING,
    /**
     * Message which is used as response for PING. It is lost on server side and server for maintaining connection
     */
    PONG,
    /**
     * Confirming login message
     */
    CONFIRM_LOGIN,
    /**
     * Getting a user message
     */
    GET_USER,
    /**
     * Registering new sensor
     */
    REGISTER_SENSOR,
    /**
     * Gettings the moisture of the sensor
     */
    GET_MOISTURE,
    /**
     * Adding new measured value
     */
    ADD_MEASURED_VALUE,
    /**
     * Unregistering a sensor
     */
    UNREGISTER_SENSOR,
    /**
     * Getting a owner of sensor
     */
    GET_SENSOR_OWNER,
    /**
     * Getting a threshold of sensor
     */
    GET_THRESOLD,
    /**
     * Setting a threshold of sensor
     */
    SET_THRESOLD,
    /**
     * Setting a nickname of sensor
     */
    SET_SENSOR_NICKNAME,
    /**
     * Getting a nickname of sensor
     */
    GET_SENSOR_NICKNAME,
    /**
     * Getting all measured data by single sensor
     */
    GET_MEASUREMENT_DATA,
    /**
     * Getting measured data in time interval by a single sensor
     */
    GET_MEASUREMENT_DATA_IN_RANGE,    
    /**
     * Check if unit is in the data source
     
    /**
     * Gettings all sensors register under specific unit
     */
    GET_AVAILABLE_REGISTERED_SENSORS,
    /**
     * Setting unit nickname
     */
    SET_UNIT_NICKNAME,
    /**
     * Getting all unregistered sensors
     */
    GET_SENSORS_IN_RANGE,
    /**
     * Getting if sensor is online
     */
    IS_SENSOR_ACTIVE,
    /**
     * Sets a time period for which is valve opened for a sensor
     */
    SET_IRRIGATION_TIME,
    /**
     * Gets a time period for which is valve opened for a sensor
     */
    GET_IRRIGATION_TIME,
    /**
     * Error signalizing something did not went according to a plan
     */
    ERROR
}
