/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package us.albertzb.tarot.utils;

/**
 *
 * @author albertzb [albertzb42@gmail.com]
 */
public class Env {
    
    private Env() {}
    
    public static String getVar(String name) {
        return getVar(name, "");
    }
    
    public static String getRequiredVar(String name) {
        String value = getVar(name, "");
        if(value.isEmpty()) {
            throw new IllegalStateException(name + " is not defined as environment variable or property");
        }
        return value;
    }
    
    public static String getVar(String name, String defValue) {
        String variable = System.getenv(name);
        if(variable == null || variable.isEmpty()) {
            variable = System.getProperty(name);
        }
        if(variable == null) {
            variable = defValue;
        }
        return variable;
    }
    
}
