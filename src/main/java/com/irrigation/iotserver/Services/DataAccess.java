/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Services;

/**
 *
 * @author brune
 */
import com.irrigation.iotserver.Configuration.DatabaseConnector;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;
import java.util.List;

/**
 * Implementation of this interface contains logic behind managing the stored data
 * @author brune
 */
public interface DataAccess {
    /**
     * 
     * @param name username
     * @return hashed value representing password of user
     * @throws SQLException 
     */
    public String getPasswordQuery(String name) throws SQLException, SQLNonTransientConnectionException;
    /**
     * Method returns username if name parameter is found in the data source
     * @param name Username
     * @return username from data source if present, if not returns empty string
     * @throws SQLException Exception is thrown when problem with query occurs
     */
    public boolean getUserQuery(String name) throws SQLException;
    /**
     * Method adds new entry to the data source 
     * @param name Name to be added
     * @param password password to be added
     * @throws SQLException Exception is thrown when problem with query occurs
     */
    public boolean addUserQuery(String name,String password);
    /**
     * Method gets threshold for sensor from the data source
     * @param sensor_ID Id of sensor
     * @return threshold for sensor
     * @throws SQLException  Exception is thrown when problem with query occurs
     */
    public String getThresoldQuery(String sensor_ID) throws SQLException;
    /**
     *  Method sets threshold for the sensor by modifing data source
     * @param sensor_ID Id of sensor
     * @param thresold threshold to be set
     * @throws SQLException Exception is thrown when problem with query occurs
     */
    public void setThresoldQuery(String sensor_ID,String thresold) throws SQLException;
    /**
     * Method adds measured value of moisture as new entry into data source
     * @param sensor_ID ID of sensor
     * @param measure measured value
     * @param date date of measurement
     * @param type type of data
     * @throws SQLException Exception is thrown when problem with query occurs
     */
    public void addMeasurementQuery(String sensor_ID,String measure,String date, String type)throws SQLException;
    /**
     * Method gets last measured value for sensor from data source
     * @param sensor_ID sensor ID
     * @param type type of measurememt
     * @return last measured value
     * @throws SQLException Exception is thrown when problem with query occurs
     */
    public List<String> getLastMeasurementQuery(String sensor_ID,String type) throws SQLException;
    /**
     * Method register sensor under a unit by modifing data source
     * @param sensor_ID ID of sensor
     * @param sensor_unit_ID ID of unit
     * @throws SQLException Exception is thrown when problem with query occurs
     * @return true if device is registered successfully
     */
    public boolean registerDeviceQuery(String sensor_ID,String sensor_unit_ID) throws SQLException;
    /**
     * Method check the data source for all sensors which are registered under specific unit
     * @param username username
     * @return Sensors registered under specific unit
     * @throws SQLException Exception is thrown when problem with query occurs
     */
    public List<String> getAvailableSensors(String username) throws SQLException;
    /**
     * Method modifies the data source by removing sensor from under a user to which is registered
     * @param sensor_ID ID of sensor
     * @param username name of user
     * @throws SQLException Exception is thrown when problem with query occurs
     */
    public void unregisterDeviceQuery(String sensor_ID,String username) throws SQLException;
    /**
     * Methods modifies the data source by setting the nickname for sensor
     * @param sensor_ID ID of sensor
     * @param nickname Nickname for sensor
     * @throws SQLException Exception is thrown when problem with query occurs
     */
    public void setSensorNickname (String sensor_ID,String nickname) throws SQLException;  
    /**
     * Methods checks the data source for nickname of sensor
     * @param sensor_ID Id of sensor
     * @return Nickname of sensor if its present, if not, returns ID of sensor
     * @throws SQLException Exception is thrown when problem with query occurs
     */
    public String getSensorNickname (String sensor_ID) throws SQLException; 
    /**
     * Method checks the data source for sensor owner
     * @param sensorID Id of sensor
     * @return Unit as owner if present, else empty string
     * @throws SQLException 
     */
    public String getSensorOwner (String sensorID) throws SQLException;
    /**
     * Method checks the data source for all measurements of sensor
     * @param sensorID ID of sensor
     * @return Measurements of sensor
     * @param type type of measurement 
     * @throws SQLException Exception is thrown when problem with query occurs
     */
    public List<String> getMeasurementDataQuery (String sensorID, String type) throws SQLException;
    /**
     * Method checks the data source for all measurements of sensor in time interval
     * @param sensorID ID of sensor
     * @param from lower bound
     * @param to higher bound
     * @param type type of measurement 
     * @return Measurements of sensor in time interval
     * @throws SQLException Exception is thrown when problem with query occurs
     */
    public List<String> getMeasurementDataInRange (String sensorID,String from,String to, String type) throws SQLException;
    
    /**
     * Gets irrigation time for a sensor from data source
     * @param sensorID ID of sensor
     * @return irrigation time 
     * @throws SQLException Exception is thrown when problem with query occurs
     */
    public String getIrrigationTime(String sensorID) throws SQLException;;
    
    /**
     * Sets irrigation time for a sensor by modifying data source
     * @param sensorID ID of sensor
     * @param value irrigation time value to be set
     * @throws SQLException Exception is thrown when problem with query occurs
     */
    public void setIrrigationTime(String sensorID,String value) throws SQLException;
    
    /**
     * 
     * @param sensor_ID ID of sensor
     * @return list containing ID and nickname of group
     * @throws SQLException 
     */
    public List<String> getDeviceGroupQuery(String sensor_ID) throws SQLException;
    
    public List<String> getGroupsQuery(String username) throws SQLException;
    
    public List<String> getAllDevicesInGroupQuery(String group) throws SQLException;
         
    public boolean addDeviceToGroup (String device_ID,String group) throws SQLException;

    public boolean removeDeviceFromGroupQuery(String device_ID) throws SQLException;
          
    public boolean addGroupQuery(String group_ID,String username) throws SQLException;
    
    public boolean removeGroupQuery(String group_name, String username) throws SQLException;
    
    public boolean changeGroupName(String username, String oldGroup,String newGroup) throws SQLException;
    
    public String getGroupID(String username, String group) throws SQLException;
    
    public boolean createGroupQuery(String username, String group) throws SQLException;
    
    public String getTokenOwnerQuery(String username) throws SQLException;
    
    public void setTokenQuery(String username,String token) throws SQLException;
    
    public void addDeviceQuery(String deviceID)throws SQLException;
   
    public boolean checkIfDeviceExistsQuery(String deviceID) throws SQLException;
    
    public String getGroupNameQuery(String username) throws SQLException;
    
    public void refreshConnection();
                 



}
