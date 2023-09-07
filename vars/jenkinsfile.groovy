//import groovy.transform.Field
//import com.toyota.tfs.exception.*
import com.org.log.Logger
import com.org.log.LogLevel

def call(){
  node('general') {
    Logger logger = new Logger(this, "Jenkinsfile", LogLevel.fromString(env.LOG_LEVEL))
    def specs = [:]
    try {
    notification.initPipelineStatus()
    
    stage('Specs Checkout'){
      cleanWs()
      ciFunc.checkoutVarFunc([
      repo: Repo,
      branch: Branch
      ])
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
