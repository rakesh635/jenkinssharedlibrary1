package com.org.service

//import groovy.transform.Field
//import com.org.exception.*
import com.org.log.Logger
import com.org.log.LogLevel
import com.org.constant.GlobalVars
import com.org.utils.PublishHtml

class Codecoverage implements Serializable{
Script mainScript
Map specs
Map config
Logger logger
PublishHtml publishHtml

  def Codecoverage(Script mainScript, Map specs, Map config){
  this.mainScript = mainScript
  this.specs = specs
  this.config = config
  this.logger = new Logger(mainScript,"Codecoverage")
  this.publishHtml = new PublishHtml(mainScript, GlobalVars.publishHtml_allowMissing, GlobalVars.publishHtml_alwaysLinkToLastBuild, GlobalVars.publishHtml_keepAll, GlobalVars.publishHtml_codeCoverage_reportDir, GlobalVars.publishHtml_codeCoverage_reportFiles, GlobalVars.publishHtml_codeCoverage_reportName, GlobalVars.publishHtml_reportTitles)
  }
  void codecoverageCheckFunc(Map specs, Map config){
    if (specs.codeCoverage.isCodecoverageRequired && specs.containsKey("codeCoverage")){  
      if (specs.codeCoverage.tool == "jacoco") {
        if (specs.codeCoverage.containsKey("command")){
          logger.info "executing codeCoverage command: " + specs.codeCoverage.command 
          mainScript.sh specs.codeCoverage.command 

        } else {
          logger.info "executing codeCoverage command: " + config.java.codecoverage.jacoco.command 
          mainScript.sh config.java.codecoverage.jacoco.command 
        } 
        publishHtml.publishHtmlFunc(GlobalVars.publishHtml_allowMissing, GlobalVars.publishHtml_alwaysLinkToLastBuild, GlobalVars.publishHtml_keepAll, GlobalVars.publishHtml_codeCoverage_reportDir, GlobalVars.publishHtml_codeCoverage_reportFiles, GlobalVars.publishHtml_codeCoverage_reportName, GlobalVars.publishHtml_reportTitles)
        //mainScript.publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: './target/site/jacoco/', reportFiles: 'index.html', reportName: 'Code Coverage Report', reportTitles: ''])
        logger.info "codeCoverage successfully completed."
      } else {
          logger.warn "unsupported tool. Please use jacoco."
        }
      } else {
          logger.warn "Skipping code coverage stage because code coverage templates are missing or code coverage stage is disabled."
        }
    }
}
