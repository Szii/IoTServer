/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.irrigation.Messages.MessageFormat.LoRaMessageDeserializer;
import com.irrigation.iotserver.Data.MeasurementType;
import com.irrigation.iotserver.Data.ParsedMessage;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

/**
 * Singleton class used to parse JSON retrieved through MQTT
 * @author brune
 */

@Component
public class EndDeviceMessageParserService {
     
    public  EndDeviceMessageParserService(){}
    
 
    
    public ParsedMessage parseJSONData(String dataInJSON) throws JsonProcessingException{

            System.out.println("Parsing start");
            ObjectMapper objectMapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(ParsedMessage.class, new LoRaMessageDeserializer());
            objectMapper.registerModule(module);
            System.out.println("Parsing read");
            ParsedMessage measurement = objectMapper.readValue(dataInJSON, ParsedMessage.class);
             System.out.println("Parsing end");
            System.out.println("parsed humidity: " + measurement.getHumidity());
            System.out.println("parsed temperature: " + measurement.getTemperature());
            System.out.println("parsed id: " + measurement.getDeviceID());
            return measurement;
    }
    
    public ParsedMessage parseJSON(String dataInJSON){
       ParsedMessage parsedMessage = new ParsedMessage("a",1,1);
       ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode rootNode = objectMapper.readTree(dataInJSON);
            String devEui = rootNode.path("end_device_ids").path("device_id").asText();
            System.out.println("parsed devui: " + devEui);
            parsedMessage.setDeviceID(devEui);
            JsonNode payload = rootNode.path("uplink_message").path("decoded_payload").path("bytes");
            parsedMessage.setHumidity(payload.get(0).asInt());
            parsedMessage.setTemperature(payload.get(01).asInt());
            System.out.println("parsed humidity: " + parsedMessage.getHumidity());
            System.out.println("parsed temperature: " + parsedMessage.getTemperature());
            return parsedMessage;
            

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        return parsedMessage;
    }
}
