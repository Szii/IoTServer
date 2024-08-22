/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Logic;

import com.irrigation.Messages.MessageData.Device;
import com.irrigation.Messages.MessageFormat.Code;
import com.irrigation.Messages.MessageFormat.CredentialsRequest;
import com.irrigation.Messages.MessageFormat.DeviceRequest;
import com.irrigation.Messages.MessageFormat.GroupRequest;
import com.irrigation.Messages.MessageFormat.MeasurementRequest;
import com.irrigation.Messages.MessageFormat.Payload;
import com.irrigation.iotserver.Data.DataAccess;
import com.irrigation.iotserver.Program;
import com.irrigation.iotserver.Security.PasswordHasher;
import com.irrigation.iotserver.Security.TokenGenerator;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author brune
 */
@RestController
@RequestMapping("/api")
public class Controller {
    DataAccess databaseManager;
    public Controller(){
       this.databaseManager =  Program.getDatabaseManager();
    }
    
    
    public String checkAuthorisation(String token) throws SQLException{
            String username =  databaseManager.getTokenOwnerQuery(token);
            if(username.equals("")){
                throw new SQLException("Token not found");
            }
            else{
                return username;
            }
    }
    
    @PostMapping("/getAllDevices")
    public Payload getDevices(@RequestBody DeviceRequest deviceRequest) {
        try {
            String username = databaseManager.getTokenOwnerQuery(deviceRequest.getToken());
            return new Payload.PayloadBuilder()
                    .setCode(Code.SUCCESS)
                    .setContent(this.getAvailableDevicesBasedOnUsername(username))
                    .build();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }
    }
    
        @PostMapping("/getGroupDevices")
    public Payload getGroupDevices(@RequestBody DeviceRequest deviceRequest) {
        try {
            String username = databaseManager.getTokenOwnerQuery(deviceRequest.getToken());
            return new Payload.PayloadBuilder()
                    .setCode(Code.SUCCESS)
                    .setObject(this.getAvailableDevicesBasedOnUsernameAndGroup
                        (databaseManager.getGroupID(username,deviceRequest.getNewGroup())))
                    .build();
                            
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }
    }
    
    @PostMapping("/getGroups")
    public Payload getGroups(@RequestBody GroupRequest groupRequest){
          try {
            String username = databaseManager.getTokenOwnerQuery(groupRequest.getToken());
            return new Payload.PayloadBuilder()
                    .setCode(Code.SUCCESS)
                    .setContent(databaseManager.getGroupsQuery(username))
                    .build();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }
    }
    
    @PostMapping("/renameGroup")
    public Payload renameGroup(@RequestBody GroupRequest groupRequest){
          try {
             String username = databaseManager.getTokenOwnerQuery(groupRequest.getToken());
             if(databaseManager.changeGroupName(username, groupRequest.getGroup(), groupRequest.getGroupNewName())){
                 return new Payload.PayloadBuilder().setCode(Code.SUCCESS).build();
             }
             else{
                 return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
             }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }
    }
    
    @PostMapping("/updateDevice")
    public Payload updateDevice(@RequestBody DeviceRequest deviceRequest){
        try {
            String username = checkAuthorisation(deviceRequest.getToken());
            if(deviceRequest.getTreshold() != null)
                databaseManager.setThresoldQuery(deviceRequest.getDevice(),deviceRequest.getTreshold());
            if(deviceRequest.getIrrigationTime()!= null)
                databaseManager.setIrrigationTime(deviceRequest.getDevice(), deviceRequest.getIrrigationTime());
            if(deviceRequest.getDeviceNickname()!= null)
                databaseManager.setSensorNickname(deviceRequest.getDevice(), deviceRequest.getDeviceNickname());
            if(deviceRequest.getNewGroup()!= null)
                databaseManager.addDeviceToGroup(deviceRequest.getDevice(), databaseManager.getGroupID(username, deviceRequest.getNewGroup()));
            
            return new Payload.PayloadBuilder().setCode(Code.SUCCESS).build();
        } catch (SQLException ex) {
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }

    }
    
