/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Logic;

import com.irrigation.iotserver.Data.DataAccess;
import com.irrigation.iotserver.Data.ParsedMessage;
import com.irrigation.iotserver.Data.Publisher;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

/**
 * Retrieve and sends MQTT messages. These messages are processed and data are stored or got from DB
 * @author brune
 */
public class OutboundManager extends Thread implements IMqttMessageListener, MqttCallback  {
    
    
    String address = "eu2.cloud.thethings.industries:1883";
    String appId = "user-app@jcudp";
    String accessKey = "NNSXS.7M6KXOFU7WJ2M34MI5JOTI4PFMD55NKQWWO3ARQ.6VUC6RXGKWE7UKY6BF4T4W3OTOPFGZV7V4ZP5RYO7LNAN6DVBPVQ";
    MqttClient client;
    Publisher pub;
    DataAccess databaseManager;

    public OutboundManager(DataAccess databaseManager){
        this.databaseManager = databaseManager;
        init();
    }
    
    private void init(){
        try {
            client = new MqttClient(
                    "tcp://eu2.cloud.thethings.industries:1883", //URI
                    MqttClient.generateClientId(), //ClientId
                    new MemoryPersistence()); //Persistence
            connect();
            subscribeToTopics();
        } catch (MqttException ex) {
            Logger.getLogger(OutboundManager.class.getName()).log(Level.SEVERE, null, ex);
        }
            pub = new Publisher();
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
        
        options.setUserName(appId);
        options.setPassword(accessKey.toCharArray());     
        try {
            
            client.setCallback(this);
            client.connect(options);
            
        } catch (MqttException ex) {
            Logger.getLogger(OutboundManager.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
    
    public void sendMessage(String device,String message){      
        try {
            System.out.println("Trying to send message");
            client.disconnectForcibly();
            this.init();
            pub.sentMessageToDevice(device,message);
        } catch (MqttException ex) {
            Logger.getLogger(OutboundManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
       String messageTxt = new String( message.getPayload(), "UTF-8" );
       System.out.println( "Message arrived on topic: " + topic + ": '" + messageTxt + "'");
       evaluateMessageBasedOnType(messageTxt);
    }

    @Override
    public void connectionLost(Throwable cause) {
       System.out.println("Connection lost : " + cause.getMessage());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        try {
            System.out.println("Message delivered: " + token.getMessage().toString());
        } catch (MqttException ex) {
            Logger.getLogger(OutboundManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void evaluateMessageBasedOnType(String wholeMessageAsJSON) {
        try {
            System.out.println("Parsing message");
            ParsedMessage parsedMessage  = EndDeviceMessageParser.getInstance().parseJSON(wholeMessageAsJSON);
            if(parsedMessage.getHumidity() == -1){
                return;
            }
            System.out.println("checking if device exists");
            addDeviceIfNotExist(parsedMessage.getDeviceID());
            int storedThreshold = Integer.parseInt(databaseManager.getThresoldQuery(parsedMessage.getDeviceID()));
            int storedIrrigationTime = Integer.parseInt(databaseManager.getIrrigationTime(parsedMessage.getDeviceID()));
            if(storedThreshold >=  parsedMessage.getHumidity() && storedIrrigationTime > 0){
                System.out.println("Sending message");
                sendMessage(parsedMessage.getDeviceID(),String.valueOf(storedIrrigationTime));
            }
            System.out.println("Saving measurements");
            saveMeasurement(parsedMessage);
        } catch (SQLException ex) {
            Logger.getLogger(OutboundManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void addDeviceIfNotExist(String deviceID) throws SQLException{
        if(!databaseManager.checkIfDeviceExistsQuery(deviceID)){
            databaseManager.addDeviceQuery(deviceID);
        }
    }
    
    private void saveMeasurement(ParsedMessage data) throws SQLException{
       databaseManager.addMeasurementQuery(data.getDeviceID(), String.valueOf(data.getHumidity()), getCurrentDateTime());
    }
    
    private String getCurrentDateTime(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        System.out.println("Formatted Date and Time: " + formattedDateTime);
        return formattedDateTime;
    }
}
