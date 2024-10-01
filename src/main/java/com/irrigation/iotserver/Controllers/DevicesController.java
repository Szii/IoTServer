/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Controllers;

import com.irrigation.Messages.MessageData.Device;
import com.irrigation.Messages.MessageFormat.Code;
import com.irrigation.Messages.MessageFormat.DeviceRequest;
import com.irrigation.Messages.MessageFormat.Payload;
import com.irrigation.iotserver.Services.ControllerHelperService;
import com.irrigation.iotserver.Services.DataAccess;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api/devices")
public class DevicesController {
    
   private final DataAccess databaseManager;
   private final ControllerHelperService helperService;
    public DevicesController(DataAccess databaseManager,ControllerHelperService helperService){
       this.databaseManager =  databaseManager;
       this.helperService = helperService;
    }
    
      @GetMapping("/getAll")
    public Payload getDevices(@RequestHeader("Authorization") String token) {
        try {
            String username = databaseManager.getTokenOwnerQuery(helperService.getToken(token));
            return new Payload.PayloadBuilder()
                    .setCode(Code.SUCCESS)
                    .setObject(this.getAvailableDevicesBasedOnUsername(username))
                    .build();
        } catch (SQLException ex) {
           
            Logger.getLogger(DevicesController.class.getName()).log(Level.SEVERE, null, ex);
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }
    }
    
    @GetMapping("/getAllInGroup")
    public Payload getGroupDevices(@RequestHeader("Authorization") String token, @RequestParam(required = true) String groupName) {
        try {
            String username = databaseManager.getTokenOwnerQuery(helperService.getToken(token));
            String groupID = databaseManager.getGroupID(username,groupName);
            System.out.println("got groupID for group " + groupName + " ID " + groupID);
            return new Payload.PayloadBuilder()
                    .setCode(Code.SUCCESS)
                    .setObject(this.getAvailableDevicesBasedOnGroup(
                        (databaseManager.getGroupID(username,groupName))))
                    .build();
                            
        } catch (SQLException ex) {
            Logger.getLogger(DevicesController.class.getName()).log(Level.SEVERE, null, ex);
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }
    }
    
      @PostMapping("/register")
    public Payload registerDevice(@RequestHeader("Authorization") String token, @RequestBody DeviceRequest deviceRequest){
                try {
                    String username = helperService.checkAuthorisation(token);

                     return new Payload.PayloadBuilder()
                        .setCode(helperService.returnCodeSuccessOnTrue(databaseManager.registerDeviceQuery(deviceRequest.getDevice(), username)))
                        .build();
                  
        } catch (SQLException ex) {
            Logger.getLogger(DevicesController.class.getName()).log(Level.SEVERE, null, ex);
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }
    }
    
     @PostMapping("/unregister")
     public Payload unregistrerDevice(@RequestHeader("Authorization") String token, @RequestBody DeviceRequest deviceRequest){
                try {
                    String username = helperService.checkAuthorisation(token);
                    databaseManager.unregisterDeviceQuery(deviceRequest.getDevice(), username);
                     return new Payload.PayloadBuilder()
                        .setCode(Code.SUCCESS)
                        .build();
                  
        } catch (SQLException ex) {
            Logger.getLogger(DevicesController.class.getName()).log(Level.SEVERE, null, ex);
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }
    }
    
        
    @PostMapping("/removeFromGroup")
    public Payload removeDeviceFromGroup(@RequestHeader("Authorization") String token, @RequestBody DeviceRequest deviceRequest){
        try {
            String username = helperService.checkAuthorisation(token);
            databaseManager.removeDeviceFromGroupQuery(deviceRequest.getDevice());     
            return new Payload.PayloadBuilder().setCode(Code.SUCCESS).build();
        } catch (SQLException ex) {
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }

    }
    
    @PostMapping("/update")
    public Payload updateDevice(@RequestHeader("Authorization") String token, @RequestBody DeviceRequest deviceRequest){
        System.out.println("UPDATE DEVICE ENDPOINT CALLED");
        try {
            System.out.println("Updating device: " + helperService.getToken(token) + deviceRequest.getDevice() + deviceRequest.getDeviceNickname()+ deviceRequest.getIrrigationTime()+ deviceRequest.getNewGroup());
            String username = helperService.checkAuthorisation(token);
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
    
     private ArrayList<Device> getAvailableDevicesBasedOnGroup(String group){
       try {
           return getDevices(databaseManager.getAllDevicesInGroupQuery(group));
       } catch (SQLException ex) {
           Logger.getLogger(DevicesController.class.getName()).log(Level.SEVERE, null, ex);
       }
       return new ArrayList<>();
     }
    
      private ArrayList<Device> getAvailableDevicesBasedOnUsername(String username){   
       try {
           return getDevices(databaseManager.getAvailableSensors(username));
       } catch (SQLException ex) {
           Logger.getLogger(DevicesController.class.getName()).log(Level.SEVERE, null, ex);
       }
       return new ArrayList<>();

    }
       
     private ArrayList<Device> getDevices(ArrayList<String> devices) throws SQLException{
           ArrayList<Device> devicesList = new ArrayList();
            for (String sensorID : devices){
                ArrayList<String> humidityMeasurement = databaseManager.getLastMeasurementQuery(sensorID,"TYPE_HUMIDITY");
                ArrayList<String> temperatureMeasurement = databaseManager.getLastMeasurementQuery(sensorID,"TYPE_TEMPERATURE");
                Device device = new Device.DeviceBuilder()
                        .setID(sensorID)
                        .setNickname(databaseManager.getSensorNickname(sensorID))
                        .setIrrigationTime(databaseManager.getIrrigationTime(sensorID))
                        .setGroup(databaseManager.getDeviceGroupQuery(sensorID).get(1))
                        .setHumidityValue(humidityMeasurement.get(0))
                        .setThreshold(databaseManager.getThresoldQuery(sensorID))
                        .setHumidityDate(humidityMeasurement.get(1))
                        .setTemperatureDate(temperatureMeasurement.get(1))
                        .setTemperatureValue(temperatureMeasurement.get(0))
                        .build();
                
                devicesList.add(device);

            }
       return devicesList;
     }              
}
