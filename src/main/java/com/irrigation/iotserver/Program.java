/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver;

import com.irrigation.iotserver.Data.DataConnector;

/**
 *
 * @author brune
 */
public class Program {
    public Program(){
        DataConnector dataConnector = new DataConnector();
        dataConnector.sendMessage("device1","{\n" +
"  \"downlinks\": [{\n" +
"    \"f_port\": 15,\n" +
"    \"frm_payload\": \"vu8=\",\n" +
"    \"priority\": \"HIGH\",\n" +
"    \"confirmed\": true,\n" +
"    \"correlation_ids\": [\"my-correlation-id\"]\n" +
"  }]\n" +
"}");
    }
}
