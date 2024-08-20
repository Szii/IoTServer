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
     * Adds a new user to to database
     */
    ADD_USER,
    /**
     * Registering new sensor
     */
    REGISTER_DEVICE,
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
    UNREGISTER_DEVICE,
    /**
     * Getting a owner of sensor
     */
    GET_SENSOR_OWNER,
    /**
     * Getting a threshold of sensor
     */
    SET_THRESOLD,
    /**
     * Setting a nickname of sensor
     */
    SET_DEVICE_NICKNAME,
    /**
     * Getting all measured data by single sensor
     */
    GET_MEASUREMENT_DATA,
    /**
     * Getting measured data in time interval by a single sensor
     */
    GET_MEASUREMENT_DATA_IN_RANGE,    
     
    /**
     * Gettings all sensors register under specific unit
     */
    GET_AVAILABLE_REGISTERED_DEVICES,

    /**
     * Sets a time period for which is valve opened for a sensor
     */
    SET_IRRIGATION_TIME,
    /**
     * Error signalizing something did not went according to a plan
     */
    GET_GROUPS,
    
    GET_DEVICES_IN_GROUP,
    
    CHANGE_GROUP_NAME,
    
    CHANGE_DEVICE_GROUP,

    DELETE_GROUP,
        
    CREATE_GROUP,

    ERROR
}
