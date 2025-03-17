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
@Schema(name = "CredentialsRequest", description = "User credentials for login/registration")
public class CredentialsRequest implements Serializable {
    @Schema(description = "The user’s unique name", example = "Franta")
    String username;
    @Schema(description = "The user’s password (plaintext when sent over HTTPS, it is encoded afterwards)", example = "Lala")
    String password;

    public CredentialsRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
      public CredentialsRequest() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
