/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Logic;

import com.irrigation.iotserver.Data.DataAccess;
import com.irrigation.iotserver.Data.Publisher;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Recieve and sends MQTT messages. These messages are processed and data are stored or retreived from DB
 * @author brune
 */
public class OutboundManager extends Thread implements IMqttMessageListener  {
    
    
    String address = "eu2.cloud.thethings.industries:1883";
    String appId = "user-app@jcudp";
    String accessKey = "NNSXS.J4N5MS7NOT5SOKD7SZQVAUR4EAW77SHKHUEUECY.YS2LWSWGNA7VUZDPUG2ANSQVW2QNXV7WIFFHO7JFEOKRH6I4JSAA";
    MqttClient client;
    Publisher pub;
    DataAccess databaseManager;

    public OutboundManager(DataAccess databaseManager){
        this.databaseManager = databaseManager;
    }
    
    @Override
    public void start(){
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
    }
    
    private void subscribeToTopics() throws MqttException{
        client.subscribe("#", this);
        System.out.println("Subscribed to topics");
        
    }
    
    private void connect(){
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(appId);
        options.setPassword(accessKey.toCharArray()); 
        try {
            client.connect(options);
        } catch (MqttException ex) {
            Logger.getLogger(OutboundManager.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
    
    public void sendMessage(String device,String message){
            pub = new Publisher();
            pub.setClient(client);
        try {
            pub.sentMessageToDevice(device,message);
        } catch (MqttException ex) {
            Logger.getLogger(OutboundManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
       String messageTxt = new String( message.getPayload(), "UTF-8" );
       System.out.println( "Message on " + topic + ": '" + messageTxt + "'");
    }
}
