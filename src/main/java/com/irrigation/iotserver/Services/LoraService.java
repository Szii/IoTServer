/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.irrigation.Exceptions.MissingJSONContentException;
import com.irrigation.iotserver.Configuration.LoraConfig;
import com.irrigation.iotserver.Data.MeasurementType;
import com.irrigation.iotserver.Data.ParsedMessage;
import jakarta.annotation.PostConstruct;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Service;

/**
 * Retrieve and sends MQTT messages. These messages are processed and data are stored or got from DB
 * @author brune
 */
@Service
public class LoraService extends Thread implements IMqttMessageListener, MqttCallback  {
    
    private final static int PERCENTAGE_MIN = 0;
    private final static int PERCENTAGE_MAX = 100;
    
    private final static int TEMPERATURE_MIN = -50;
    private final static int TEMPERATURE_MAX = 100;
    

    private final PublisherService pub;
    private final DataAccess databaseManager;
    private final EndDeviceMessageParserService parser;
    private final LoraConfig conf;

    private MqttClient client;
    
    

    public LoraService(DataAccess databaseManager,LoraConfig conf,PublisherService pub,EndDeviceMessageParserService parser){
        this.databaseManager = databaseManager;
        this.conf = conf;
        this.pub = pub;
        this.parser = parser;
        System.out.println(conf.address);
        System.out.println(conf.appId);
        System.out.println(conf.key);
        init();
    }
    
    @PostConstruct
    public void postConstructor() {
        System.out.println("Outbound manager started");
        this.start();  
    }
    
    private void init(){
        try {
            client = new MqttClient(
                    "tcp://" + conf.address, 
                    MqttClient.generateClientId(), 
                    new MemoryPersistence());
            connect();
            subscribeToTopics();
        } catch (MqttException ex) {
            Logger.getLogger(LoraService.class.getName()).log(Level.SEVERE, null, ex);
        }
            pub.setClient(client);
    }
    
    @Override
    public void run(){
     
    }
    
    
    private void subscribeToTopics() throws MqttException{
        //client.subscribe("v3/+/devices/+/up",this);
      
       // client.subscribe("#", this);
        
        client.subscribe("v3/user-app@jcudp/devices/otaatest/up", this);
        
      
        System.out.println("Subscribed to topics");
        
    }
    
    private void connect(){
        MqttConnectOptions options = new MqttConnectOptions();
        
        options.setUserName(conf.appId);
        options.setPassword(conf.key.toCharArray());  
        options.setAutomaticReconnect(true);
        try {
            
            client.setCallback(this);
            client.connect(options);
      
            
        } catch (MqttException ex) {
            Logger.getLogger(LoraService.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
    
    public void sendMessage(String device,String message){      
       pub.sendMessageToDevice(device,message);

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
       String messageTxt = new String( message.getPayload(), "UTF-8" );
       System.out.println( "Message arrived on topic: " + topic + ": '" + messageTxt + "'");
       evaluateMessageBasedOnType(messageTxt);
    }

    @Override
    public void connectionLost(Throwable cause) {
           System.err.println("Connection lost: " + cause.getMessage());
           cause.printStackTrace();
        try {
            client.reconnect();
        } catch (MqttException ex) {
            Logger.getLogger(LoraService.class.getName()).log(Level.SEVERE, null, ex);
        }
          
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
    
    public void evaluateMessageBasedOnType(String wholeMessageAsJSON) {

        try {
            System.out.println("Parsing message");
            ParsedMessage parsedMessage = parser.parseJSONData(wholeMessageAsJSON);

            System.out.println("Checking if device exists");
            addDeviceIfNotExist(parsedMessage.getDeviceID());

            int storedThreshold = getStoredThreshold(parsedMessage.getDeviceID());
            int storedIrrigationTime = getStoredIrrigationTime(parsedMessage.getDeviceID());

            boolean shouldIrrigate = storedThreshold >= parsedMessage.getHumidity() && storedIrrigationTime > 0;
            String irrigationMessage = shouldIrrigate ? String.valueOf(storedIrrigationTime) : "0";
            if(!irrigationMessage.equals("0")){
                System.out.println("Sending message. Got: " + parsedMessage.getHumidity() + " and threshold is: " + storedThreshold);
                sendMessage(parsedMessage.getDeviceID(), irrigationMessage); 
            }
            else{
                System.out.println("Do not sending message. Got: " + parsedMessage.getHumidity() + " and threshold is: " + storedThreshold);
            }
            
            
            System.out.println("Saving measurements");
            saveMeasurement(parsedMessage);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(LoraService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MissingJSONContentException ex) {
            Logger.getLogger(LoraService.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(LoraService.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    private int getStoredThreshold(String deviceId) throws SQLException {
        return Integer.parseInt(databaseManager.getThresoldQuery(deviceId));
    }

    private int getStoredIrrigationTime(String deviceId) throws SQLException {
        return Integer.parseInt(databaseManager.getIrrigationTime(deviceId));
    }

    private void addDeviceIfNotExist(String deviceID) throws SQLException{
        
        if(!databaseManager.checkIfDeviceExistsQuery(deviceID)){
            databaseManager.addDeviceQuery(deviceID);
        }
    }
    
    private void saveMeasurement(ParsedMessage data) throws SQLException{
        if(data.getHumidity() <= PERCENTAGE_MAX || data.getHumidity() <= PERCENTAGE_MIN){
                 System.out.println("Saving humidity " + data.getHumidity());
                 databaseManager.addMeasurementQuery(data.getDeviceID(), String.valueOf(data.getHumidity()), getCurrentDateTime(), MeasurementType.TYPE_HUMIDITY.toString()); 
        }
        else{
            
        }
        if(data.getTemperature() <= TEMPERATURE_MIN  || data.getTemperature() <= TEMPERATURE_MAX){
                 System.out.println("Saving temperature " + data.getTemperature());
                 databaseManager.addMeasurementQuery(data.getDeviceID(), String.valueOf(data.getTemperature()), getCurrentDateTime(), MeasurementType.TYPE_TEMPERATURE.toString());  
        }
    }
    
    private String getCurrentDateTime(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        System.out.println("Formatted Date and Time: " + formattedDateTime);
        return formattedDateTime;
    }
}
