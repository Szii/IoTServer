/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import com.irrigation.iotserver.Configuration.DatabaseConfig;
import com.irrigation.iotserver.Data.DatabaseConnector;
import com.irrigation.iotserver.Data.DatabaseManager;
import com.irrigation.iotserver.Data.ParsedMessage;
import com.irrigation.iotserver.Logic.OutboundManager;
import com.irrigation.iotserver.Main;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author brune
 */

@SpringBootTest(classes = {DatabaseConnector.class,DatabaseConfig.class})
public class EvaluatingMessagesTest {
    
   @Autowired
   DatabaseConnector connector;
   
    static DatabaseManager databaseManager;
    String databaseAddress;
    
    @BeforeEach
    public  void init() throws ClassNotFoundException, SQLException{ 
       databaseManager = new DatabaseManager(connector.connect());
    }
    

    
    
    @Test
    public void evaluateMessageBasedOnType() {
        ParsedMessage parsedMessage;
        try {
            System.out.println("Parsing message");
            parsedMessage = new ParsedMessage("",1,1);
            parsedMessage.setDeviceID("eui-2222");
            parsedMessage.setHumidity(50);
            System.out.println("checking if device exists");
            addDeviceIfNotExist(parsedMessage.getDeviceID());
            int storedThreshold = Integer.parseInt(databaseManager.getThresoldQuery(parsedMessage.getDeviceID()));
            if(storedThreshold >=  parsedMessage.getHumidity()){
                System.out.println("Sending message");
            }
            System.out.println("Saving measurements");
            saveMeasurement(parsedMessage);
            assertEquals(String.valueOf(50),databaseManager.getLastMeasurementQuery(parsedMessage.getDeviceID(),"TYPE_HUMIDITY").get(0));
        } catch (SQLException ex) {
            Logger.getLogger(OutboundManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

        
        
    }
    
    private void addDeviceIfNotExist(String deviceID) throws SQLException{
        if(!databaseManager.checkIfDeviceExistsQuery(deviceID)){
            databaseManager.addDeviceQuery(deviceID);
        }
    }
    
    private void saveMeasurement(ParsedMessage data) throws SQLException{
       databaseManager.addMeasurementQuery(data.getDeviceID(), String.valueOf(data.getHumidity()), getCurrentDateTime(),"");
    }
    
    private String getCurrentDateTime(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        System.out.println("Formatted Date and Time: " + formattedDateTime);
        return formattedDateTime;
    }
}
