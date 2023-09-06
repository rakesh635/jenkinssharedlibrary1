package com.org.constant

class GlobalVars {

    static Boolean publishHtml_allowMissing = false 
    static Boolean publishHtml_alwaysLinkToLastBuild = false 
    static Boolean publishHtml_keepAll = false 


    static String publishHtml_unitTest_reportDir = './target/site/'
    static String publishHtml_unitTest_reportFiles = 'surefire-report.html'
    static String publishHtml_unitTest_reportName = 'UnitTest Report'

    static String publishHtml_codeCoverage_reportDir = './target/site/jacoco/'
    static String publishHtml_codeCoverage_reportFiles = 'index.html'
    static String publishHtml_codeCoverage_reportName = 'Code Coverage Report'

    static String publishHtml_reportTitles = '' 

    static String STAGE_SKIPPED_STATUS = 'SKIPPED' 
    static String STAGE_SKIPPED_COMMENTS = 'Skipping stage because stage is disabled.' 

    static String STAGE_CUCCESSFUL_STATUS = 'CUCCESSFUL' 
    static String STAGE_CUCCESSFUL_COMMENTS = 'Stage completed succesfully.' 

    static String STAGE_FAILED_STATUS = 'FAILED' 
}
