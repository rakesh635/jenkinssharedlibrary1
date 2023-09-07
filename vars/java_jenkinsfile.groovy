//import groovy.transform.Field
//import com.toyota.tfs.exception.*
import com.org.log.Logger
import com.org.log.LogLevel
import com.org.constant.GlobalVars

def call(Map specs, Map config){
  node('general') {
    Logger logger = new Logger(this, "Java-Jenkinsfile", LogLevel.fromString(env.LOG_LEVEL))
    try {

      try {     
        stage('Code Checkout'){
            cleanWs()
            ciFunc.checkoutVarFunc([
            repo: specs.scm.repo,
            branch: specs.scm.branch  
            ])
            env.STAGE_CODECHECKOUT_STATUS = GlobalVars.STAGE_CUCCESSFUL_STATUS
            env.STAGE_CODECHECKOUT_COMMENTS = GlobalVars.STAGE_CUCCESSFUL_COMMENTS
            }
      } catch(CheckOutEXP){
        env.STAGE_CODECHECKOUT_STATUS = GlobalVars.STAGE_CUCCESSFUL_STATUS
        logger.exception(CheckOutEXP, "failed to checkOut code")

        env.STAGE_CODECHECKOUT_COMMENTS = CheckOutEXP.getMessage()
        throw new Exception("failed to checkOut code: " + CheckOutEXP.getMessage())
        continuePipeline = false
      }

    try {
      stage('Build'){
        ciFunc.build(specs, config)
        env.STAGE_BUILD_STATUS = GlobalVars.STAGE_CUCCESSFUL_STATUS
        env.STAGE_BUILD_COMMENTS = GlobalVars.STAGE_CUCCESSFUL_COMMENTS
      }
    } catch(buildEXP){
        env.STAGE_BUILD_STATUS = GlobalVars.STAGE_CUCCESSFUL_STATUS
        logger.exception(buildEXP, "failed to build code")

        env.STAGE_BUILD_COMMENTS = buildEXP.getMessage()
        throw new Exception("failed to build code: " + buildEXP.getMessage())
        continuePipeline = false
      }
    try {
      if (specs.unitTest.isUnittestRequired && specs.containsKey("unitTest")){
          stage('UnitTest'){
              ciFunc.unittest(specs, config)
              env.STAGE_UNITTEST_STATUS = GlobalVars.STAGE_CUCCESSFUL_STATUS
              env.STAGE_UNITTEST_COMMENTS = GlobalVars.STAGE_CUCCESSFUL_COMMENTS
          }
      }
        else {
        logger.warn "Skipping unit test stage because unit Test templates are missing or Unit Test stage is disabled."
        }
    } catch(unitTestEXP){
        env.STAGE_UNITTEST_STATUS = GlobalVars.STAGE_CUCCESSFUL_STATUS
        logger.exception(unitTestEXP, "failed to build code")

        env.STAGE_UNITTEST_COMMENTS = unitTestEXP.getMessage()
        throw new Exception("failed to build code: " + unitTestEXP.getMessage())
        continuePipeline = false
      }

    try {
      if (specs.codeCoverage.isCodecoverageRequired && specs.containsKey("codeCoverage")){  
      stage('CodeCoverage'){
        ciFunc.codecoverage(specs, config)
        env.STAGE_CODECOVERAGE_STATUS = GlobalVars.STAGE_CUCCESSFUL_STATUS
        env.STAGE_CODECOVERAGE_COMMENTS = GlobalVars.STAGE_CUCCESSFUL_COMMENTS
        }
      } 
      else {
      logger.warn "Skipping code coverage stage because code coverage templates are missing or code coverage stage is disabled." 
        }
    } catch(codeCoverageEXP){
        env.STAGE_CODECOVERAGE_STATUS = GlobalVars.STAGE_CUCCESSFUL_STATUS
        logger.exception(codeCoverageEXP, "failed to execute code Coverage")

        env.STAGE_CODECOVERAGE_COMMENTS = codeCoverageEXP.getMessage()
        throw new Exception("failed to execute code Coverage: " + codeCoverageEXP.getMessage())
        continuePipeline = false
      }

    try {
      if (specs.codeQuality.isCodeQualityRequired && specs.containsKey("codeQuality")){  
      stage('CodeQuality'){
        ciFunc.codequality(specs, config)
        env.STAGE_CODECQUALITY_STATUS = GlobalVars.STAGE_CUCCESSFUL_STATUS
        env.STAGE_CODECQUALITY_COMMENTS = GlobalVars.STAGE_CUCCESSFUL_COMMENTS
        }
      } 
      else {
      logger.warn "Skipping code quality stage because code quality templates are missing or code quality stage is disabled." 
        }  
    } catch(codeQualityEXP){
        env.STAGE_CODECQUALITY_STATUS = GlobalVars.STAGE_CUCCESSFUL_STATUS
        logger.exception(codeQualityEXP, "failed to perform code quality")

        env.STAGE_CODECQUALITY_COMMENTS = codeQualityEXP.getMessage()
        throw new Exception("failed to perform code quality: " + codeQualityEXP.getMessage())
        continuePipeline = false
      }
    }   
    catch(Exception e) {
      logger.error "Error in build stage : " + e.getMessage()
    throw e
      }
    }
  }
