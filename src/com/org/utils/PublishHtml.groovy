package com.org.utils

//import groovy.transform.Field
//import com.org.exception.*
import com.org.log.Logger
import com.org.log.LogLevel
import com.org.constant.GlobalVars

class PublishHtml implements Serializable{
Script mainScript
Boolean allowMissing
Boolean alwaysLinkToLastBuild
Boolean keepAll
String reportDir
String reportFiles
String reportName
String reportTitles
Logger logger

  def PublishHtml(Script mainScript, Boolean allowMissing, Boolean alwaysLinkToLastBuild, Boolean keepAll, String reportDir, String reportFiles, String reportName, String reportTitles){
  this.mainScript = mainScript
  this.allowMissing = allowMissing
  this.alwaysLinkToLastBuild = alwaysLinkToLastBuild
  this.keepAll = keepAll
  this.reportDir = reportDir
  this.reportFiles = reportFiles
  this.reportName = reportName
  this.reportTitles = reportTitles
  this.logger = new Logger(mainScript,"publishHtml")
  }
  void publishHtmlFunc(Boolean allowMissing, Boolean alwaysLinkToLastBuild, Boolean keepAll, String reportDir, String reportFiles, String reportName, String reportTitles) {
      mainScript.publishHTML([allowMissing: allowMissing, alwaysLinkToLastBuild: alwaysLinkToLastBuild, keepAll: keepAll, reportDir: reportDir, reportFiles: reportFiles, reportName: reportName, reportTitles: reportTitles]) 
  }
}
