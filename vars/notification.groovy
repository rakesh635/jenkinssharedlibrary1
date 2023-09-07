#!/usr/bin/env groovy 
import com.org.log.Logger
import com.org.log.LogLevel
import groovy.transform.Field
import com.org.constant.GlobalVars

@Field logger = new Logger(this, "email", LogLevel.fromString(env.LOG_LEVEL))

def initPipelineStatus(){   
    env.STAGE_CODECHECKOUT_STATUS = GlobalVars.STAGE_SKIPPED_STATUS
    env.STAGE_CODECHECKOUT_COMMENTS = GlobalVars.STAGE_SKIPPED_COMMENTS
    env.STAGE_BUILD_STATUS= GlobalVars.STAGE_SKIPPED_STATUS
    env.STAGE_BUILD_COMMENTS= GlobalVars.STAGE_SKIPPED_COMMENTS
    env.STAGE_UNITTEST_STATUS= GlobalVars.STAGE_SKIPPED_STATUS
    env.STAGE_UNITTEST_COMMENTS= GlobalVars.STAGE_SKIPPED_COMMENTS
    env.STAGE_CODECOVERAGE_STATUS= GlobalVars.STAGE_SKIPPED_STATUS
    env.STAGE_CODECOVERAGE_COMMENTS= GlobalVars.STAGE_SKIPPED_COMMENTS
    env.STAGE_CODECQUALITY_STATUS= GlobalVars.STAGE_SKIPPED_STATUS
    env.STAGE_CODECQUALITY_COMMENTS= GlobalVars.STAGE_SKIPPED_COMMENTS
    
    }

def emailPipelineStatus(){
    try{ 
        def templatename = null

        logger.info ('Preparing subject line')

        sh "envsubst < 'template.txt'> 'index.html'"

        def String EmailSubject = "Build " + env.BUILD_NUMBER + "-" + currentBuild.currentResult + "(" + (currentBuild.fullDisplayName) + ")"

        sh "echo '${EmailSubject}' > emailsub.html" 

        def html_body = sh(script: "cat index.html", returnStdout: true).trim()
        html_subject = sh(script: "cat emailsub.html", returnStdout: true).trim()

        emailext attachmentsPattern: 'LTI*.png',
        mimeType: 'text/html',
        body: html_body,
        from: "rakesh635@gmail.com",
        subject: html_subject,
        to: "rakeshkumar.10675353@ltimindtree.com"


    }catch(emailEx){
        logger.exception(emailEx, "failed to send email")
        throw new Exception("failed to send email: " + emailEx.getMessage())
        continuePipeline = false
    }
} 
