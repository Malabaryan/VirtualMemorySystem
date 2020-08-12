/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logging;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Kenneth
 */
public class Log {
    public enum Type {
        INFO,
        ERROR
    }
    private String processId = "";
    private String detail = "";
    private Type type = Type.INFO;
    private Date time;
    
    public Log(Type type, String detail){
        this.type = type;
        this.detail = detail;
        this.time = new Date();
    }
    
    public Log(Type type, String detail, String processId){
        this.type = type;
        this.detail = detail;
        this.processId = processId;
        this.time = new Date();
    }

    public String getProcessId() {
        return processId;
    }

    public String getDetail() {
        return detail;
    }

    public Type getType() {
        return type;
    }
   
    public String toString(boolean showProcessId){
        return getTimeStamp() 
                + "[" + getType().name() + "]"
                + (showProcessId && !processId.equals("") ? "[" + getProcessId() + "]" : "") 
                + ": " + getDetail();
    }
    
    public String toString(){
        return toString(true);
    }
    
    public String getTimeStamp() {
        SimpleDateFormat formatter = new SimpleDateFormat("[HH:mm:ss]");   
        return formatter.format(time);
    }
}
