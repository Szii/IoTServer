/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Controllers;

import com.irrigation.Messages.MessageData.Device;
import com.irrigation.Messages.MessageFormat.Code;
import com.irrigation.Messages.MessageFormat.DeviceRequest;
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
@RequestMapping("/api/devices")
@Tag(name = "Devices", description = "Operations related to device management")
public class DevicesController {

    private final DataAccess databaseManager;
    private final ControllerHelperService helperService;

    public DevicesController(DataAccess databaseManager, ControllerHelperService helperService) {
        this.databaseManager = databaseManager;
        this.helperService = helperService;
    }

    @Operation(
        summary = "Get all devices for the authenticated user",
        description = "Retrieves all devices associated with the user identified by the Authorization token."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "List of devices returned in Payload's data field",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Payload.class),
                examples = {
                    @ExampleObject(
                        name = "Successful retrieval",
                        value = """
                        {
                          "code": "SUCCESS",
                          "data":[
                                    {
                                      "ID": "otaatest",
                                      "nickname": "Macešky_1",
                                      "group": "Sklenik_venku",
                                      "irrigationTime": 3,
                                      "threshold": "40",
                                      "humidity_date": "2025-03-01 09:05:30",
                                      "temperature_date": "2025-03-01 09:05:00",
                                      "temperature_value": 21,
                                      "humidity_value": 50
                                    },
                                    {
                                      "ID": "eui-2222",
                                      "nickname": "Macešky_2",
                                      "group": "Sklenik_venku",
                                      "irrigationTime": 3,
                                      "threshold": 35,
                                      "humidity_date": "2025-03-01 10:25:30",
                                      "temperature_date": "2025-03-01 10:25:00",
                                      "temperature_value": 22,
                                      "humidity_value": 40
                                    }
                               ]
                        }
                        """
                    ),
                    @ExampleObject(
                        name = "Failure to retrieve devices",
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
    @GetMapping("/getAll")
    public Payload getDevices(
        @Parameter(description = "Authorization token assigned to the user", required = true)
        @RequestHeader("Authorization") String token
    ) {
        try {
            String username = databaseManager.getTokenOwnerQuery(helperService.getToken(token));
            return new Payload.PayloadBuilder()
                    .setCode(Code.SUCCESS)
                    .setDevices(this.getAvailableDevicesBasedOnUsername(username))
                    .build();
        } catch (SQLException ex) {
            Logger.getLogger(DevicesController.class.getName()).log(Level.SEVERE, null, ex);
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }
    }

    @Operation(
        summary = "Get all devices in a specified group",
        description = "Retrieves all devices that belong to a specific group for the user identified by the Authorization token."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "List of devices in the specified group",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Payload.class),
                examples = {
                    @ExampleObject(
                        name = "Devices in group retrieval successful",
                        value = """
                        {
                          "code": "SUCCESS",
                          "data":[
                                    {
                                      "ID": "otaatest",
                                      "nickname": "Macešky_1",
                                      "group": "Sklenik_venku",
                                      "irrigationTime": 3,
                                      "threshold": 40,
                                      "humidity_date": "2025-03-01 09:05:30",
                                      "temperature_date": "2025-03-01 09:05:00",
                                      "temperature_value": 21,
                                      "humidity_value": 50
                                    },
                                    {
                                      "ID": "eui-2222",
                                      "nickname": "Macešky_2",
                                      "group": "Sklenik_venku",
                                      "irrigationTime": 3,
                                      "threshold": 35,
                                      "humidity_date": "2025-03-01 10:25:30",
                                      "temperature_date": "2025-03-01 10:25:00",
                                      "temperature_value": 22,
                                      "humidity_value": 40
                                    }
                               ]
                        }    
                        """
                    ),
                    @ExampleObject(
                        name = "Failure retrieving group devices - group not found or wrong auth token",
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
    @GetMapping("/getAllInGroup")
    public Payload getGroupDevices(
        @Parameter(description = "Authorization token", required = true)
        @RequestHeader("Authorization") String token,
        @Parameter(description = "Name of the group to retrieve devices from", required = true)
        @RequestParam("Group name") String groupName
    ) {
        try {
            String username = databaseManager.getTokenOwnerQuery(helperService.getToken(token));
            String groupID = databaseManager.getGroupID(username, groupName);
            return new Payload.PayloadBuilder()
                    .setCode(Code.SUCCESS)
                    .setDevices(this.getAvailableDevicesBasedOnGroup(groupID))
                    .build();
        } catch (SQLException ex) {
            Logger.getLogger(DevicesController.class.getName()).log(Level.SEVERE, null, ex);
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }
    }

    @Operation(
        summary = "Register a new device for the authenticated user",
        description = "Associates the specified device (by hardware or logical ID) with the user identified by the Authorization token."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Device registration result",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Payload.class),
                examples = {
                    @ExampleObject(
                        name = "Successful registration",
                        value = """
                        {
                          "code": "SUCCESS"
                        }
                        """
                    ),
                    @ExampleObject(
                        name = "Registration failed - device not found or wrong auth token",
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
    @PostMapping("/register")
    public Payload registerDevice(
        @Parameter(description = "Authorization token", required = true)
        @RequestHeader("Authorization") String token,
        @RequestBody DeviceRequest deviceRequest
    ) {
        try {
            String username = helperService.checkAuthorisation(token);
            Code resultCode = helperService.returnCodeSuccessOnTrue(
                    databaseManager.registerDeviceQuery(deviceRequest.getDevice(), username)
            );
            return new Payload.PayloadBuilder()
                   .setCode(resultCode)
                   .build();
        } catch (SQLException ex) {
            Logger.getLogger(DevicesController.class.getName()).log(Level.SEVERE, null, ex);
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }
    }

    @Operation(
        summary = "Unregister an existing device",
        description = "Removes the association between a device and the authenticated user."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Device unregistration result",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Payload.class),
                examples = {
                    @ExampleObject(
                        name = "Successful unregistration",
                        value = """
                        {
                          "code": "SUCCESS"
                        }
                        """
                    ),
                    @ExampleObject(
                        name = "Unregistration failed  - device not found or wrong auth token",
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
    @PostMapping("/unregister")
    public Payload unregistrerDevice(
        @Parameter(description = "Authorization token", required = true)
        @RequestHeader("Authorization") String token,
        @RequestBody DeviceRequest deviceRequest
    ) {
        try {
            String username = helperService.checkAuthorisation(token);
            databaseManager.unregisterDeviceQuery(deviceRequest.getDevice(), username);
            return new Payload.PayloadBuilder()
                   .setCode(Code.SUCCESS)
                   .build();
        } catch (SQLException ex) {
            Logger.getLogger(DevicesController.class.getName()).log(Level.SEVERE, null, ex);
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }
    }

    @Operation(
        summary = "Remove a device from its current group",
        description = "Sets the device's group to default/no group, for the authenticated user."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Removal from group result",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Payload.class),
                examples = {
                    @ExampleObject(
                        name = "Removal success",
                        value = """
                        {
                          "code": "SUCCESS"
                        }
                        """
                    ),
                    @ExampleObject(
                        name = "Removal failed - device not found or wrong auth token",
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
    @PostMapping("/removeFromGroup")
    public Payload removeDeviceFromGroup(
        @Parameter(description = "Authorization token", required = true)
        @RequestHeader("Authorization") String token,
        @RequestBody DeviceRequest deviceRequest
    ) {
        try {
            String username = helperService.checkAuthorisation(token);
            databaseManager.removeDeviceFromGroupQuery(deviceRequest.getDevice());
            return new Payload.PayloadBuilder().setCode(Code.SUCCESS).build();
        } catch (SQLException ex) {
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }
    }

    @Operation(
        summary = "Update device settings",
        description = """
            Updates device properties such as threshold, irrigation time, nickname, 
            or assigns/removes the device from a group. 
            Fields in the request body which are not null will be updated.
        """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Device update operation result",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Payload.class),
                examples = {
                    @ExampleObject(
                        name = "Update success",
                        value = """
                        {
                          "code": "SUCCESS"
                        }
                        """
                    ),
                    @ExampleObject(
                        name = "Update failure - device not found or wrong auth token",
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
    @PostMapping("/update")
    public Payload updateDevice(
        @Parameter(description = "Authorization token", required = true)
        @RequestHeader("Authorization") String token,
        @RequestBody DeviceRequest deviceRequest
    ) {
        System.out.println("UPDATE DEVICE ENDPOINT CALLED");
        try {
            System.out.println("Updating device: " 
                               + helperService.getToken(token) 
                               + deviceRequest.getDevice() 
                               + deviceRequest.getDeviceNickname() 
                               + deviceRequest.getIrrigationTime() 
                               + deviceRequest.getNewGroup());
            
            String username = helperService.checkAuthorisation(token);


            if (deviceRequest.getTreshold() != null) {
                databaseManager.setThresoldQuery(deviceRequest.getDevice(), deviceRequest.getTreshold());
            }

            if (deviceRequest.getIrrigationTime() != null) {
                databaseManager.setIrrigationTime(deviceRequest.getDevice(), deviceRequest.getIrrigationTime());
            }

            if (deviceRequest.getDeviceNickname() != null) {
                databaseManager.setSensorNickname(deviceRequest.getDevice(), deviceRequest.getDeviceNickname());
            }

            if (deviceRequest.getNewGroup() != null) {
                String newGroup = deviceRequest.getNewGroup();
                String groupID = databaseManager.getGroupID(username, newGroup);

                if (newGroup.isEmpty() || groupID.isEmpty()) {
                    databaseManager.removeDeviceFromGroupQuery(deviceRequest.getDevice());
                } else {
                    databaseManager.addDeviceToGroup(deviceRequest.getDevice(), groupID);
                }
            }

            return new Payload.PayloadBuilder().setCode(Code.SUCCESS).build();
        } catch (SQLException ex) {
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }
    }


    private ArrayList<Device> getAvailableDevicesBasedOnGroup(String group) {
        try {
            return getDevices(databaseManager.getAllDevicesInGroupQuery(group));
        } catch (SQLException ex) {
            Logger.getLogger(DevicesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }

    private ArrayList<Device> getAvailableDevicesBasedOnUsername(String username){
        try {
            return getDevices(databaseManager.getAvailableSensors(username));
        } catch (SQLException ex) {
            Logger.getLogger(DevicesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }

    private ArrayList<Device> getDevices(List<String> devices) throws SQLException {
        System.out.println(devices);
        ArrayList<Device> devicesList = new ArrayList<>();
        for (String sensorID : devices) {
            List<String> humidityMeasurement = databaseManager.getLastMeasurementQuery(sensorID, "TYPE_HUMIDITY");
            List<String> temperatureMeasurement = databaseManager.getLastMeasurementQuery(sensorID, "TYPE_TEMPERATURE");

            int availableGroupsSize = databaseManager.getDeviceGroupQuery(sensorID).size();
            String deviceGroup = "Default";
            if (availableGroupsSize != 0) {
                deviceGroup = databaseManager.getGroupNameQuery(
                                  databaseManager.getDeviceGroupQuery(sensorID).get(0)
                              );
            }

            Device device = new Device.DeviceBuilder()
                .setDeviceID(sensorID)
                .setNickname(databaseManager.getSensorNickname(sensorID))
                .setIrrigationTime(databaseManager.getIrrigationTime(sensorID))
                .setGroup(deviceGroup)
                .setHumidityValue(humidityMeasurement.get(0))
                .setThreshold(databaseManager.getThresoldQuery(sensorID))
                .setHumidityDate(humidityMeasurement.get(1))
                .setTemperatureDate(temperatureMeasurement.get(1))
                .setTemperatureValue(temperatureMeasurement.get(0))
                .build();

            devicesList.add(device);
        }
        return devicesList;
    }
}