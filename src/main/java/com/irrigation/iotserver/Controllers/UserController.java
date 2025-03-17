/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.iotserver.Controllers;

import com.irrigation.Messages.MessageFormat.Code;
import com.irrigation.Messages.MessageFormat.CredentialsRequest;
import com.irrigation.Messages.MessageFormat.Payload;
import com.irrigation.iotserver.Security.PasswordHasher;
import com.irrigation.iotserver.Security.TokenGenerator;
import com.irrigation.iotserver.Services.DataAccess;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.sql.SQLException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author brune
 */
@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "Operations related to user management")
public class UserController {
    private final DataAccess databaseManager;
    public UserController(DataAccess databaseManager){
       this.databaseManager =  databaseManager;
    }
   
   
    @Operation(
    summary = "Log in a user",
    description = "Authenticates a user based on a username and password. Returns a token on success. Returns failure if password is wrong"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Login attempt response",
            content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Payload.class),
            examples = {
                @ExampleObject(
                    name = "Login successfull",
                    value = """
                    {
                      "code": "SUCCESS",
                      "token": "auth-token-example"
                    }
                    """
                ),
                @ExampleObject(
                    name = "Login not sucessfull",
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
                                name = "Empty response",
                                value = """
                                {
                                }
                                """
                            )
                        }           
        ))
    })
    @PostMapping("/login")
    public Payload confirmLogin(@RequestBody CredentialsRequest loginRequest) {
        try {
            System.out.println("Login attempt");
            if(PasswordHasher.compareIfPassowrdMatchesWithStoredHash(loginRequest.getPassword(),
                databaseManager.getPasswordQuery(loginRequest.getUsername()))){
                String token = TokenGenerator.generateToken();
                databaseManager.setTokenQuery(loginRequest.getUsername(), token);
                return new Payload.PayloadBuilder()
                        .setCode(Code.SUCCESS)
                        .setToken(token)
                        .build();
            }
            else{
                return new Payload.PayloadBuilder()
                        .setCode(Code.FAILURE)
                        .build();
            }
        } catch (SQLException ex) {
                     return new Payload.PayloadBuilder()
                        .setCode(Code.FAILURE)
                        .build();
        }
   }
    
    @Operation(
    summary = "Register new user",
    description = "Creates a new user with username and password. Returns failure if username is already taken"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Registration attempt response",
            content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Payload.class),
            examples = {
                @ExampleObject(
                    name = "Registration complete response",
                    value = """
                    {
                      "code": "SUCCESS"
                    }
                    """
                ),
                @ExampleObject(
                    name = "Name taken response",
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
                    name = "Empty response",
                    value = """
                    {
                    }
                    """
                )
            }
        )
                
        )
    })
    @PostMapping("/register")
    public Payload confirmRegister(@RequestBody CredentialsRequest registerRequest) {
            if(databaseManager.addUserQuery(registerRequest.getUsername(),registerRequest.getPassword())){
                return new Payload.PayloadBuilder()
               .setCode(Code.SUCCESS)
               .build();  
            }
            else{
                return new Payload.PayloadBuilder()
                .setCode(Code.FAILURE)
                .build(); 
            }
    }
}
