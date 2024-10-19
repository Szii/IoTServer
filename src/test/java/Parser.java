
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.irrigation.iotserver.Services.EndDeviceMessageParserService;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brune
 */
public class Parser {
    String jsonPayload = "{ \"end_device_ids\": { \"device_id\": \"otaatest\", \"application_ids\": { \"application_id\": \"user-app\" }, \"dev_eui\": \"AAAAAAAAAAAAAAAA\", \"join_eui\": \"AE56CC33BC12724D\", \"dev_addr\": \"27FD8DA4\" }, \"correlation_ids\": [ \"gs:uplink:01J7NTNHC3YHDXHNXDKR5AFJ2E\" ], \"received_at\": \"2024-09-13T13:55:23.347794264Z\", \"uplink_message\": { \"session_key_id\": \"AZHrpGCSyIssKyUV0v9fuw==\", \"f_port\": 1, \"f_cnt\": 7, \"frm_payload\": \"NzA=\", \"decoded_payload\": { \"bytes\": [70] }, \"rx_metadata\": [ { \"gateway_ids\": { \"gateway_id\": \"gw1\", \"eui\": \"0016C001F110127D\" }, \"timestamp\": 13981017, \"rssi\": -90, \"channel_rssi\": -90, \"snr\": 13.5, \"frequency_offset\": \"7397\", \"uplink_token\": \"ChEKDwoDZ3cxEggAFsAB8RASfRDZqtUGGgsIy4iRtwYQtIahQiCox9WKtO4F\", \"channel_index\": 1, \"received_at\": \"2024-09-13T13:55:23.123022948Z\" } ], \"settings\": { \"data_rate\": { \"lora\": { \"bandwidth\": 125000, \"spreading_factor\": 7, \"coding_rate\": \"4/5\" } }, \"frequency\": \"868300000\", \"timestamp\": 13981017 }, \"received_at\": \"2024-09-13T13:55:23.140734087Z\", \"consumed_airtime\": \"0.046336s\", \"packet_error_rate\": 0.5714286, \"network_ids\": { \"net_id\": \"000013\", \"ns_id\": \"EC656E00001030C5\", \"tenant_id\": \"jcudp\", \"cluster_id\": \"eu2\", \"cluster_address\": \"eu2.cloud.thethings.industries\", \"tenant_address\": \"jcudp.eu2.cloud.thethings.industries\" } } }";
         
    @Test
    public void parseDeviceID(){
            
         try {
            ObjectMapper objectMapper = new ObjectMapper();
            
            
            // Parse the JSON payload
            JsonNode rootNode = objectMapper.readTree(jsonPayload);

            // Extract the dev_eui field
            String devEui = rootNode.path("end_device_ids").path("dev_eui").asText();
            System.out.println("dev_eui: " + devEui);
            
            assertEquals(devEui,"AAAAAAAAAAAAAAAA");
  
        } catch (JsonProcessingException ex) {
            Logger.getLogger(EndDeviceMessageParserService.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }
    
    @Test
    public void parseByteArrayToInteger() {
        try {
           
            ObjectMapper objectMapper = new ObjectMapper();
            // Parse the JSON payload
            JsonNode rootNode = objectMapper.readTree(jsonPayload);

            // Extract the decoded payload bytes array
            JsonNode decodedPayloadBytes = rootNode.path("uplink_message").path("decoded_payload").path("bytes");

            // Convert the byte array into a single integer value
            int decodedValue = 0;
            for (int i = 0; i < decodedPayloadBytes.size(); i++) {
                decodedValue = decodedPayloadBytes.get(i).asInt();
            }
            assertEquals(decodedValue,70);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      
    }

    
}