    @PostMapping("/measurement")
    public Payload getMeasurement(@RequestBody MeasurementRequest measurementRequest){
        try {
            String username = checkAuthorisation(measurementRequest.getToken());
            if(measurementRequest.getFrom() != null && measurementRequest.getTo() != null)
                    return new Payload.PayloadBuilder()
                    .setContent(databaseManager.getMeasurementDataInRange(measurementRequest.getDevice(),measurementRequest.getFrom(),measurementRequest.getTo()))
                    .setCode(Code.SUCCESS)
                    .build();
            
            return new Payload.PayloadBuilder()
                    .setContent(databaseManager.getMeasurementDataQuery(measurementRequest.getDevice()))
                    .setCode(Code.SUCCESS)
                    .build();
            
        } catch (SQLException ex) {
             Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
             return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }
        
    }
    
    
    
    @PostMapping("/login")
    public Payload confirmLogin(@RequestBody CredentialsRequest loginRequest) {
        try {
            System.out.println("Login attempt");
            if(PasswordHasher.compareIfPassowrdMatchesWithStoredHash(loginRequest.getPassword(),
                    databaseManager.getPasswordQuery(loginRequest.getUsername()))){
                String token = TokenGenerator.generateToken();
                databaseManager.setTokenQuery(loginRequest.getUsername(), token);
                return new Payload.PayloadBuilder()
                        .setCode(Code.SUCCESS)
                        .setContent(databaseManager.getAvailableSensors((loginRequest.getUsername())))
                        .setToken(token)
                        .build();
            }
            else{
                return new Payload.PayloadBuilder()
                        .setCode(Code.FAILURE)
                        .build();
            }
        } catch (SQLException ex) {
                     return new Payload.PayloadBuilder()
                        .setCode(Code.FAILURE)
                        .build();
        }
   }
    
     @PostMapping("/register")
    public Payload confirmRegister(@RequestBody CredentialsRequest registerRequest) {
            if(databaseManager.addUserQuery(registerRequest.getUsername(),registerRequest.getPassword())){
                return new Payload.PayloadBuilder()
               .setCode(Code.SUCCESS)
               .build();  
            }
            else{
                return new Payload.PayloadBuilder()
                .setCode(Code.FAILURE)
                .build(); 
            }
    }
    
    
    
    
        private ArrayList<Device> getAvailableDevicesBasedOnUsername(String username){
         ArrayList<Device> devices = new ArrayList();
        try {
            for (String sensorID : databaseManager.getAvailableSensors(username)){
                Device device = new Device.DeviceBuilder()
                        .setID(sensorID)
                        .setNickname(databaseManager.getSensorNickname(sensorID))
                        .setIrrigationTime(databaseManager.getIrrigationTime(sensorID))
                        .setGroup(databaseManager.getDeviceGroupQuery(sensorID).get(1))
                        .setLastMeasuredValue(databaseManager.getMeasurementDataQuery(sensorID).get(0))
                        .setThreshold(databaseManager.getThresoldQuery(sensorID))
                        .setDate(databaseManager.getMeasurementDataQuery(sensorID).get(1))
                        .build();
                
                devices.add(device);

            }
            return devices;
                    
                    } catch (SQLException ex) {
            Logger.getLogger(UserConnection.class.getName()).log(Level.SEVERE, null, ex);
            return devices;
        }
    }
    
        private ArrayList<Device> getAvailableDevicesBasedOnUsernameAndGroup(String group){
         ArrayList<Device> devices = new ArrayList();
        try {
            for (String sensorID :  databaseManager.getAllDevicesInGroupQuery(group)){
                Device device = new Device.DeviceBuilder()
                        .setID(sensorID)
                        .setNickname(databaseManager.getSensorNickname(sensorID))
                        .setIrrigationTime(databaseManager.getIrrigationTime(sensorID))
                        .setGroup(databaseManager.getDeviceGroupQuery(sensorID).get(1))
                        .setLastMeasuredValue(databaseManager.getMeasurementDataQuery(sensorID).get(0))
                        .setThreshold(databaseManager.getThresoldQuery(sensorID))
                        .setDate(databaseManager.getMeasurementDataQuery(sensorID).get(1))
                        .build();
                
                devices.add(device);

            }
            return devices;
                    
                    } catch (SQLException ex) {
            Logger.getLogger(UserConnection.class.getName()).log(Level.SEVERE, null, ex);
            return devices;
        }
        }
    
}
