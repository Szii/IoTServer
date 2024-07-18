/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author brune
 */
public class DatabaseManager implements DataAccess {
    private final Connection connection;
    
    public DatabaseManager(Connection connection){
        this.connection = connection;
    }
    
    
        @Override
      public boolean confirmLoginQuery(String name,String password) throws SQLException{
         String query = "SELECT* FROM users WHERE name = ?" + "AND" + " password = ?";
                PreparedStatement pst;
                pst = connection.prepareStatement(query);
                pst.setString(1, name);
                pst.setString(2,password);     
                ResultSet result = pst.executeQuery();
 
                String val = "";

                if(result.next()){
                     pst.close();
                     return true;
                }
                else{
                     pst.close();
                     return false;
                }
         
        }
  
       @Override
       public String getUserQuery(String name) throws SQLException{
            String query = "SELECT* FROM users WHERE name = " + "\"" +  name + "\"";
          PreparedStatement pst = connection.prepareStatement(query);
           ResultSet result = pst.executeQuery();
          
           String val = "";
           if(result.next()){
           val = result.getString("name");
            pst.close();
            return val; 
    
          }
           else{
               pst.close();
               return "error";
           }
         }
         
         
         @Override
        public void addUserQuery(String name,String password)throws SQLException{
           String query = " insert into users (username,password)"
             + " values (?, ?)";
           PreparedStatement pst = connection.prepareStatement(query);
           pst.setString (1, name);
           pst.setString (2, password);
          
           pst.executeUpdate();
           pst.close();
       
          }
         
        @Override
        public void registerUnitQuery(String unit_ID,String user){
             
            try { 

                String query;
                query = " SELECT* FROM units WHERE unit_ID = " + "\"" + unit_ID + "\"";
                PreparedStatement pst;
                pst = connection.prepareStatement(query);
                ResultSet result = pst.executeQuery();
             
                if(result.next()){
                String val = "";
                    val = result.getString("user");
            
                    if(val == null){
                        val = "";
                    }
                    if(!val.equals("")){
                       // out.println("error");
                    }
                    else{
                       String query1 = " UPDATE units SET user = ? WHERE unit_ID = " + "\"" + unit_ID + "\"";


                        try {
                          
                            pst = connection.prepareStatement(query1);
                         //   pst.setString (1, unit_ID);
                            pst.setString (1, user);
                            pst.executeUpdate();
                         
                            pst.close();
                        //    out.println("");
                        } catch (SQLException ex) {
                         //   out.println("error");
                            System.out.println(ex);
                        }
                    }

                }      
            } 
            catch (SQLException ex) {}
        }
        
        @Override
        public ArrayList<String> getRegisteredUnitsQuery(String user) throws SQLException{
                ArrayList<String> registeredUnits =new ArrayList();
                String query;
                query = " SELECT* FROM units WHERE user = " + "\"" + user + "\"";
                PreparedStatement pst;
                pst = connection.prepareStatement(query);
                ResultSet result = pst.executeQuery();
           
                String val = "";
                while(result.next()){
                    val = result.getString("unit_ID");  
                    System.out.println("Found registered unit for user " + user + " Unit id: " + val);
                    registeredUnits.add(val);
                }
                pst.close();
                return registeredUnits;
        }

        
        
        @Override
        public String getUnitQuery(String name) throws SQLException{
            String query = "SELECT* FROM units WHERE unit_ID = " + "\"" +  name + "\"";
          PreparedStatement pst = connection.prepareStatement(query);
           ResultSet result = pst.executeQuery();
       
          if(result.next() == true){
     

            String val = result.getString("unit_ID");
            System.out.println("Found unit: " + val);
            if(val != ""){
                 pst.close();
                 return val;
            }
         }
          else{
                    pst.close();
                 return "error";
          }   
          return "";
        
         }
           
