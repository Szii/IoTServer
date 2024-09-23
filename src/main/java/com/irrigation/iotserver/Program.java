/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver;

import com.irrigation.iotserver.Data.DataAccess;
import com.irrigation.iotserver.Logic.OutboundManager;
import com.irrigation.iotserver.Data.DatabaseConnector;
import com.irrigation.iotserver.Data.DatabaseManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author brune
 */
public class Program {
    static DataAccess databaseManager;
    OutboundManager dataConnector;
    public Program(){
        prepareConnectionToDatabase();
        prepareConnectionToLoRaServer();
        String jsonPayload = "{ \"end_device_ids\": { \"device_id\": \"otaatest\", \"application_ids\": { \"application_id\": \"user-app\" }, \"dev_eui\": \"AAAAAAAAAAAAAAAA\", \"join_eui\": \"AE56CC33BC12724D\", \"dev_addr\": \"27FD8DA4\" }, \"correlation_ids\": [ \"gs:uplink:01J7NTNHC3YHDXHNXDKR5AFJ2E\" ], \"received_at\": \"2024-09-13T13:55:23.347794264Z\", \"uplink_message\": { \"session_key_id\": \"AZHrpGCSyIssKyUV0v9fuw==\", \"f_port\": 1, \"f_cnt\": 7, \"frm_payload\": \"NzA=\", \"decoded_payload\": { \"bytes\": [55,48] }, \"rx_metadata\": [ { \"gateway_ids\": { \"gateway_id\": \"gw1\", \"eui\": \"0016C001F110127D\" }, \"timestamp\": 13981017, \"rssi\": -90, \"channel_rssi\": -90, \"snr\": 13.5, \"frequency_offset\": \"7397\", \"uplink_token\": \"ChEKDwoDZ3cxEggAFsAB8RASfRDZqtUGGgsIy4iRtwYQtIahQiCox9WKtO4F\", \"channel_index\": 1, \"received_at\": \"2024-09-13T13:55:23.123022948Z\" } ], \"settings\": { \"data_rate\": { \"lora\": { \"bandwidth\": 125000, \"spreading_factor\": 7, \"coding_rate\": \"4/5\" } }, \"frequency\": \"868300000\", \"timestamp\": 13981017 }, \"received_at\": \"2024-09-13T13:55:23.140734087Z\", \"consumed_airtime\": \"0.046336s\", \"packet_error_rate\": 0.5714286, \"network_ids\": { \"net_id\": \"000013\", \"ns_id\": \"EC656E00001030C5\", \"tenant_id\": \"jcudp\", \"cluster_id\": \"eu2\", \"cluster_address\": \"eu2.cloud.thethings.industries\", \"tenant_address\": \"jcudp.eu2.cloud.thethings.industries\" } } }";

//        dataConnector.evaluateMessageBasedOnType(jsonPayload);
    } 
    
    public static DataAccess getDatabaseManager(){
        return databaseManager;
    }
    
    private void prepareConnectionToLoRaServer(){
        dataConnector = new OutboundManager(databaseManager);
        dataConnector.start();
    }
    
    private void prepareConnectionToDatabase(){
         try {
            databaseManager = new DatabaseManager(new DatabaseConnector().connect());
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void addUser() throws SQLException{
        databaseManager.addUserQuery("test", "12345");
    }
    
    
}
