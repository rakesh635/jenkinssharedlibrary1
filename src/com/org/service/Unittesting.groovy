package com.org.service

//import groovy.transform.Field
//import com.org.exception.*
import com.org.log.Logger
import com.org.log.LogLevel
import com.org.constant.GlobalVars
import com.org.utils.PublishHtml

class Unittesting implements Serializable{
Script mainScript
Map specs
Map config
Logger logger
PublishHtml publishHtml

  def Unittesting(Script mainScript, Map specs, Map config){
  this.mainScript = mainScript
  this.specs = specs
  this.config = config
  this.logger = new Logger(mainScript,"Unittesting")
  this.publishHtml = new PublishHtml(mainScript, GlobalVars.publishHtml_allowMissing, GlobalVars.publishHtml_alwaysLinkToLastBuild, GlobalVars.publishHtml_keepAll, GlobalVars.publishHtml_unitTest_reportDir, GlobalVars.publishHtml_unitTest_reportFiles, GlobalVars.publishHtml_unitTest_reportName, GlobalVars.publishHtml_reportTitles)
  }
  void unitTestFunc(Map specs, Map config) {
    if (specs.unitTest.isUnittestRequired && specs.containsKey("unitTest")){
        if (specs.unitTest.tool == 'junit') {
            mainScript.sh config.java.unittest.junit.command 
            mainScript.sh config.java.unittest.junit.surefire
            publishHtml.publishHtmlFunc(GlobalVars.publishHtml_allowMissing, GlobalVars.publishHtml_alwaysLinkToLastBuild, GlobalVars.publishHtml_keepAll, GlobalVars.publishHtml_unitTest_reportDir, GlobalVars.publishHtml_unitTest_reportFiles, GlobalVars.publishHtml_unitTest_reportName, GlobalVars.publishHtml_reportTitles)
            //mainScript.publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: './target/site/', reportFiles: 'surefire-report.html', reportName: 'UnitTest Report', reportTitles: '']) 
          } else {
                logger.warn "unsupported tool. Please use junit."
            } 
    } 
    else {
    logger.warn "Skipping unit test stage because unit Test templates are missing or Unit Test stage is disabled."
        } 
  }
}
