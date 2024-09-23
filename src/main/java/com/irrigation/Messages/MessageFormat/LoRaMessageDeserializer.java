/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.Messages.MessageFormat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.irrigation.iotserver.Data.ParsedMessage;
import java.io.IOException;

/**
 *
 * @author brune
 */
public class LoRaMessageDeserializer extends StdDeserializer<ParsedMessage> {

    public LoRaMessageDeserializer() {
        this(null);
    }

    public LoRaMessageDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ParsedMessage deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);


// Print the entire node to confirm the structure
System.out.println("Full JSON node: " + node.toString());
    // Extract the device ID from the correct path
   String deviceId = node.path("end_device_ids").path("device_id").asText();
   if (deviceId.isEmpty()) {
       throw new IllegalArgumentException("Device ID is missing or empty");
   }

   // Extract the decoded payload from the correct path
   JsonNode decodedPayloadNode = node.path("uplink_message").path("decoded_payload");
   if (decodedPayloadNode.isMissingNode()) {
       throw new IllegalArgumentException("Decoded payload is missing");
   }

// Extract temperature and humidity from the decoded payload
    int temperature = decodedPayloadNode.path("Temperature").asInt();  // Temperature is a field
    int humidity = decodedPayloadNode.path("Humidity").asInt();
    return new ParsedMessage(deviceId, temperature, humidity);
    }
}
