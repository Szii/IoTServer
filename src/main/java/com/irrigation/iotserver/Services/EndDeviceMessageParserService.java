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
import com.irrigation.Exceptions.MissingJSONContentException;
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

    private final ObjectMapper objectMapper;

    public EndDeviceMessageParserService() {
        this.objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ParsedMessage.class, new LoRaMessageDeserializer());
        objectMapper.registerModule(module);
    }

    public ParsedMessage parseJSONData(String dataInJSON) throws JsonProcessingException, MissingJSONContentException {
        System.out.println("Parsing start");
        JsonNode rootNode = objectMapper.readTree(dataInJSON);

        JsonNode uplinkMessage = rootNode.at("/uplink_message");

        if (!uplinkMessage.has("frm_payload") || !uplinkMessage.has("decoded_payload")) {
            throw new MissingJSONContentException("Missing required fields: 'frm_payload' or 'decoded_payload'");
        }
            
        ParsedMessage measurement = objectMapper.readValue(dataInJSON, ParsedMessage.class);

        System.out.printf("Parsed data - ID: %s, Humidity: %d, Temperature: %d%n",
                measurement.getDeviceID(), measurement.getHumidity(), measurement.getTemperature());

        return measurement;
    }

    public ParsedMessage parseJSON(String dataInJSON) throws MissingJSONContentException {
        try {
            JsonNode rootNode = objectMapper.readTree(dataInJSON);

            JsonNode uplinkMessage = rootNode.at("/uplink_message");

            if (!uplinkMessage.has("frm_payload") || !uplinkMessage.has("decoded_payload")) {
                throw new MissingJSONContentException("Missing required fields: 'frm_payload' or 'decoded_payload'");
            }
            
            String deviceId = rootNode.at("/end_device_ids/device_id").asText();
            JsonNode payload = rootNode.at("/uplink_message/decoded_payload/bytes");

            int humidity = payload.get(0).asInt();
            int temperature = payload.get(1).asInt();

            ParsedMessage parsedMessage = new ParsedMessage(deviceId, temperature, humidity);

            System.out.printf("Parsed data - ID: %s, Humidity: %d, Temperature: %d%n",
                    parsedMessage.getDeviceID(), parsedMessage.getHumidity(), parsedMessage.getTemperature());

            return parsedMessage;
        } catch (IOException e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            return null; 
        }
    }
}
