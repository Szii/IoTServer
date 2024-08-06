/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irrigation.Messages;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author brune
 */
public class Payload implements Serializable {
    List<String> content;
    MessageType type;
    Code code;
    
    public Payload(){}

    public Payload(List<String> content, MessageType type, Code code) {
        this.content = content;
        this.type = type;
        this.code = code;
    }
    
    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }


    
}
