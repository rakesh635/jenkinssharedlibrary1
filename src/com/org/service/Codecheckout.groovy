package com.org.service

//import groovy.transform.Field
//import com.org.exception.*
import com.org.log.Logger
import com.org.log.LogLevel


public class Codecheckout implements Serializable{
Script mainScript
Map specs
Logger logger

  def Codecheckout(Script mainScript, Map specs){
  this.mainScript = mainScript
  this.specs = specs
  this.logger = new Logger(mainScript,"Codecheckout")
  }
  void checkOutFunc(Map specs){
    try {
      mainScript.checkout([$class: 'GitSCM',
      branches: [[name: specs.branch]],
      extensions: [],
      userRemoteConfigs: [[url: specs.repo ]]
      ])
      logger.info "Code succesfully checkedout from: " + specs.repo
    } catch(Exception e){
            throw new Exception("Error in Codecheckout: " + e.getMessage())
        }
   } 
}
