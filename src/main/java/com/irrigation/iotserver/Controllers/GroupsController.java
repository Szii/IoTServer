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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/api/groups")
public class GroupsController {
    private final ControllerHelperService helperService;  
    private final DataAccess databaseManager;
    
    public GroupsController(DataAccess databaseManager, ControllerHelperService helperService){
       this.databaseManager =  databaseManager;
       this.helperService = helperService;
       
    }
    
         @GetMapping("/get")
        public Payload getGroups(@RequestHeader("Authorization") String token){
              try {
                String username = helperService.checkAuthorisation(token);
                return new Payload.PayloadBuilder()
                        .setCode(Code.SUCCESS)
                        .setContent(databaseManager.getGroupsQuery(username))
                        .build();
            } catch (SQLException ex) {
                Logger.getLogger(GroupsController.class.getName()).log(Level.SEVERE, null, ex);
                return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
            }
        }

        @PostMapping("/rename")
        public Payload renameGroup(@RequestHeader("Authorization") String token, @RequestBody GroupRequest groupRequest){
            System.out.println("RENAME GROUP ENDPOINT CALLED");
              try {
                 String username = helperService.checkAuthorisation(token);
                 if(databaseManager.changeGroupName(username, groupRequest.getGroup(), groupRequest.getGroupNewName())){
                     return new Payload.PayloadBuilder().setCode(Code.SUCCESS).build();
                 }
                 else{
                     return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
                 }
            } catch (SQLException ex) {
                Logger.getLogger(GroupsController.class.getName()).log(Level.SEVERE, null, ex);
                return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
            }
        }

        @DeleteMapping("/remove")
        public Payload removeGroup(@RequestHeader("Authorization") String token,  @RequestParam("group") String group){
            try {
                String username = helperService.checkAuthorisation(token);
                databaseManager.removeGroupQuery(username, group);     
                return new Payload.PayloadBuilder().setCode(Code.SUCCESS).build();
            } catch (SQLException ex) {
                return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
            }

        }

        @PostMapping("/add")
        public Payload addGroup(@RequestHeader("Authorization") String token, @RequestBody GroupRequest groupRequest){
            System.out.println("ADD GROUP ENDPOINT CALLED " + groupRequest.getGroup());
            try {
                String username = helperService.checkAuthorisation(token);
                databaseManager.createGroupQuery(username,groupRequest.getGroup());     
                return new Payload.PayloadBuilder().setCode(Code.SUCCESS).build();
            } catch (SQLException ex) {
                return new Payload.PayloadBuilder().setCode(Code.FAILURE).build();
            }

        }
}
