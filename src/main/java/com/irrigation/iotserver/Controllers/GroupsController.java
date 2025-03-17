/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Controllers;

import com.irrigation.Messages.MessageFormat.Code;
import com.irrigation.Messages.MessageFormat.GroupRequest;
import com.irrigation.Messages.MessageFormat.Payload;
import com.irrigation.iotserver.Services.ControllerHelperService;
import com.irrigation.iotserver.Services.DataAccess;
import java.sql.SQLException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 *
 * @author brune
 */



@RestController
@RequestMapping("/api/groups")
@Tag(name = "Groups", description = "Operations related to group management")
public class GroupsController {

    private final ControllerHelperService helperService;
    private final DataAccess databaseManager;

    public GroupsController(DataAccess databaseManager, ControllerHelperService helperService){
        this.databaseManager = databaseManager;
        this.helperService = helperService;
    }

    @Operation(
        summary = "Retrieve all groups of the authenticated user",
        description = "Returns a list of group names associated with the user identified by the authorization token."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful retrieval of groups",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Payload.class),
                examples = {
                    @ExampleObject(
                        name = "List of groups",
                        value = """
                        {
                          "code": "SUCCESS",
                          "content": [
                            "Group1",
                            "Group2",
                            "Group3"
                          ]
                        }
                        """
                    ),
                    @ExampleObject(
                        name = "No groups found",
                        value = """
                        {
                          "code": "SUCCESS",
                          "content": []
                        }
                        """
                    ),
                     @ExampleObject(
                        name = "Retrieve failure - auth token is wrong",
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
                        name = "Failure response",
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
    @GetMapping("/get")
    public Payload getGroups(
        @Parameter(
            description = "Authorization token assigned to the user",
            required = true
        )
        @RequestHeader("Authorization") String token
    ) {
        try {
            String username = helperService.checkAuthorisation(token);
            return new Payload.PayloadBuilder()
                    .setCode(Code.SUCCESS)
                    .setGroups(databaseManager.getGroupsQuery(username))
                    .build();
        } catch (SQLException ex) {
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }
    }

    @Operation(
        summary = "Rename an existing group",
        description = "Renames an existing group specified in the request body to a new name for the user identified by the authorization token."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Group rename operation",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Payload.class),
                examples = {
                    @ExampleObject(
                        name = "Rename success",
                        value = """
                        {
                          "code": "SUCCESS"
                        }
                        """
                    ),
                    @ExampleObject(
                        name = "Rename failure - group do not exist or auth token is wrong",
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
    @PostMapping("/rename")
    public Payload renameGroup(
        @Parameter(
            description = "Authorization token assigned to the user",
            required = true
        )
        @RequestHeader("Authorization") String token,
        @RequestBody GroupRequest groupRequest
    ) {
        try {
            String username = helperService.checkAuthorisation(token);
            if (databaseManager.changeGroupName(username, groupRequest.getGroup(), groupRequest.getGroupNewName())){
                return new Payload.PayloadBuilder().setCode(Code.SUCCESS).build();
            } else {
                return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
            }
        } catch (SQLException ex) {
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }
    }

    @Operation(
        summary = "Remove an existing group",
        description = "Removes the specified group from the user identified by the Authorization token."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Group removal operation",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Payload.class),
                examples = {
                    @ExampleObject(
                        name = "Remove success",
                        value = """
                        {
                          "code": "SUCCESS"
                        }
                        """
                    ),
                      @ExampleObject(
                        name = "Remove failure - group do not exist or auth token is wrong",
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
    @DeleteMapping("/remove")
    public Payload removeGroup(
        @Parameter(description = "Authorization token assigned to the user", required = true)
        @RequestHeader("Authorization") String token,
        @Parameter(description = "Name of the group to be removed", required = true)
        @RequestParam("group") String group
    ) {
        try {
            String username = helperService.checkAuthorisation(token);
            databaseManager.removeGroupQuery(username, group);
            return new Payload.PayloadBuilder().setCode(Code.SUCCESS).build();
        } catch (SQLException ex) {
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }
    }

    @Operation(
        summary = "Add a new group",
        description = "Creates a new group specified in the request body for the user identified by the Authorization token. If new group parameter is provided, it is ignored"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Group creation operation",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Payload.class),
                examples = {
                    @ExampleObject(
                        name = "Create success",
                        value = """
                        {
                          "code": "SUCCESS"
                        }
                        """
                    ),
                    @ExampleObject(
                        name = "Create failure - group do not exist or auth token is wrong",
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
    @PostMapping("/add")
    public Payload addGroup(
        @Parameter(description = "Authorization token assigned to the user", required = true)
        @RequestHeader("Authorization") String token,
        @RequestBody GroupRequest groupRequest
    ) {
        try {
            String username = helperService.checkAuthorisation(token);
            databaseManager.createGroupQuery(username, groupRequest.getGroup());
            return new Payload.PayloadBuilder().setCode(Code.SUCCESS).build();
        } catch (SQLException ex) {
            return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
        }
    }
}
