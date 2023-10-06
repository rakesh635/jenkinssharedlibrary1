@Library('ni-utils') _

//service name is extrapolated from repository name
def svcName = currentBuild.rawBuild.project.parent.displayName

//get pod template definition
def pod = libraryResource 'org/foo/k8s-pod-template.groovy'
def image_dependencies =
'''
    - name: fermium-alpine
      image: node:fermium-alpine
      imagePullPolicy: IfNotPresent
      command:
        - cat
      tty: true
'''

def template_vars = [
    'build_label': 'datascience',
    'node_version' :'fermium',
    'image_dependencies' : [image_dependencies]
]
pod = renderTemplate(pod, template_vars)

def sharedLibrary = new com.org.foo.sharedLibrary()

def compileData = [run: true]
def testData = [run: true]
def artifactData = [run: true]
def intTestData = [run: true]
def deploymentData = [run: true]
def afterDeployData = [run: true]

def buildCommands = [
    compileData: compileData,
    testData: testData,
    artifactData: artifactData,
    intTestData: intTestData,
    deploymentData: deploymentData,
    afterDeployData: afterDeployData
]

// Set slack channel
def slackChannel = "k8s-jenkins"

timestamps {
    commonPipeline(sharedLibrary, svcName, buildCommands, pod, slackChannel)
}
