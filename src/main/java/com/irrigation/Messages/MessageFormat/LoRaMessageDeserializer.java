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
import com.irrigation.iotserver.Data.MeasurementType;
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
         String deviceId = node.path("end_device_ids").path("device_id").asText();
         JsonNode decodedPayloadNode = node.path("uplink_message").path("decoded_payload");
        System.out.println("deserializing message, expecting two numbers and one string");
        int temperature = decodedPayloadNode.path("Temperature").asInt();  
        int humidity = decodedPayloadNode.path("Humidity").asInt();
        String type = decodedPayloadNode.path("Type").toString();
        System.out.println("Deserialzation complete, got: " + temperature + " " + humidity + " " + type);
        return new ParsedMessage(deviceId, temperature, humidity);
    }
}
