/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Service;

/**
 *
 * @author brune
 */
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DatabaseService implements DataAccess {
    private final Connection connection;
    private static final Logger logger = Logger.getLogger(DatabaseService.class.getName());

    public DatabaseService(Connection connection) {
        this.connection = connection;
    }

    // ==============================
    // 🔹 HELPER METHODS
    // ==============================

    private PreparedStatement prepareStatement(String query, Object... parameters) throws SQLException {
        PreparedStatement pst = connection.prepareStatement(query);
        for (int i = 0; i < parameters.length; i++) {
            pst.setObject(i + 1, parameters[i]);
        }
        return pst;
    }

    private String fetchSingleValue(String query, String column, Object... parameters) throws SQLException {
        try (PreparedStatement pst = prepareStatement(query, parameters);
             ResultSet result = pst.executeQuery()) {
            return result.next() ? result.getString(column) : "";
        }
    }

    private boolean checkExistence(String query, Object... parameters) throws SQLException {
        try (PreparedStatement pst = prepareStatement(query, parameters);
             ResultSet result = pst.executeQuery()) {
            return result.next();
        }
    }

    private boolean executeUpdate(String query, Object... parameters) {
        try (PreparedStatement pst = prepareStatement(query, parameters)) {
            pst.executeUpdate();
            return true;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Database update error", ex);
            return false;
        }
    }

    private List<String> fetchList(String query, String column, Object... parameters) throws SQLException {
        List<String> values = new ArrayList<>();
        try (PreparedStatement pst = prepareStatement(query, parameters);
             ResultSet result = pst.executeQuery()) {
            while (result.next()) {
                values.add(result.getString(column));
            }
        }
        return values;
    }

    // ==============================
    // 🔹 USER METHODS
    // ==============================

    @Override
    public String getPasswordQuery(String username) throws SQLException {
        return fetchSingleValue("SELECT password FROM users WHERE username = ?", "password", username);
    }

    @Override
    public boolean getUserQuery(String username) throws SQLException {
        return checkExistence("SELECT 1 FROM users WHERE username = ?", username);
    }

    @Override
    public boolean addUserQuery(String username, String password) {
        return executeUpdate("INSERT INTO users (username, password) VALUES (?, ?)", username, password);
    }

    @Override
    public String getTokenOwnerQuery(String token) throws SQLException {
        return fetchSingleValue("SELECT username FROM users WHERE token = ?", "username", token);
    }

    @Override
    public void setTokenQuery(String username, String token) {
        executeUpdate("UPDATE users SET token = ? WHERE username = ?", token, username);
    }

 
    // ==============================
    // 🔹 DEVICE METHODS
    // ==============================

    @Override
    public boolean checkIfDeviceExistsQuery(String deviceID) throws SQLException {
        return checkExistence("SELECT 1 FROM devices WHERE device_ID = ?", deviceID);
    }

    @Override
    public void addDeviceQuery(String deviceID) throws SQLException {
        executeUpdate("INSERT INTO devices (device_ID) VALUES (?)", deviceID);
    }

    @Override
    public String getThresoldQuery(String sensor_ID) throws SQLException {
        return fetchSingleValue("SELECT device_treshold FROM devices WHERE device_ID = ?", "device_treshold", sensor_ID);
    }

    @Override
    public void setThresoldQuery(String sensor_ID, String threshold) throws SQLException {
        executeUpdate("UPDATE devices SET device_treshold = ? WHERE device_ID = ?", threshold, sensor_ID);
    }

    @Override
    public String getIrrigationTime(String sensorID) throws SQLException {
        return fetchSingleValue("SELECT device_irrigationTime FROM devices WHERE device_ID = ?", "device_irrigationTime", sensorID);
    }

    @Override
    public void setIrrigationTime(String sensorID, String value) throws SQLException {
        executeUpdate("UPDATE devices SET device_irrigationTime = ? WHERE device_ID = ?", value, sensorID);
    }

    // ==============================
    // 🔹 MEASUREMENT METHODS
    // ==============================

    @Override
    public void addMeasurementQuery(String sensor_ID, String measure, String date, String type) throws SQLException {
        truncateMeasurementTable();
        executeUpdate("INSERT INTO measurements (device_ID, date, value, type_ID) VALUES (?, ?, ?, ?)",
                sensor_ID, date, measure, getTypeIDFromTypeNameQuery(type));
    }
    
        @Override
    public List<String> getMeasurementDataQuery(String sensorID, String type) throws SQLException {
        List<String> measurements = new ArrayList<>();
        String query = "SELECT measurements.value, measurements.date " +
                "FROM measurements " +
                "INNER JOIN measurement_types ON measurements.type_ID = measurement_types.type_ID " +
                "WHERE device_ID = ? AND measurement_types.type_name = ?";

        try (PreparedStatement pst = prepareStatement(query, sensorID, type);
             ResultSet result = pst.executeQuery()) {
            while (result.next()) {
                measurements.add(result.getString("value"));
                measurements.add(result.getString("date"));
            }
        }
        return measurements;
    }

    @Override
    public List<String> getMeasurementDataInRange(String sensorID, String from, String to, String type) throws SQLException {
        List<String> measurements = new ArrayList<>();
        String query = "SELECT measurements.value, measurements.date " +
                "FROM measurements " +
                "INNER JOIN measurement_types ON measurements.type_ID = measurement_types.type_ID " +
                "WHERE device_ID = ? AND measurement_types.type_name = ? AND date BETWEEN ? AND ?";

        try (PreparedStatement pst = prepareStatement(query, sensorID, type, from, to);
             ResultSet result = pst.executeQuery()) {
            while (result.next()) {
                measurements.add(result.getString("value"));
                measurements.add(result.getString("date"));
            }
        }
        return measurements;
    }

    @Override
    public List<String> getLastMeasurementQuery(String sensor_ID, String type) throws SQLException {
        List<String> measurements = new ArrayList<>();
        String query = "SELECT measurements.value, measurements.date " +
                "FROM measurements " +
                "INNER JOIN measurement_types ON measurement_types.type_ID = measurements.type_ID " +
                "WHERE device_ID = ? AND measurement_types.type_name = ? " +
                "ORDER BY date DESC LIMIT 1";
        
        try (PreparedStatement pst = prepareStatement(query, sensor_ID, type);
             ResultSet result = pst.executeQuery()) {
            if (result.next()) {
                measurements.add(result.getString("value"));
                measurements.add(result.getString("date"));
            }
        }
        return measurements.isEmpty() ? List.of("", "") : measurements;
    }


    private void truncateMeasurementTable() throws SQLException {
        String countQuery = "SELECT COUNT(*) AS rowcount FROM measurements";
        int maxCount = 10000;

        try (PreparedStatement ps = prepareStatement(countQuery);
             ResultSet result = ps.executeQuery()) {
            if (result.next() && result.getInt("rowcount") > maxCount) {
                executeUpdate("TRUNCATE measurements");
                executeUpdate("ALTER TABLE measurements AUTO_INCREMENT = 1");
            }
        }
    }

    private int getTypeIDFromTypeNameQuery(String typeName) throws SQLException {
        return Integer.parseInt(fetchSingleValue("SELECT type_ID FROM measurement_types WHERE type_name = ?", "type_ID", typeName));
    }

    // ==============================
    // 🔹 GROUP METHODS
    // ==============================

    @Override
    public boolean addGroupQuery(String group_ID, String username) {
        return executeUpdate("INSERT INTO `groups`  (groupname, username) VALUES (?, ?)", group_ID, username);
    }

    @Override
    public boolean removeGroupQuery(String username, String group_name) throws SQLException {
        String group_ID = getGroupID(username, group_name);
        removeDeviceFromGroupQuery(group_ID);
        return executeUpdate("DELETE FROM `groups`  WHERE group_ID = ?", group_ID);
    }

    @Override
    public String getGroupID(String username, String group) throws SQLException {
        return fetchSingleValue("SELECT group_ID FROM `groups` WHERE username = ? AND group_name = ?", "group_ID", username, group);
    }

    @Override
    public List<String> getGroupsQuery(String username) throws SQLException {
        return fetchList("SELECT group_name FROM `groups` WHERE username = ?", "group_name", username);
    }
    
    @Override
    public String getGroupNameQuery(String username) throws SQLException {
        return fetchSingleValue("SELECT group_name FROM `groups` WHERE group_ID = ?", "group_name", username);
    }

    @Override
    public boolean changeGroupName(String username, String oldGroup, String newGroup) {
        return executeUpdate("UPDATE `groups`  SET group_name = ? WHERE username = ? AND group_name = ?", newGroup, username, oldGroup);
    }
    
    @Override
    public List<String> getDeviceGroupQuery(String sensor_ID) throws SQLException {
        return fetchList(
                "SELECT groups.group_ID FROM `groups`  JOIN devices ON groups.group_ID = devices.group_ID WHERE devices.device_ID = ?",
                "group_ID",
                sensor_ID
        );
    }

    @Override
    public List<String> getAllDevicesInGroupQuery(String group) throws SQLException {
        return fetchList("SELECT device_ID FROM devices WHERE group_ID = ?", "device_ID", group);
    }

    @Override
    public boolean addDeviceToGroup(String device_ID, String group) throws SQLException {
        System.out.println("Adding device to group :" +  group);

        return executeUpdate("UPDATE devices SET group_ID = ? WHERE device_ID = ?", group, device_ID);
    }

    @Override
    public boolean removeDeviceFromGroupQuery(String device_ID) throws SQLException {
        return executeUpdate("UPDATE devices SET group_ID = NULL WHERE device_ID = ?", device_ID);
    }

    @Override
    public boolean createGroupQuery(String username, String group) throws SQLException {
        if (checkExistence("SELECT 1 FROM `groups` WHERE username = ? AND group_name = ?", username, group)) {
            return false;  // Group already exists
        }
        return executeUpdate("INSERT INTO `groups` "
                + "(group_name, username) VALUES (?, ?)", group, username);
    }
    
    
    // ======================
    // Sensor Methods
    // ======================
    
    @Override
    public boolean registerDeviceQuery(String sensor_ID, String user) throws SQLException {
        return executeUpdate("INSERT INTO user_device (device, user) VALUES (?, ?)", sensor_ID, user);
    }

    @Override
    public void unregisterDeviceQuery(String sensor_ID, String username) throws SQLException {
        executeUpdate("UPDATE devices SET group_ID = NULL WHERE device_ID = ?", sensor_ID);
        executeUpdate("UPDATE devices SET device_name = NULL WHERE device_ID = ?", sensor_ID);
        executeUpdate("DELETE FROM user_device WHERE device = ? AND user = ?", sensor_ID, username);
    }

    @Override
    public void setSensorNickname(String sensor_ID, String nickname) throws SQLException {
        if(nickname.isBlank()){
             executeUpdate("UPDATE devices SET device_name = NULL WHERE device_ID = ?", sensor_ID);
        }
        executeUpdate("UPDATE devices SET device_name = ? WHERE device_ID = ?", nickname, sensor_ID);
    }

    @Override
    public String getSensorNickname(String sensor_ID) throws SQLException {
        return fetchSingleValue("SELECT device_name FROM devices WHERE device_ID = ?", "device_name", sensor_ID);
    }

    @Override
    public String getSensorOwner(String sensorID) throws SQLException {
        return fetchSingleValue("SELECT username FROM user_device WHERE device_ID = ?", "username", sensorID);
    }

    @Override
    public List<String> getAvailableSensors(String username) throws SQLException {
        return fetchList(
                "SELECT devices.device_ID FROM user_device JOIN devices ON devices.device_ID = user_device.device WHERE user_device.user = ?",
                "device_ID",
                username
        );
    }
}