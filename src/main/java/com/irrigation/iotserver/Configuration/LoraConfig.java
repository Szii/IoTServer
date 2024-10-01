/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.stereotype.Component;

/**
 *
 * @author brune
 */
@Component
@ConfigurationPropertiesScan
public class LoraConfig {
    
    @Value("${lora.address}")
    public String address;
    @Value("${lora.appId}")
    public String appId;
    @Value("${lora.key}")
    public String key;
}
