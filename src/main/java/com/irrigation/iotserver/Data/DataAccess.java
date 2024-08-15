/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Data;

/**
 *
 * @author brune
 */
import java.sql.SQLException;
import java.util.ArrayList;

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
    public String getPasswordQuery(String name) throws SQLException;
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
     * Method add a username to unit ID, registering unit to the user
     * @param unit_ID unit ID
     * @param user username
     */
    public void registerUnitQuery(String unit_ID,String user);
    /**
     * Method returns registered units for user from data source
     * @param user Username
     * @return Registered units
     * @throws java.sql.SQLException Exception is thrown when problem with query occurs
     */
    public ArrayList<String> getRegisteredUnitsQuery(String user) throws SQLException;
    /**
     * Method gets unit if it is found in the datasource
     * @param name ID of unit
     * @return unit if it is present in data source, else empty string
     * @throws SQLException Exception is thrown when problem with query occurs
     */
    public String getUnitQuery(String name) throws SQLException;
    /**
     * Method checks if unit is registered under any user
     * @param unit_ID unit ID
     * @return true if unit is registered
     * @throws SQLException Exception is thrown when problem with query occurs
     */
    public boolean checkIfUnitIsRegisteredQuery(String unit_ID) throws SQLException;
    /**
     * Method checks the data source if unit is present
     * @param unit_ID ID of unit
     * @return Returns true if unit is present
     * @throws SQLException Exception is thrown when problem with query occurs
     */
    public boolean checkUnitQuery(String unit_ID) throws SQLException;
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
     * Method adds unit as new entry to the data source
     * @param unit_ID ID of unit
     * @throws SQLException Exception is thrown when problem with query occurs
     */
    public void addUnitQuery(String unit_ID)throws SQLException;
    /**
     * Method adds measured value of moisture as new entry into data source
     * @param sensor_ID ID of sensor
     * @param measure measured value
     * @param date date of measurement
     * @throws SQLException Exception is thrown when problem with query occurs
     */
    public void addMeasurmentQuery(String sensor_ID,String measure,String date)throws SQLException;
    /**
     * Method unregister unit by modifing data source
     * @param unit_ID ID of unit
     * @throws SQLException Exception is thrown when problem with query occurs
     */
    public void unregisterUnitQuery(String unit_ID)throws SQLException;
    /**
     * Method gets last measured value for sensor from data source
     * @param sensor_ID sensor ID
     * @return last measured value
     * @throws SQLException Exception is thrown when problem with query occurs
     */
    public String lastQuery(String sensor_ID) throws SQLException;
    /**
     * Method checks the data source if sensor belongs to unit
     * @param sensor_ID ID of sensor
     * @param unit_ID ID of unit
     * @return Returns true if sensor belongs to unit
     * @throws SQLException Exception is thrown when problem with query occurs
     */
    public boolean doesSensorBelongsToUnit(String sensor_ID,String unit_ID) throws SQLException;  
    /**
     * Method checks the data source if sensor is registered under any unit
     * @param sensor_ID
     * @param unit_ID
     * @return Returns true if sensor is register under any unit
     * @throws SQLException Exception is thrown when problem with query occurs
     */
    public boolean isSensorHiddenFromUnit(String sensor_ID,String unit_ID) throws SQLException;  
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
    public ArrayList<String> getAvailableSensors(String username) throws SQLException;
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
     * Methods modifies the data source by setting the nickname for unit
     * @param unit_ID ID of unit
     * @param nickname Nickname for unit
     * @throws SQLException Exception is thrown when problem with query occurs
     */
    public void setUnitNickname (String unit_ID,String nickname) throws SQLException;
    /**
     * Methods checks the data source for nickname of sensor
     * @param sensor_ID Id of sensor
     * @return Nickname of sensor if its present, if not, returns ID of sensor
     * @throws SQLException Exception is thrown when problem with query occurs
     */
    public String getSensorNickname (String sensor_ID) throws SQLException; 
   /**
     * Methods checks the data source for nickname of unit
     * @param unit_ID Id of unit
     * @return Nickname of unit if its present, if not, returns ID of unit
     * @throws SQLException Exception is thrown when problem with query occurs
     */
    public String getUnitNickname (String unit_ID) throws SQLException;
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
     * @throws SQLException Exception is thrown when problem with query occurs
     */
    public ArrayList<String> getMeasurementDataQuery (String sensorID) throws SQLException;
    /**
     * Method checks the data source for all measurements of sensor in time interval
     * @param sensorID ID of sensor
     * @param from lower bound
     * @param to higher bound
     * @return Measurements of sensor in time interval
     * @throws SQLException Exception is thrown when problem with query occurs
     */
    public ArrayList<String> getMeasurementDataInRange (String sensorID,String from,String to) throws SQLException;
    
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
    public ArrayList<String> getDeviceGroupQuery(String sensor_ID) throws SQLException;
    
    public ArrayList<String> getGroupsQuery(String username) throws SQLException;
    
    public ArrayList<String> getAllDevicesInGroupQuery(String group, String username) throws SQLException;
         
    public boolean addDeviceToGroup (String device_ID,String group) throws SQLException;

    public boolean removeDeviceFromGroupQuery(String device_ID) throws SQLException;
          
    public boolean addGroupQuery(String group_ID,String username) throws SQLException;
    
    public boolean removeGroupQuery(String group_name, String username) throws SQLException;
    
    public boolean changeGroupName(String username, String oldGroup,String newGroup) throws SQLException;
    
    public String getGroupID(String username, String group) throws SQLException;
                 



}
