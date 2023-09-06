package com.org.log

import com.cloudbees.groovy.cps.NonCPS
import java.text.SimpleDateFormat
import java.io.StringWriter
import java.io.PrintWriter

class Logger implements Serializable {

    Script script
    String component
    LogLevel logLevel
    LogLevel defaultLogLevel = LogLevel.INFO
    String defaultLogLevelName = defaultLogLevel.toString()
    StringWriter sw

    Logger(Script script, String component, LogLevel thatLogLevel = LogLevel.INFO){
        this.logLevel = thatLogLevel
        this.script = script
        this.component = component
    }

    void info(String message) {
       doPrint(message, LogLevel.INFO)
    }
   
    void warn(String message) {
       doPrint(message, LogLevel.WARN)
    }
      
    void error(String message) {
       doPrint(message, LogLevel.ERROR)
    }
    
    void trace(String message) {
       doPrint(message, LogLevel.TRACE)
    }
    
    void debug(String message) {
       doPrint(message, LogLevel.DEBUG)
    }
 
    void deprecated(String message) {
       doPrint(message, LogLevel.DEPRECATED)
    }
  
    void fatal(String message) {
       doPrint(message, LogLevel.FATAL)
    }


    void exception(Exception ex, String message) {

        if(message != null){
            error(message + "-" + ex.getMessage())
        }else{
            error(message + "-" + ex.getMessage())
        }
        //printingExStack(ex.getStackTrace())
    }
    
    

   private void doPrint(message, LogLevel thatLogLevel){
        if(doLog(thatLogLevel)){
            printing(message, thatLogLevel)
        }
    }
    

    private void printing(String message, LogLevel thatLogLevel = LogLevel.SYSTEM){
        if(!(thatLogLevel.getLevel() == LogLevel.SYSTEM.getLevel() && logLevel.getLevel() < LogLevel.DEBUG.getLevel())){
           
            script.println(getCurrentTime() + wrapColor(thatLogLevel) + "[${component}] ${message}")
        }
    }
        

    private String wrapColor(LogLevel thatLogLevel){
        return " \033["+ thatLogLevel.getColorCode() +"m ["+ thatLogLevel.toString() +"] \033[0m "
    }

    private boolean doLog(LogLevel thatLogLevel) {
        LogLevel appLogLevel=getAppLogLevel()
        if (appLogLevel.getLevel() >= thatLogLevel.getLevel()) {
          return true
        }
        return false
     }
    

    private getAppLogLevel(){
        LogLevel appLogLevel=defaultLogLevel
        try{
            printing("application log level found in script:"+script.env.APP_LOG_LEVEL, LogLevel.SYSTEM)
            appLogLevel=logLevel.fromString(script.env.APP_LOG_LEVEL)
            if(appLogLevel == null){
                throw new Exception("application log level is not valid")
            }
        }catch(Exception logLevelEx){
            appLogLevel=defaultLogLevel
            printing("encountered issue while getting application log level, setting app log level to default [${defaultLogLevelName}]-> "+ logLevelEx.getMessage(), , LogLevel.SYSTEM)
            def sw = new StringWriter()
            logLevelEx.printStackTrace(new PrintWriter(sw))
            printing(sw.toString(), LogLevel.SYSTEM)
        }
        return appLogLevel
    }
    
    private String getCurrentTime(){
        def date = new Date()
        def sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
        return sdf.format(date)
    }
    
    /*
    * This method prints the exception stack trace 
    */
    private void printingExStack(Exception ex){
        def sw = new StringWriter()
        ex.printStackTrace(new PrintWriter(sw))
        debug(sw.toString())
    }
}
