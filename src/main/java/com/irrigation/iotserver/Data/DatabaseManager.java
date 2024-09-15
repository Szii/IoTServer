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
import java.util.logging.Level;
import java.util.logging.Logger;

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
      public String getPasswordQuery(String username) throws SQLException{
         String query = "SELECT* FROM users WHERE username = ?";
                PreparedStatement pst;
                pst = connection.prepareStatement(query);
                pst.setString(1, username);
                ResultSet result = pst.executeQuery();
                String value = "";
                if(result.next()){
                     value = result.getString("password");
                     pst.close();
                }
                else{
                     pst.close();
                }
                return value;
        }
  
       @Override
       public boolean getUserQuery(String name) throws SQLException{
            String query = "SELECT* FROM users WHERE username = " + "\"" +  name + "\"";
          PreparedStatement pst = connection.prepareStatement(query);
           ResultSet result = pst.executeQuery();
          
           String val = "";
           if(result.next()){
           val = result.getString("username");
            pst.close();
            return true;
    
          }
           else{
               pst.close();
               return false;
           }
         }
         
         
         @Override
        public boolean addUserQuery(String username,String password){
            try {
                String query = " insert into users (username,password)"
                        + " values (?, ?)";
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setString (1, username);
                pst.setString (2, password);

                pst.executeUpdate();
                pst.close();

                return true;
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
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
              String query = "SELECT* FROM devices WHERE device_ID = " + "\"" +  sensor_ID + "\"";
               PreparedStatement pst = connection.prepareStatement(query);
               ResultSet result = pst.executeQuery();
                 if(result.next()){
                     String val = result.getString("device_treshold");
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
              String query = " UPDATE devices SET device_treshold = ? WHERE device_ID = " + "\"" + sensor_ID+ "\"";
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
          public void addMeasurementQuery(String sensor_ID,String measure,String date)throws SQLException{
            int maxCount = 10000;  
              
           String countQuery = "SELECT COUNT(*) AS rowcount FROM measurements";   
           PreparedStatement ps = connection.prepareStatement(countQuery);
           ResultSet result = ps.executeQuery();  
           result.next();
           int count = result.getInt("rowcount");
           result.close();
           if(count > maxCount){
           
            ps = connection.prepareStatement("TRUNCATE measurements");
            ps.executeUpdate();
            
            ps = connection.prepareStatement("ALTER TABLE measurements AUTO_INCREMENT = 1");
            ps.execute();  
            
            ps.close();
           }
              
          String query = " insert into measurements (device_ID,date,value)"
             + " values (?, ?,?)";
           PreparedStatement pst = connection.prepareStatement(query);
           pst.setString (1, sensor_ID);
           pst.setString (2, date);
           pst.setString (3, measure);
        
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
         public String getLastMeasurementQuery(String sensor_ID) throws SQLException{
            String query = "SELECT* FROM measurements WHERE device_ID = " + "\"" +  sensor_ID + "\"";
          PreparedStatement pst = connection.prepareStatement(query);
           ResultSet result = pst.executeQuery();
        
           while(result.next()){
               if (result.isLast()){
                     String val = result.getString("value");                          
                     pst.close();
                     return val;
               }      
           }
           return "";
        }
         @Override
         public ArrayList<String> getAllDevicesInGroupQuery(String group) throws SQLException{
             ArrayList<String> device_IDs = new ArrayList();
             String query = "SELECT * FROM devices WHERE group_ID = ?";
              PreparedStatement pst = connection.prepareStatement(query);
              pst.setString(1, group);
              ResultSet result = pst.executeQuery();
                  while (result.next()){
                        device_IDs.add(result.getString("device_ID"));
             }
             pst.close();  
             return device_IDs;
         }
         @Override
         public boolean addDeviceToGroup(String device_ID,String group) throws SQLException{
             System.out.println("adding device " + device_ID + " to group: " + group);
                
             
             String query = " UPDATE devices SET group_ID = ? WHERE device_ID = " + "\"" + device_ID+ "\"";
             
             PreparedStatement pst = connection.prepareStatement(query); 
              try {    
                pst.setString (1,group);
                pst.executeUpdate();
                pst.close();
                return true;
              } 
              catch (SQLException ex) {
                 System.out.println(ex);
                 return false;

             }
         }
         @Override
         public boolean removeDeviceFromGroupQuery(String device_ID) throws SQLException{
             String query = " UPDATE devices SET device_group = ? WHERE device_ID = " + "\"" + device_ID+ "\"";
             
             PreparedStatement pst = connection.prepareStatement(query); 
              try {    
                pst.setString (1,"");
                pst.executeUpdate();
                pst.close();
                return true;
              } 
              catch (SQLException ex) {
                 System.out.println(ex);
                 return false;

             }
         }
         @Override
         public boolean addGroupQuery(String group_ID,String username) throws SQLException{
              String query = " insert into groups (groupname,username)"
                        + " values (?, ?)";
              PreparedStatement pst = connection.prepareStatement(query);
              
              try {    
                pst.setString (1, group_ID);
                pst.setString (2, username);
                pst.executeUpdate();
                pst.close();
              } 
              catch (SQLException ex) {
                 System.out.println(ex);
                 return false;
             }
               System.out.println("Sensor added returning true");
             return true;
         }
         @Override
         public boolean removeGroupQuery(String username, String group_name) throws SQLException{
             String group_ID = getGroupID(username,group_name);
             String query1 = " UPDATE devices SET group_ID = ? WHERE group_ID = " + "\"" + group_ID+ "\"";
              PreparedStatement pst1 = connection.prepareStatement(query1);
              try {    
                pst1.setString (1,null);
                pst1.executeUpdate();
                pst1.close();
              //  out.println("");
                System.out.println("Device group set to null" );
              } 
              catch (SQLException ex) {
                  return false;
             }
             
             
             
             
              String query2 ="DELETE from diplomova_prace_db.groups WHERE group_name = " + "\"" + group_name+ "\" AND username = " + "\"" + username+ "\"";
              PreparedStatement pst = connection.prepareStatement(query2);
              
              try {    
              
                pst.executeUpdate();
                System.out.println("Group :" + group_name + " with user " + username + " removed");
              } 
              catch (SQLException ex) {
                System.out.println(ex);
                return false;
             
             }
             return true;
         }
         
         @Override
         public String getGroupID(String username, String group) throws SQLException{
             String query = "SELECT* FROM `groups` WHERE username = " + "\"" +  username + "\" AND group_name = " + "\"" +  group + "\"";
             PreparedStatement pst = connection.prepareStatement(query);
             ResultSet result = pst.executeQuery();
             String val;
             if (result.next()){
                     val = result.getString("group_ID");
                     return val;
             } 
             else return "";
         }
         
         @Override
         public ArrayList<String> getDeviceGroupQuery(String sensor_ID) throws SQLException{
             ArrayList<String> group = new ArrayList();
             System.out.println("Finding group for device: " + sensor_ID );
               String guery2 = "SELECT measurements.value,measurements.date FROM measurements LEFT JOIN devices ON devices.device_ID = measurements.device_ID WHERE devices.device_ID = ?";
               String query = "SELECT * FROM `groups` LEFT JOIN `devices` ON `groups`.group_ID = `devices`.group_ID WHERE `devices`.device_ID = ?";
              PreparedStatement pst = connection.prepareStatement(query);
              pst.setString(1, sensor_ID);
               ResultSet result = pst.executeQuery();
               String val = "";
                   while (result.next()){
                        val = result.getString("group_ID");
                        group.add(val);
                        val = result.getString("group_name");
                        group.add(val);
                   }
                   pst.close();
                   if(group.isEmpty()){
                       group.add("");
                       group.add("");
                   }
                   return group;  
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
             String query = "SELECT* FROM user_device WHERE device_ID = " + "\"" +  sensorID + "\"";
          PreparedStatement pst = connection.prepareStatement(query);
           ResultSet result = pst.executeQuery();
               if (result.next()){
                     String val = result.getString("username");
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
        public boolean registerDeviceQuery(String sensor_ID,String username) throws SQLException{
            
            String query = " insert into user_device (user,device)"
                        + " values (?, ?)";
              PreparedStatement pst = connection.prepareStatement(query);
              pst = connection.prepareStatement(query);
              
              try {    
                pst.setString (1, username);
                pst.setString (2, sensor_ID);
                pst.executeUpdate();
                pst.close();
              } 
              catch (SQLException ex) {
                 System.out.println(ex);
                 return false;
             }
               System.out.println("Sensor added returning true");
             return true;
        }
        
        @Override
        public ArrayList<String> getAvailableSensors(String username) throws SQLException{
            
         ArrayList<String> sensors = new ArrayList();
         
         
         String query = "SELECT * FROM `user_device` LEFT JOIN `devices` ON `devices`.device_ID = user_device.device"
                 + " LEFT JOIN `users` ON `users`.username = user_device.user"
                 + " WHERE username = " + "\"" + username  + "\"";

          PreparedStatement pst = connection.prepareStatement(query);
           ResultSet result = pst.executeQuery();
               while (result.next()){
                    String val = result.getString("device_ID");
                         System.out.println("got sensor");
                         sensors.add(val);      
               }
               pst.close();
               System.out.println("No other registered sensors for unit: " + username);
               return sensors;
        }
        
        @Override
        public void unregisterDeviceQuery(String sensor_ID, String username) throws SQLException {
            String query ="DELETE from user_device WHERE device = " + "\"" + sensor_ID+ "\" AND user = " + "\"" + username+ "\"";
              PreparedStatement pst = connection.prepareStatement(query);
              
              try {    
              
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
              String query = " UPDATE devices SET device_name = ? WHERE device_ID = " + "\"" + sensor_ID+ "\"";
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
              String query = "SELECT* FROM devices WHERE device_ID = " + "\"" + sensor_ID  + "\"";
              PreparedStatement pst = connection.prepareStatement(query);
               ResultSet result = pst.executeQuery();
               String val = "";
                   while (result.next()){
                        val = result.getString("device_name");
                        if(val == null){
                            val = "";
                        }
                         if(val.equals("")){  
                             val = result.getString("device_ID");
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
        System.out.println("getting measurement for sensor" + sensorID);
        ArrayList<String> measuredData = new ArrayList<>();
        //String query = "SELECT* FROM measurment WHERE sensor_ID = ?";
          String query = "SELECT measurements.value,measurements.date FROM measurements LEFT JOIN devices ON devices.device_ID = measurements.device_ID WHERE devices.device_ID = ?";
              PreparedStatement pst = connection.prepareStatement(query);
              pst.setString(1, sensorID);
               ResultSet result = pst.executeQuery();
               String val = "";
                   while (result.next()){
                        val = result.getString("value");
                        measuredData.add(val);
                        val = result.getString("date");
                        measuredData.add(val);
                   }
                   pst.close();
                   if(measuredData.isEmpty()){
                       measuredData.add("");
                       measuredData.add("");
                   }
                   return measuredData;    
    }
    
    @Override
    public ArrayList<String> getMeasurementDataInRange(String sensorID,String from, String to) throws SQLException {
        ArrayList<String> measuredData = new ArrayList<>();
        String query = "SELECT* FROM measurements WHERE device_ID = ? AND date BETWEEN ? AND ?";
        
              PreparedStatement pst = connection.prepareStatement(query);
              pst.setString(1, sensorID);
              pst.setString(2, from);
              pst.setString(3, to);
               ResultSet result = pst.executeQuery();
               String val = "";
                   while (result.next()){
                        val = result.getString("value");
                        measuredData.add(val);
                        val = result.getString("date");
                        measuredData.add(val);
                   }
                   pst.close();
                   return measuredData;    
    }
    


    @Override
    public String getIrrigationTime(String sensorID) throws SQLException {
      String query = "SELECT* FROM devices WHERE device_ID = " + "\"" +  sensorID + "\"";
               PreparedStatement pst = connection.prepareStatement(query);
               ResultSet result = pst.executeQuery();
                 if(result.next()){
                     String val = result.getString("device_irrigationTime");
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
            String query = " UPDATE devices SET device_irrigationTime = ? WHERE device_ID = " + "\"" + sensorID+ "\"";
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
    
    @Override
    public ArrayList<String> getGroupsQuery(String username) throws SQLException{
              String query = "SELECT* FROM diplomova_prace_db.groups WHERE username = " + "\"" +  username + "\"";
               PreparedStatement pst = connection.prepareStatement(query);
               ResultSet result = pst.executeQuery();
               ArrayList<String> groups = new ArrayList();
               while (result.next()){
                    String val = result.getString("group_name");
                    System.out.println("got group: " + val);
                    groups.add(val);      
               }
               pst.close();
               return groups;
    }
    
    @Override
    public boolean changeGroupName(String username, String oldGroup,String newGroup) throws SQLException {
        
            String query = " UPDATE diplomova_prace_db.groups SET group_name = ? WHERE username = " + "\"" + username + "\" AND group_name = " + "\"" + oldGroup + "\"";
            PreparedStatement pst = connection.prepareStatement(query);
            try {
                pst.setString (1, newGroup);
                pst.executeUpdate();
                pst.close();
                return true;
                       
            }catch (SQLException ex) {
              return false;
            }    
    }

    @Override
    public boolean createGroupQuery(String username, String group) throws SQLException {
          String query1 = "SELECT* FROM diplomova_prace_db.groups WHERE username = " + "\"" + username + "\" AND group_name = " + "\"" + group + "\"";
          PreparedStatement pst1 = connection.prepareStatement(query1);
          ResultSet result = pst1.executeQuery();
          if(result.next()){
              pst1.close();
              return false;
          }
          pst1.close();
                  
          String query2 = " insert into diplomova_prace_db.groups (group_name,username)"
             + " values (?, ?)";
           PreparedStatement pst2 = connection.prepareStatement(query2);
           pst2.setString (1, group);
           pst2.setString (2, username);
        
           pst2.executeUpdate();
           pst2.close();
           return true;
    }
    
       @Override
       public String getTokenOwnerQuery(String token) throws SQLException{
            String query = "SELECT* FROM users WHERE token = " + "\"" +  token + "\"";
          PreparedStatement pst = connection.prepareStatement(query);
           ResultSet result = pst.executeQuery();
          
           String val = "";
           if(result.next()){
           val = result.getString("username");
            pst.close();
            return val;
    
          }
           else{
               pst.close();
               return "";
           }
         }
       
       @Override
       public void setTokenQuery(String username,String token) throws SQLException{
             String query = " UPDATE users SET token = ? WHERE username = " + "\"" + username + "\"";
             PreparedStatement pst = connection.prepareStatement(query);
                        try {
                            pst = connection.prepareStatement(query);
                         //   pst.setString (1, unit_ID);
                            pst.setString (1, token);
                            pst.executeUpdate();
                            pst.close();
                        //    out.println("");
                        } catch (SQLException ex) {
                            System.out.println(ex);    
                        } 
       }

    @Override
    public void addDeviceQuery(String deviceID) throws SQLException {
        try {
                String query = " insert into devices (device_ID)"
                        + " values (?)";
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setString (1, deviceID);
                pst.executeUpdate();
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @Override
    public boolean checkIfDeviceExistsQuery(String deviceID) throws SQLException {
               String query = "SELECT* FROM devices WHERE device_ID = " + "\"" + deviceID  + "\"";
                PreparedStatement pst = connection.prepareStatement(query);
                ResultSet result = pst.executeQuery();

                if (result.next()){   
                     return true;
                }
                else{
                    return false;
             }
    }
    
}
