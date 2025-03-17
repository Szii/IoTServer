/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.Messages.MessageFormat;


import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

/**
 *
 * @author brune
 */

@Schema(
    name = "GroupRequest", 
    description = "Payload for group-related operations."
)
public class GroupRequest implements Serializable {

    @Schema(
        description = "Name of the group existing or new",
        example = "Sklenik_venek"
    )
    private String group;

    @Schema(
        description = "New name to rename the group - used when renaming.",
        example = "Sklenik_venek_maly",
        nullable = true
    )
    private String groupNewName;

    public GroupRequest() {}

    public GroupRequest(String group) {
        this.group = group;
    }

    public GroupRequest(String group, String groupNewName) {
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
