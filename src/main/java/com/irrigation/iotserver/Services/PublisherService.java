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
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

/**
 *
 * @author brune
 */
@Service
public class PublisherService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final JsonNode MESSAGE_TEMPLATE;

    private MqttClient client;

    static {
        try {
            MESSAGE_TEMPLATE = OBJECT_MAPPER.readTree(
                "{\"downlinks\":[{\"f_port\": 15, \"frm_payload\": \"\", \"priority\": \"NORMAL\"}]}"
            );
        } catch (JsonProcessingException e) {
            throw new ExceptionInInitializerError("Failed to parse message template: " + e.getMessage());
        }
    }

    public PublisherService() {}

    public void setClient(MqttClient client) {
        this.client = client;
    }

    private String prepareMessage(String payload) {
        try {
            JsonNode messageNode = MESSAGE_TEMPLATE.deepCopy();
            ((ObjectNode) messageNode.path("downlinks").get(0))
                .put("frm_payload", Base64.getEncoder().encodeToString(payload.getBytes(StandardCharsets.UTF_8)));

            return OBJECT_MAPPER.writeValueAsString(messageNode);
        } catch (JsonProcessingException e) {
            System.out.println(e);
            return "";
        }
    }

    private MqttMessage createMqttMessage(String payload) {
        System.out.println("Creating MQTT message");
        String formattedMessage = prepareMessage(payload);
        MqttMessage mqttMessage = new MqttMessage(formattedMessage.getBytes(StandardCharsets.UTF_8));
        mqttMessage.setQos(1);
        System.out.println("Created message: " + formattedMessage);
        return mqttMessage;
    }

    public void sendMessageToDevice(String deviceID, String payload) {
        if (client == null) {
            System.out.println("MQTT client is not initialized!");
            return;
        }

        try {
             System.out.println("Sending MQTT message to device: " + deviceID);
             System.out.println("MQTT client: " + client.getCurrentServerURI() + " " + client.getClientId());

            client.publish("v3/user-app@jcudp/devices/" + deviceID + "/down/push", createMqttMessage(payload));

             System.out.println("Message sent successfully to device " + deviceID);
        } catch (Exception e) {
             System.out.println("Failed to send MQTT message: " + e);
        }
    }
}
