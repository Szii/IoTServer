/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

/**
 *
 * @author brune
 */
@Service
public class PublisherService {
    
    MqttClient client;
    String MESSAGE_PRESET = "{\"downlinks\":[{\"f_port\": 15,\"frm_payload\":\"vu8=\",\"priority\": \"NORMAL\"}]}";
    
    public PublisherService(){
        
    }

    public MqttClient getClient() {
        return client;
    }

    public void setClient(MqttClient client) {
        this.client = client;
    }
    
    private String getMessageInValidFormat(String dataToSent){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(MESSAGE_PRESET);
            JsonNode downlinkNode = rootNode.path("downlinks").get(0); 
            ((ObjectNode) downlinkNode).put("frm_payload", convertToBase64(dataToSent)); 
            return  objectMapper.writeValueAsString(rootNode);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(PublisherService.class.getName()).log(Level.SEVERE, null, ex);
            return  "";
        }
    }
    
    private String convertToBase64(String undecodedData){
        String encodedString = Base64.getEncoder().encodeToString(undecodedData.getBytes());
        System.out.println("Original string: " + undecodedData);
        System.out.println("Base64-encoded string: " + encodedString);
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedValue = new String(decodedBytes);
        System.out.println("Decoded value: " + decodedValue);
        return encodedString;
    }
    
    private MqttMessage createMessage(String message){
       System.out.println("Started creating mqtt message");
        MqttMessage msg = new MqttMessage(getMessageInValidFormat(message).getBytes());
        msg.setQos(1);
        System.out.println("created message: " + msg.toString());
        return msg;
    }
    
    public void sentMessageToDevice(String deviceID,String message){
        try {
            System.out.println("Started sending mqtt message for device " + deviceID);
            System.out.println("Mqtt client: " + client.getCurrentServerURI() + " " + client.getClientId());
            client.publish("v3/user-app@jcudp/devices/"+deviceID+"/down/push", createMessage(message));
            System.out.println("message " + message + " sent");
        } catch (Exception ex) {
            Logger.getLogger(PublisherService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
 
}
