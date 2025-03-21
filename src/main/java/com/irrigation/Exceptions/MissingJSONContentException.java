/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.irrigation.Exceptions;

/**
 *
 * @author brune
 */
public class MissingJSONContentException extends RuntimeException {
    public MissingJSONContentException(String message) {
        super(message);
    }
}
