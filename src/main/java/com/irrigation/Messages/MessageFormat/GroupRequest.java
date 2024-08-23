/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.Messages.MessageFormat;

import java.io.Serializable;

/**
 *
 * @author brune
 */
public class GroupRequest extends UserRequest implements Serializable {
    
    String group;
    String  groupNewName;
    
    public GroupRequest() {}

    public GroupRequest(String token, String group) {
        super(token);
        this.group = group;
    }
    
    public GroupRequest(String token, String group, String groupNewName) {
        super(token);
        this.group = group;
        this.groupNewName = groupNewName;
    }
    
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroupNewName() {
        return groupNewName;
    }

    public void setGroupNewName(String groupNewName) {
        this.groupNewName = groupNewName;
    }
    
    
    
}