           @Override
          public boolean checkIfUnitIsRegisteredQuery(String unit_ID) throws SQLException{
              System.out.println("checking if unit " + unit_ID +" exists");
            String query = "SELECT* FROM units WHERE unit_ID = " + "\"" +  unit_ID + "\"";
          PreparedStatement pst = connection.prepareStatement(query);
           ResultSet result = pst.executeQuery();
          String val = null;
          
          while(result.next()){
             val = result.getString("user");
            if(val == null){
              System.out.println("unit is not registered");
              pst.close();
              return false;
                }
            else{
              System.out.println("unit is registered");
              pst.close();
              return true;
            }
                   
           }
          pst.close();
          return false;
         }
        @Override
        public boolean checkUnitQuery(String unit_ID) throws SQLException{
              System.out.println("checking if unit " + unit_ID +" exists");
            String query = "SELECT* FROM units WHERE unit_ID = " + "\"" +  unit_ID + "\"";
          PreparedStatement pst = connection.prepareStatement(query);
           ResultSet result = pst.executeQuery();
         
          if(result.next() == true){
              pst.close();
               return true;
 
            }
          else{
              pst.close();
               return false;
          }   
           
         }
          @Override
         public String getThresoldQuery(String sensor_ID) throws SQLException{
              String query = "SELECT* FROM sensors WHERE sensor_ID = " + "\"" +  sensor_ID + "\"";
               PreparedStatement pst = connection.prepareStatement(query);
               ResultSet result = pst.executeQuery();
                 if(result.next()){
                     String val = result.getString("thresold");
                     if(val == null){
                         val = "-1";
                     }
                     pst.close();
                     return val;
                 }
                 else{
                     pst.close();
                     return "error";
                 }
             
          }
         
          @Override
           public void setThresoldQuery(String sensor_ID,String thresold) throws SQLException{
              String query = " UPDATE sensors SET thresold = ? WHERE sensor_ID = " + "\"" + sensor_ID+ "\"";
               PreparedStatement pst = connection.prepareStatement(query);         
              try {    
                pst.setString (1, thresold);
                pst.executeUpdate();
                pst.close();
              } 
              catch (SQLException ex) {
                System.out.println(ex);
             }
              pst.close();
          }
          
          @Override
          public void addUnitQuery(String unit_ID)throws SQLException{
          String query = " insert into units (unit_ID,user)"
             + " values (?, ?)";
           PreparedStatement pst = connection.prepareStatement(query);
           pst.setString (1, unit_ID);
           pst.setString (2, null);
        
           pst.executeUpdate();
           pst.close();
          }
          
          @Override
          public void addMeasurmentQuery(String sensor_ID,String measure,String date)throws SQLException{
            int maxCount = 10000;  
              
           String countQuery = "SELECT COUNT(*) AS rowcount FROM measurment";   
           PreparedStatement ps = connection.prepareStatement(countQuery);
           ResultSet result = ps.executeQuery();  
           result.next();
           int count = result.getInt("rowcount");
           result.close();
           if(count > maxCount){
           
            ps = connection.prepareStatement("TRUNCATE measurment");
            ps.executeUpdate();
            
            ps = connection.prepareStatement("ALTER TABLE measurment AUTO_INCREMENT = 1");
            ps.execute();  
            
            ps.close();
           }
              
          String query = " insert into measurment (sensor_ID,moisture,dateTime)"
             + " values (?, ?,?)";
           PreparedStatement pst = connection.prepareStatement(query);
           pst.setString (1, sensor_ID);
           pst.setString (2, measure);
           pst.setString (3, date);
        
           pst.executeUpdate();
           pst.close();
          }
                
        @Override     
        public void unregisterUnitQuery(String unit_ID)throws SQLException{
         
            String query=  "UPDATE units SET user = ?, nickname = ? WHERE unit_ID = ?";
              PreparedStatement pst = connection.prepareStatement(query);
              
              try {    
                pst.setString (1, null);
                pst.setString (2, null);
                pst.setString (3, unit_ID);
                pst.executeUpdate();
              
                pst.close();
              } 
              catch (SQLException ex) {
                 System.out.println(ex);
             }
        }
           
         @Override
         public String lastQuery(String sensor_ID) throws SQLException{
            String query = "SELECT* FROM measurment WHERE sensor_ID = " + "\"" +  sensor_ID + "\"";
          PreparedStatement pst = connection.prepareStatement(query);
           ResultSet result = pst.executeQuery();
        
           while(result.next()){
               if (result.isLast()){
                     String val = result.getString("moisture");                          
                     pst.close();
                     return val;
               }      
           }
           return "";
        }
         
