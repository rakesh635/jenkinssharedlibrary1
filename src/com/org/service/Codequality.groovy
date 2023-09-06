package com.org.service

//import groovy.transform.Field
//import com.org.exception.*
import com.org.log.Logger
import com.org.log.LogLevel

class Codequality implements Serializable{
Script mainScript
Map specs
Map config
Logger logger
  
  def Codequality(Script mainScript, Map specs, Map config){
  this.mainScript = mainScript
  this.specs = specs
  this.config = config
  this.logger = new Logger(mainScript,"Codequality")
  }
  void codequalityFunc(Map specs, Map config){
    if (specs.codeQuality.isCodeQualityRequired && specs.containsKey("codeQuality")){  
      if (specs.codeQuality.tool == "sonarqube") {
        if (specs.codeQuality.containsKey("command")) {
          mainScript.sh specs.codeQuality.command 
        } else {
          mainScript.sh """ mvn sonar:sonar -Dsonar.projectKey=${specs.codeQuality.projectKey} -Dsonar.host.url=${specs.codeQuality.url} -Dsonar.login=${specs.codeQuality.login} -Dsonar.projectName=${specs.codeQuality.projectName} -Dsonar.organization=${specs.codeQuality.organization} """  
        }
        logger.info "codeQuality successfully completed."
       } else {
          logger.warn "unsupported tool. Please use sonarqube."
        }
      } else {
          logger.warn "Skipping code quality stage because code quality templates are missing or code quality stage is disabled."
        }   
  }
  
}
