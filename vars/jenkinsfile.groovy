//import groovy.transform.Field
//import com.toyota.tfs.exception.*
import com.org.log.Logger
import com.org.log.LogLevel

def call(){
  node('general') {
    Logger logger = new Logger(this, "Jenkinsfile", LogLevel.fromString(env.LOG_LEVEL))
    def specs = [:]
    try {
    script{
      notification.initPipelineStatus()
    }
    
    stage('Specs Checkout'){
      cleanWs()
      script{
      ciFunc.checkoutVarFunc([
      repo: Repo,
      branch: Branch
      ])
      }
        stage('reading GlobalConfig & Specs'){ 
            try {
            logger.info "reading the specs from Specs repository"
            def specsDir = "./$Version"
            logger.debug "specs version" + specsDir
                if(fileExists(specsDir + "/ci_template.yaml")){
                ci_template = readYaml file : specsDir + "/ci_template.yaml"
                specs = specs + ci_template
                logger.debug "reading specs file" + specs
                }
            
                logger.info "reading the global config from resources"
                def request = libraryResource "com/org/service/globalConfig/globalConfig.yaml"
                config = readYaml text: request
                logger.debug "reading config file" + config
              
                }
            catch(Exception e) {
                logger.error "Error in reading specs file : " + e.getMessage()
            throw e
                }
            }
        }

        if (specs.build.type == "java") {
            logger.info "Calling java_jenkinsfile" 
            ciFunc.java_jenkinsfile(specs, config)
        } else {
        logger.warn "unsupported technology, as of now we only supports Java."
        }  
    }   
    catch(Exception e) {
      logger.error "Error in build stage : " + e.getMessage()
    throw e
      }

      finally {
        notification.emailPipelineStatus()
      }
    }
  }