         @Override
         public boolean doesSensorBelongsToUnit(String sensor_ID,String unit_ID) throws SQLException{
         String query = "SELECT* FROM sensors WHERE sensor_ID = " + "\"" +  sensor_ID + "\"";
          PreparedStatement pst = connection.prepareStatement(query);
           ResultSet result = pst.executeQuery();
           System.out.println("Checking if sensor: " + sensor_ID + "belongs to unit: " + unit_ID);
           String val = "";
               if (result.next()){
                     val = result.getString("sensor_Unit_ID");
                     if(val == null){
                         return false;
                     }
                     if(val.equals(unit_ID)){
                         pst.close();
                         return true;
                     }
                     else{
                         pst.close();
                         return false;
                     }
               }      

          pst.close();
          return false;
         }
         
         @Override
         public String getSensorOwner(String sensorID) throws SQLException{
             String query = "SELECT* FROM sensors WHERE sensor_ID = " + "\"" +  sensorID + "\"";
          PreparedStatement pst = connection.prepareStatement(query);
           ResultSet result = pst.executeQuery();
               if (result.next()){
                     String val = result.getString("sensor_Unit_ID");
                     if(val == null){
                         val = "";
                     }
                     return val;
    
               }
               return "";
         }
         
        @Override
        public boolean isSensorHiddenFromUnit(String sensor_ID,String unit_ID) throws SQLException{
         String query = "SELECT* FROM sensors WHERE sensor_ID = " + "\"" +  sensor_ID + "\"";
          PreparedStatement pst = connection.prepareStatement(query);
           ResultSet result = pst.executeQuery();
               if (result.next()){
                     String val = result.getString("sensor_Unit_ID");
                     if(val == null){
                         val = "";
                     }                
                     if(val.equals("")){
                         pst.close();
                         return false;
                     }
                     else{
                         pst.close();
                         return true;
                     }
               }  
               else{
                    pst.close();
                    return true;
               }
         }
        
        @Override
        public void registerSensor(String sensor_ID,String sensor_unit_ID) throws SQLException{
             String query = " UPDATE sensors SET sensor_unit_ID = ? WHERE sensor_ID = " + "\"" + sensor_ID+ "\"";
              PreparedStatement pst = connection.prepareStatement(query);
              pst = connection.prepareStatement(query);
              
              try {    
                pst.setString (1, sensor_unit_ID);
                pst.executeUpdate();
                pst.close();
              } 
              catch (SQLException ex) {
                 System.out.println(ex);
             }
        }
        
        @Override
        public ArrayList<String> getRegisteredAvailableSensors(String unit_ID) throws SQLException{
            
         ArrayList<String> sensors = new ArrayList();

            
         String query = "SELECT* FROM sensors WHERE sensor_unit_ID = " + "\"" + unit_ID  + "\"";
          PreparedStatement pst = connection.prepareStatement(query);
           ResultSet result = pst.executeQuery();
               while (result.next()){
                    String val = result.getString("sensor_ID");
                         System.out.println("got sensor");
                         sensors.add(val);      
               }
               pst.close();
               System.out.println("No other registered sensors for unit: " + unit_ID);
               return sensors;
        }
        
        @Override
        public void unregisterSensorQuery(String sensor_ID) throws SQLException {
             String query = " UPDATE sensors SET sensor_unit_ID = ?, nickname = ?,thresold = ?, irrigationTime = ? WHERE sensor_ID = ?";
              PreparedStatement pst = connection.prepareStatement(query);
              
              try {    
              
                pst.setString (1,null);
                pst.setString (2,null);
                pst.setString (3,"-1");
                pst.setString (4,"3");
                pst.setString (5,sensor_ID);
                pst.executeUpdate();
                System.out.println("Sensor unregistered");
              } 
              catch (SQLException ex) {
                  System.out.println("Error");
                 System.out.println(ex);
             }
        }
        
