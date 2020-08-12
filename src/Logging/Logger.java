/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logging;

import java.util.ArrayList;

/**
 *
 * @author Kenneth
 */
public class Logger {
    private static Logger instance = new Logger();
    
    private ArrayList<Log> logs;
    
    private Logger(){
        logs = new ArrayList<>();
    }
    
    public static void log(Log.Type type, String details) {
        log(new Log(type, details));
    }
    
    public static void log(Log.Type type, String details, String processId) {
        log(new Log(type, details, processId));
    }
    
    public static String getAllLogs(){
        String logString = "";
        ArrayList<Log> tLogs;
        tLogs = (ArrayList<Log>) instance.logs.clone();
        for(Log log : tLogs.subList(Math.max(tLogs.size() - 20, 0), tLogs.size())){
            logString += log.toString(true) + "\n";
        }
        return logString;
    }
    
    public static String getProcessLogs(String processId){
        String logString = "";
        ArrayList<Log> tLogs;
        tLogs = (ArrayList<Log>) instance.logs.clone();
        for(Log log : tLogs.subList(Math.max(tLogs.size() - 40, 0), tLogs.size())){
            if (log.getProcessId().equals(processId))
                logString += log.toString(false) + "\n";
        }
        return logString;
    }
    
    private static void log(Log log){
        instance.logs.add(log);
    }
    
    
}
