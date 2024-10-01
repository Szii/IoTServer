/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Services;

import com.irrigation.Messages.MessageFormat.Code;
import java.sql.SQLException;
import org.springframework.stereotype.Service;

/**
 *
 * @author brune
 */
@Service
public class ControllerHelperService {
    
    private final DataAccess databaseManager;
    
    ControllerHelperService(DataAccess databaseManager){
        this.databaseManager = databaseManager;
    }
    
    
    public Code returnCodeSuccessOnTrue(boolean statement){
        return statement ? Code.SUCCESS : Code.FAILURE;
    } 
    
    public String getToken(String token){
             if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
             return token;
    }
        
    public String checkAuthorisation(String token) throws SQLException{
            String username =  databaseManager.getTokenOwnerQuery(getToken(token));
            if(username.equals("")){
                throw new SQLException("Token not found");
            }
            else{
                return username;
            }
    }
    
}
