/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Controllers;

import com.irrigation.Messages.MessageFormat.Code;
import com.irrigation.Messages.MessageFormat.Payload;
import com.irrigation.iotserver.Services.ControllerHelperService;
import com.irrigation.iotserver.Services.DataAccess;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author brune
 */
@RestController
@RequestMapping("/api/measurement")
public class MeasurementsController {
     private final DataAccess databaseManager;
     private final ControllerHelperService helperService;
     
    public MeasurementsController(DataAccess databaseManager,  ControllerHelperService helperService){
       this.databaseManager =  databaseManager;
       this.helperService = helperService;
    }
    
      @GetMapping("/get")
    public Payload getMeasurement(@RequestHeader("Authorization") String token,  @RequestParam (required = true)  String device, @RequestParam (required = false)  String from,@RequestParam (required = false)  String to, @RequestParam (required = true)  String type){
        System.out.println("from is: " + from + " to is: " + to);
        try {
            String username = helperService.checkAuthorisation(token);
            if(from!= null && to != null){
                    return new Payload.PayloadBuilder()
                    .setContent(databaseManager.getMeasurementDataInRange(device,from,to,type))
                    .setCode(Code.SUCCESS)
                    .build();
            }
            return new Payload.PayloadBuilder()
                    .setContent(databaseManager.getMeasurementDataQuery(device,type))
                    .setCode(Code.SUCCESS)
                    .build();
            
        } catch (SQLException ex) {
             Logger.getLogger(MeasurementsController.class.getName()).log(Level.SEVERE, null, ex);
             return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }
        
    }
}
