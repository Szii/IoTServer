/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Logic;

import com.irrigation.Messages.MessageData.Device;
import com.irrigation.iotserver.Data.DataAccess;
import com.irrigation.Messages.MessageFormat.Code;
import com.irrigation.Messages.MessageFormat.MessageType;
import com.irrigation.Messages.MessageFormat.Payload;
import com.irrigation.iotserver.Security.PasswordHasher;
import com.irrigation.iotserver.Security.TokenGenerator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author brune
 */
public class UserConnection extends Thread{
    DataAccess databaseManager;
    Socket socket;
    ObjectInputStream objectInput;
    ObjectOutputStream objectOutput;
    String userToken = "";
    

    public UserConnection(Socket socket, DataAccess databaseManager) {
        this.databaseManager = databaseManager;
        this.socket = socket;
    }
    
    @Override
    public void run(){
        System.out.println("user connected, thread created");
        try {
            objectInput = new ObjectInputStream(socket.getInputStream());
            objectOutput = new ObjectOutputStream(socket.getOutputStream());
            //Connection estabilished, wait for messages and do proper opeation
            while (true){
                System.out.println("waiting for message from user");
                Payload message;
                message = ((Payload)objectInput.readObject());   //Blocking method
                
                if(message.getCode().equals(Code.FAILURE) || !message.getToken().equals(userToken)){
                    this.interrupt(); //end the conection
                }
                processMessage(message); //reacting to message
                
            }
        } catch (IOException ex) {
            Logger.getLogger(UserConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void processMessage(Payload message) throws SQLException{
        switch(message.getType()){
            case PING:
                System.out.println("Ping arrived");
                sendMessage(new Payload.PayloadBuilder()
                        .setCode(Code.SUCCESS)
                        .build());
            break;  
            case CONFIRM_LOGIN:
                System.out.println("Login attempt");
                if(PasswordHasher.compareIfPassowrdMatchesWithStoredHash((String) message.getContent().get(1), 
                        databaseManager.getPasswordQuery((String) message.getContent().get(0)))){
                    userToken = TokenGenerator.generateToken();
                    databaseManager.setTokenQuery((String) message.getContent().get(0), userToken);
                    sendLoginConfirmationSuccess();
                    System.out.println(userToken);

                }
                else{
                    sendCodeAnswerToDatabaseRequest(false, message.getType());
                }
            break;
            case GET_USER:
                System.out.println("Does user exist?");
                sendCodeAnswerToDatabaseRequest(databaseManager.getUserQuery((String) message.getContent().get(0)),message.getType());
                break;
            case ADD_USER:
                System.out.println("Adding user");
                sendCodeAnswerToDatabaseRequest(databaseManager.addUserQuery((String) message.getContent().get(0),
                        PasswordHasher.getHash((String) message.getContent().get(1))), message.getType()); 
            break;
            case GET_AVAILABLE_REGISTERED_DEVICES:
                sendMessage(new Payload.PayloadBuilder()
                        .setCode(Code.SUCCESS)
                        .setObject(getAvailableDevicesBasedOnUsername((String) message.getContent().get(0)))
                        .setType(MessageType.GET_AVAILABLE_REGISTERED_DEVICES)
                        .build());
            break;
            case GET_MEASUREMENT_DATA:
                sendMessage(new Payload.PayloadBuilder()
                        .setCode(Code.SUCCESS)
                        .setContent(databaseManager.getMeasurementDataQuery((String)message.getContent().get(0)))
                        .setType(MessageType.GET_MEASUREMENT_DATA)
                        .build());
            break;
            case GET_MEASUREMENT_DATA_IN_RANGE:
                sendMessage(new Payload.PayloadBuilder()
                        .setCode(Code.SUCCESS)
                        .setContent(databaseManager.getMeasurementDataInRange
                                            ((String)message.getContent().get(0),
                                            (String)message.getContent().get(1),
                                            (String)message.getContent().get(2)))
                        .setType(MessageType.GET_MEASUREMENT_DATA_IN_RANGE)
                        .build()); 
            break;
            
            case SET_IRRIGATION_TIME:
                databaseManager.setIrrigationTime((String)message.getContent().get(0), (String)message.getContent().get(1));
            break;
            case SET_DEVICE_NICKNAME:
                databaseManager.setSensorNickname((String)message.getContent().get(0), (String)message.getContent().get(1));
            break;
            case SET_THRESOLD:
                databaseManager.setThresoldQuery((String)message.getContent().get(0), (String)message.getContent().get(1));
            break;
            case REGISTER_DEVICE: 
                sendRegisterDeviceConfirmation(message);
            break;
            case UNREGISTER_DEVICE:
                databaseManager.unregisterDeviceQuery((String)message.getContent().get(0), (String)message.getContent().get(1));
            break;
            case GET_GROUPS:
                 sendMessage(new Payload.PayloadBuilder()
                        .setCode(Code.SUCCESS)
                        .setContent(databaseManager.getGroupsQuery((String) message.getContent().get(0)))
                        .setType(MessageType.GET_GROUPS)
                        .build());
            break;
            case GET_DEVICES_IN_GROUP:
                String group_ID = databaseManager.getGroupID((String) message.getContent().get(0),(String) message.getContent().get(1));
                sendMessage(new Payload.PayloadBuilder()
                        .setCode(Code.SUCCESS)
                        .setObject(getAvailableDevicesBasedOnUsernameAndGroup(group_ID))
                        .setType(MessageType.GET_DEVICES_IN_GROUP)
                        .build());
                        
                
            break;
            case CHANGE_DEVICE_GROUP:
                String groupID = databaseManager.getGroupID((String) message.getContent().get(0),(String) message.getContent().get(2));
                databaseManager.addDeviceToGroup(
                        (String) message.getContent().get(1),
                        groupID);
                sendCodeAnswerToDatabaseRequest(true, message.getType());
            break;
            case CHANGE_GROUP_NAME:
                sendCodeAnswerToDatabaseRequest(databaseManager.changeGroupName((String) message.getContent().get(0),(String) message.getContent().get(1),(String) message.getContent().get(2)),message.getType());
            break;
            case DELETE_GROUP:
                 sendCodeAnswerToDatabaseRequest(databaseManager.removeGroupQuery((String) message.getContent().get(0),(String) message.getContent().get(1)),message.getType());
            break;
            case CREATE_GROUP:
                sendCodeAnswerToDatabaseRequest(databaseManager.createGroupQuery((String) message.getContent().get(0),(String) message.getContent().get(1)),message.getType());
            break;
            

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
        
   
    private void sendMessage(Payload message){
        try {
            objectOutput.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(UserConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void sendLoginConfirmationSuccess(){
        sendMessage(new Payload.PayloadBuilder()
         .setCode(Code.SUCCESS)
         .setToken(userToken)
         .setType(MessageType.CONFIRM_LOGIN)
         .build());
        
    }
    
    private void sendRegisterDeviceConfirmation(Payload message){
        try {
            sendMessage(new Payload.PayloadBuilder()
                    .setCode(returnCodeSuccessOnTrue(databaseManager.registerDeviceQuery((String)message.getContent().get(0), (String)message.getContent().get(1))))
                    .setType(MessageType.REGISTER_DEVICE)
                    .build());
        } catch (SQLException ex) {
            Logger.getLogger(UserConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Code returnCodeSuccessOnTrue(boolean statement){
        return statement ? Code.SUCCESS : Code.FAILURE;
    } 
    
    private void sendCodeAnswerToDatabaseRequest(boolean isRequestValid, MessageType requestType){
        if(isRequestValid){
                   System.out.println("Sending success");
                    sendMessage(new Payload.PayloadBuilder()
                    .setCode(Code.SUCCESS)
                    .setType(requestType)
                    .build());
                }
                else{
                    System.out.println("Sending failure");
                    sendMessage(new Payload.PayloadBuilder()
                    .setCode(Code.FAILURE)
                    .setType(requestType)
                    .build());
                }
        
    }
    
    
   
    
    
    
}
