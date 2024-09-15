/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.irrigation.iotserver.Data.ParsedMessage;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton class used to parse JSON retrieved through MQTT
 * @author brune
 */
public class EndDeviceMessageParser {
    
    private static EndDeviceMessageParser parser;
    
    private EndDeviceMessageParser(){}
    
    public static EndDeviceMessageParser getInstance(){
        if(parser == null){
            parser = new EndDeviceMessageParser();
        }
        return parser;
    }
    
    public ParsedMessage parseJSON(String dataInJSON){
        ParsedMessage parsedMessage = new ParsedMessage();
                // Create an ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode rootNode = objectMapper.readTree(dataInJSON);
            String devEui = rootNode.path("end_device_ids").path("device_id").asText();
            System.out.println("parsed devui: " + devEui);
            parsedMessage.setDeviceID(devEui);
            JsonNode payload = rootNode.path("uplink_message").path("decoded_payload").path("bytes");
            int payloadValue = -1;

            for (int i = 0; i < payload.size(); i++) {
                payloadValue = payload.get(i).asInt(); 
            }
            System.out.println("parsed humidity: " + payloadValue);
              parsedMessage.setHumidity(payloadValue);
             return parsedMessage;
            

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        return parsedMessage;
    }
    
    public int getDataValue(String dataInJSON){
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            // Parse the JSON payload
            JsonNode rootNode = objectMapper.readTree(dataInJSON);

            // Extract the decoded payload bytes array
            JsonNode decodedPayloadBytes = rootNode.path("uplink_message").path("decoded_payload").path("bytes");

            // Convert the byte array into a single integer value
            int decodedValue = 0;
            for (int i = 0; i < decodedPayloadBytes.size(); i++) {
                decodedValue = decodedPayloadBytes.get(i).asInt();
            }

            return decodedValue;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public String getTypeOfData(String dataInJSON){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            
            
            // Parse the JSON payload
            JsonNode rootNode = objectMapper.readTree(dataInJSON);

            // Extract the dev_eui field
            String devEui = rootNode.path("end_device_ids").path("dev_eui").asText();
            System.out.println("dev_eui: " + devEui);
            
            return devEui;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(EndDeviceMessageParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
      
    }
    
    public String getEndDeviceID(String dataInJSON){
         try {
            ObjectMapper objectMapper = new ObjectMapper();
            
            
            // Parse the JSON payload
            JsonNode rootNode = objectMapper.readTree(dataInJSON);

            // Extract the dev_eui field
            String devEui = rootNode.path("end_device_ids").path("dev_eui").asText();
            System.out.println("dev_eui: " + devEui);
            
            return devEui;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(EndDeviceMessageParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    
}
