/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Controllers;

import com.irrigation.Messages.MessageData.Device;
import com.irrigation.Messages.MessageData.Measurement;
import com.irrigation.Messages.MessageFormat.Code;
import com.irrigation.Messages.MessageFormat.MeasurementRequest;
import com.irrigation.Messages.MessageFormat.Payload;
import com.irrigation.iotserver.Services.ControllerHelperService;
import com.irrigation.iotserver.Services.DataAccess;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@Tag(name = "Measurements", description = "Operations related to retrieving measurement data")
public class MeasurementsController {

    private final DataAccess databaseManager;
    private final ControllerHelperService helperService;

    public MeasurementsController(DataAccess databaseManager, ControllerHelperService helperService){
        this.databaseManager = databaseManager;
        this.helperService = helperService;
    }

    @Operation(
        summary = "Retrieve measurement data for a device",
        description = """
            Returns measurement data for the specified device together with dates of measurements
            If 'from' and 'to' query params are provided, it returns data in that time range. 
            Otherwise, it returns all measurement of provided type
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Measurement data retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Payload.class),
                examples = {
                    @ExampleObject(
                        name = "Full range example",
                        value = """
                        {
                          "code": "SUCCESS",
                          "content": [
                            {
                              "value": "30",
                              "date": "2024-09-15 16:40:29"
                            },
                            {
                              "value": "21",
                              "date": "2024-09-15 21:40:29"
                            },
                            {
                              "value": "57",
                              "date": "2024-09-16 00:40:29"
                            }
                          ]
                        }
                        """
                    ),
                    @ExampleObject(
                        name = "Empty example",
                        value = """
                        {
                          "code": "SUCCESS",
                          "content": []
                        }
                        """
                    ),
                    @ExampleObject(
                        name = "Retrieve failure - auth token is wrong or measurement type do not exist",
                        value = """
                        {
                          "code": "FAILURE"
                        }
                        """
                    )
                }
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Server error",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Payload.class),
                examples = {
                    @ExampleObject(
                        value = """
                        {
                          "code": "FAILURE"
                        }
                        """
                    )
                }
            )
        )
    })
    @PostMapping("/get")
    public Payload getMeasurement(@Parameter(description = "Authorization token assigned to the user", required = true) @RequestHeader("Authorization") String token,  @RequestBody MeasurementRequest measurementRequest){
        System.out.println("from is: " + measurementRequest.getFrom() + " to is: " + measurementRequest.getTo());
        try {
            String username = helperService.checkAuthorisation(token);
            if(measurementRequest.getDevice()!= null && measurementRequest.getTo() != null){
                    return new Payload.PayloadBuilder()
                    .setMeasurements(getMeasurements(databaseManager.getMeasurementDataInRange(measurementRequest.getDevice(),measurementRequest.getFrom(),measurementRequest.getTo(),measurementRequest.getType())))
                    .setCode(Code.SUCCESS)
                    .build();
            }
            return new Payload.PayloadBuilder()
                    .setMeasurements(getMeasurements(databaseManager.getMeasurementDataQuery(measurementRequest.getDevice(),measurementRequest.getType())))
                    .setCode(Code.SUCCESS)
                    .build();
            
        } catch (SQLException ex) {
             Logger.getLogger(MeasurementsController.class.getName()).log(Level.SEVERE, null, ex);
             return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }
        
    }
    
    private ArrayList<Measurement> getMeasurements(List<String> measurements) throws SQLException {
        return IntStream.range(0, measurements.size() / 2) 
            .mapToObj(i -> new Measurement(
                measurements.get(2 * i),      
                measurements.get(2 * i + 1)  
            ))
            .collect(Collectors.toCollection(ArrayList::new));  
    }
    
}
