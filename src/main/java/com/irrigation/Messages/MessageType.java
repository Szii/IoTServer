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
     * Init message, it announces that current thread is used as update thread on server side
     */
    UPDATE_THREAD,
    /**
     * Init message, it announces that current thread is used as ack thread on server side for PONG requests
     */
    ACK_THREAD,
    /**
     * Adding a new user
     */
    ADD_USER,
   /**
     * Init message, it announces that current thread is used as main unit thread on server side for PONG requests
     */
    UNIT,
    /**
     * Init message, it announces that current thread is used as main user thread on server side for PONG requests
     */
    USER,
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
     * Check if sensor belongs to current unit
     */
    DOES_SENSOR_BELONGS_TO_UNIT,
    /**
     * Check, if sensor is available to be seen by any unit
     */
    IS_SENSOR_HIDDEN_FROM_UNIT,
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
     */
    CHECK_IF_UNIT_EXISTS,
    /**
     * Checking if unit is registered under any user
     */
    CHECK_IF_UNIT_IS_REGISTERED,
    /**
     * Getting unit nickname
     */
    GET_UNIT_NICKNAME,
    /**
     * Getting unit ID
     */
    GET_UNIT,
    /**
     * Registering new unit
     */
    REGISTERR_UNIT,
    /**
     * Getting all registered unit under user
     */
    GET_REGISTERED_UNITS,
    /**
     * Unregistering unit
     */
    UNREGISTER_UNIT,
    /**
     * Checking, if unit is online
     */
    IS_UNIT_ONLINE,
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
    GET_UNREGISTERED_SENSORS,
    /**
     * Getting all unregistered sensors for specific unit in its range
     */
    GET_UNREGISTERED_SENSORS_IN_RANGE,
     /**
     * Getting all unregistered sensors for specific unit in its range
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
