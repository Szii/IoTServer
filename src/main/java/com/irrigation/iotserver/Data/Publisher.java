/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Data;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author brune
 */
public class Publisher {
    
    MqttClient client;
    
    public Publisher(){
        
    }

    public MqttClient getClient() {
        return client;
    }

    public void setClient(MqttClient client) {
        this.client = client;
    }
    
    private MqttMessage createMessage(String message){
        MqttMessage msg = new MqttMessage(message.getBytes());
        msg.setQos(0);
        return msg;
    }
    
    public void sentMessageToDevice(String deviceID,String message) throws MqttException{
        client.publish("v3/user-app@jcudp/devices/"+deviceID+"/down/push", createMessage(message));
        System.out.println("message " + message + " sent");
    }
    
    
    
    
}
