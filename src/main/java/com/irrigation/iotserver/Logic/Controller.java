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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    private String getToken(String token){
             if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
             return token;
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
    
    @GetMapping("/devices/getAll")
    public Payload getDevices(@RequestHeader("Authorization") String token) {
        try {
            String username = databaseManager.getTokenOwnerQuery(getToken(token));
            return new Payload.PayloadBuilder()
                    .setCode(Code.SUCCESS)
                    .setObject(this.getAvailableDevicesBasedOnUsername(username))
                    .build();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }
    }
    
    @GetMapping("/devices/getAllInGroup")
    public Payload getGroupDevices(@RequestHeader("Authorization") String token, @RequestParam(required = true) String groupName) {
        try {
            String username = databaseManager.getTokenOwnerQuery(getToken(token));
            return new Payload.PayloadBuilder()
                    .setCode(Code.SUCCESS)
                    .setObject(this.getAvailableDevicesBasedOnUsernameAndGroup
                        (databaseManager.getGroupID(username,groupName)))
                    .build();
                            
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }
    }
    
    @GetMapping("/groups/get")
    public Payload getGroups(@RequestHeader("Authorization") String token){
          try {
            String username = databaseManager.getTokenOwnerQuery(getToken(token));
            return new Payload.PayloadBuilder()
                    .setCode(Code.SUCCESS)
                    .setContent(databaseManager.getGroupsQuery(username))
                    .build();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }
    }
    
    @PostMapping("/groups/rename")
    public Payload renameGroup(@RequestHeader("Authorization") String token, @RequestBody GroupRequest groupRequest){
        System.out.println("RENAME GROUP ENDPOINT CALLED");
          try {
             String username = databaseManager.getTokenOwnerQuery(getToken(token));
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
    
    @DeleteMapping("/groups/remove")
    public Payload removeGroup(@RequestHeader("Authorization") String token,  @RequestParam("group") String group){
        try {
            String username = checkAuthorisation(getToken(token));
            databaseManager.removeGroupQuery(username, group);     
            return new Payload.PayloadBuilder().setCode(Code.SUCCESS).build();
        } catch (SQLException ex) {
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }

    }
    
    @PostMapping("/groups/add")
    public Payload addGroup(@RequestHeader("Authorization") String token, @RequestBody GroupRequest groupRequest){
        System.out.println("ADD GROUP ENDPOINT CALLED " + groupRequest.getGroup());
        try {
            String username = checkAuthorisation(getToken(token));
            databaseManager.createGroupQuery(username,groupRequest.getGroup());     
            return new Payload.PayloadBuilder().setCode(Code.SUCCESS).build();
        } catch (SQLException ex) {
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }

    }
    
    @PostMapping("/devices/register")
    public Payload registerDevice(@RequestHeader("Authorization") String token, @RequestBody DeviceRequest deviceRequest){
                try {
                    String username = checkAuthorisation(getToken(token));

                     return new Payload.PayloadBuilder()
                        .setCode(returnCodeSuccessOnTrue(databaseManager.registerDeviceQuery(deviceRequest.getDevice(), username)))
                        .build();
                  
        } catch (SQLException ex) {
            Logger.getLogger(UserConnection.class.getName()).log(Level.SEVERE, null, ex);
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }
    }
    
     @PostMapping("/devices/unregister")
     public Payload unregistrerDevice(@RequestHeader("Authorization") String token, @RequestBody DeviceRequest deviceRequest){
                try {
                    String username = checkAuthorisation(getToken(token));
                    databaseManager.unregisterDeviceQuery(deviceRequest.getDevice(), username);
                     return new Payload.PayloadBuilder()
                        .setCode(Code.SUCCESS)
                        .build();
                  
        } catch (SQLException ex) {
            Logger.getLogger(UserConnection.class.getName()).log(Level.SEVERE, null, ex);
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }
    }
    
        
    @PostMapping("/devices/removeFromGroup")
    public Payload removeDeviceFromGroup(@RequestHeader("Authorization") String token, @RequestBody DeviceRequest deviceRequest){
        try {
            String username = checkAuthorisation(getToken(token));
            databaseManager.removeDeviceFromGroupQuery(deviceRequest.getDevice());     
            return new Payload.PayloadBuilder().setCode(Code.SUCCESS).build();
        } catch (SQLException ex) {
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }

    }
    
    @PostMapping("/devices/update")
    public Payload updateDevice(@RequestHeader("Authorization") String token, @RequestBody DeviceRequest deviceRequest){
        System.out.println("UPDATE DEVICE ENDPOINT CALLED");
        try {
            System.out.println("Updating device: " + getToken(token) + deviceRequest.getDevice() + deviceRequest.getDeviceNickname()+ deviceRequest.getIrrigationTime()+ deviceRequest.getNewGroup());
            String username = checkAuthorisation(getToken(token));
            if(deviceRequest.getTreshold() != null)
                databaseManager.setThresoldQuery(deviceRequest.getDevice(),deviceRequest.getTreshold());
            if(deviceRequest.getIrrigationTime()!= null)
                databaseManager.setIrrigationTime(deviceRequest.getDevice(), deviceRequest.getIrrigationTime());
            if(deviceRequest.getDeviceNickname()!= null)
                databaseManager.setSensorNickname(deviceRequest.getDevice(), deviceRequest.getDeviceNickname());   
            if(deviceRequest.getNewGroup() != null){
                databaseManager.addDeviceToGroup(deviceRequest.getDevice(), databaseManager.getGroupID(username, deviceRequest.getNewGroup()));  
            }
            return new Payload.PayloadBuilder().setCode(Code.SUCCESS).build();
        } catch (SQLException ex) {
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }

    }
    
    @GetMapping("/measurement/get")
    public Payload getMeasurement(@RequestHeader("Authorization") String token,  @RequestParam (required = true)  String device, @RequestParam (required = false)  String from,@RequestParam (required = false)  String to){
        System.out.println("from is: " + from + " to is: " + to);
        try {
            String username = checkAuthorisation(getToken(token));
            if(from!= null && to != null){
                    return new Payload.PayloadBuilder()
                    .setContent(databaseManager.getMeasurementDataInRange(device,from,to))
                    .setCode(Code.SUCCESS)
                    .build();
            }
            return new Payload.PayloadBuilder()
                    .setContent(databaseManager.getMeasurementDataQuery(device))
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
            System.out.println("returning devices: " + devices.size());
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
            System.out.println("returning devices: " + devices.size());
            return devices;
                    
                    } catch (SQLException ex) {
            Logger.getLogger(UserConnection.class.getName()).log(Level.SEVERE, null, ex);
            return devices;
        }
        }
    private Code returnCodeSuccessOnTrue(boolean statement){
        return statement ? Code.SUCCESS : Code.FAILURE;
    } 
    
}
