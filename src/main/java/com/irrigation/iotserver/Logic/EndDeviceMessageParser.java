/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Logic;

/**
 *
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
    
    public String getDataValue(String dataInJSON){
        String dataValue = "";
        
        // get dataValue from json
        
        return dataValue;
    }
    
    public String getTypeOfData(String dataInJSON){
        String typeOfData = "";
        
        // get dataType from json
        
        return typeOfData;
    }
    
    public String getEndDeviceID(String dataInJSON){
        String endDeviceID = "";
        
        // get endDeviceID from json
        
        return endDeviceID;
    }


    
}