        @Override
        public void setSensorNickname (String sensor_ID,String nickname) throws SQLException{
              String query = " UPDATE sensors SET nickname = ? WHERE sensor_ID = " + "\"" + sensor_ID+ "\"";
              PreparedStatement pst = connection.prepareStatement(query);
              
              try {    
                pst.setString (1,nickname);
                pst.executeUpdate();
                pst.close();
              //  out.println("");
                System.out.println("Sensor nickname set: " + nickname );
              } 
              catch (SQLException ex) {
                  System.out.println("Error");
               // out.println("error");
                 System.out.println(ex);
             }
        }
        @Override
         public void setUnitNickname (String unit_ID,String nickname) throws SQLException{
              String query = " UPDATE units SET nickname = ? WHERE unit_ID = " + "\"" + unit_ID+ "\"";
              PreparedStatement pst = connection.prepareStatement(query);
              
              try {    
                pst.setString (1,nickname);
                pst.executeUpdate();
                pst.close();
               // out.println("");
                System.out.println("Sensor nickname set: " + nickname );
              } 
              catch (SQLException ex) {
                  System.out.println("Error");
             //   out.println("error");
                 System.out.println(ex);
             }
        }
          @Override
           public String getSensorNickname (String sensor_ID) throws SQLException{
              String query = "SELECT* FROM sensors WHERE sensor_ID = " + "\"" + sensor_ID  + "\"";
              PreparedStatement pst = connection.prepareStatement(query);
               ResultSet result = pst.executeQuery();
               String val = "";
                   while (result.next()){
                        val = result.getString("nickname");
                        if(val == null){
                            val = "";
                        }
                         if(val.equals("")){  
                             val = result.getString("sensor_ID");
                         }

                   }
                   pst.close();
                   return val;
                 
            }
           
           @Override
            public String getUnitNickname (String unit_ID) throws SQLException{
                String query = "SELECT* FROM units WHERE unit_ID = " + "\"" + unit_ID  + "\"";
                PreparedStatement pst = connection.prepareStatement(query);
                ResultSet result = pst.executeQuery();
                String val = "";
                   if (result.next()){   
                        val = result.getString("nickname");
                        if(val == null){  
                             val = result.getString("unit_ID");
                             pst.close();
                         }
                   }

                   return val;
            }

    @Override
    public ArrayList<String> getMeasurementDataQuery(String sensorID) throws SQLException {
        ArrayList<String> measuredData = new ArrayList<>();
        //String query = "SELECT* FROM measurment WHERE sensor_ID = ?";
          String query = "SELECT measurment.moisture,measurment.dateTime FROM measurment LEFT JOIN sensors ON sensors.sensor_ID = measurment.sensor_ID WHERE sensors.sensor_ID = ?";
              PreparedStatement pst = connection.prepareStatement(query);
              pst.setString(1, sensorID);
               ResultSet result = pst.executeQuery();
               String val = "";
                   while (result.next()){
                        val = result.getString("moisture");
                        measuredData.add(val);
                        val = result.getString("dateTime");
                        measuredData.add(val);
                   }
                   pst.close();
                   return measuredData;    
    }
    
    @Override
    public ArrayList<String> getMeasurementDataInRange(String sensorID,String from, String to) throws SQLException {
        ArrayList<String> measuredData = new ArrayList<>();
        String query = "SELECT* FROM measurment WHERE sensor_ID = ? AND dateTime BETWEEN ? AND ?";
        
              PreparedStatement pst = connection.prepareStatement(query);
              pst.setString(1, sensorID);
              pst.setString(2, from);
              pst.setString(3, to);
               ResultSet result = pst.executeQuery();
               String val = "";
                   while (result.next()){
                        val = result.getString("moisture");
                        measuredData.add(val);
                        val = result.getString("dateTime");
                        measuredData.add(val);
                   }
                   pst.close();
                   return measuredData;    
    }

    @Override
    public String getIrrigationTime(String sensorID) throws SQLException {
      String query = "SELECT* FROM sensors WHERE sensor_ID = " + "\"" +  sensorID + "\"";
               PreparedStatement pst = connection.prepareStatement(query);
               ResultSet result = pst.executeQuery();
                 if(result.next()){
                     String val = result.getString("irrigationTime");
                     if(val == null){
                         val = "-1";
                     }
                     pst.close();
                     return val;
                 }
                 else{
                     pst.close();
                     return "error";
                 }
    }

    @Override
    public void setIrrigationTime(String sensorID, String value) throws SQLException {
        System.out.println("setting irrigation time, sensorID : " + sensorID + "value : " + value);
            String query = " UPDATE sensors SET irrigationTime = ? WHERE sensor_ID = " + "\"" + sensorID+ "\"";
               PreparedStatement pst = connection.prepareStatement(query);         
              try {    
                pst.setString (1, value);
                pst.executeUpdate();
                pst.close();
              } 
              catch (SQLException ex) {
                System.out.println(ex);
             }
              pst.close();
    }

 
    
    
    
}
