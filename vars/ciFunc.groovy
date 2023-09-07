def checkoutVarFunc(Map specs) {
  println "Printing specs" + specs
  docheckout = new com.org.service.Codecheckout(this, specs)
  docheckout.checkOutFunc(specs)  
  sh 'ls -ltrha'
}
def build(Map specs, Map config) {
  dobuild = new com.org.service.Build(this, specs, config)
  dobuild.buildFunc(specs, config) 
}
def unittest(Map specs, Map config) {
  dounittest = new com.org.service.Unittesting(this, specs, config)
  dounittest.unitTestFunc(specs, config) 
}
def codecoverage(Map specs, Map config) {
  docodecoverage = new com.org.service.Codecoverage(this, specs, config)
  docodecoverage.codecoverageCheckFunc(specs, config) 
}
def codequality(Map specs, Map config) {
  docodequality = new com.org.service.Codequality(this, specs, config)
  docodequality.codequalityFunc(specs, config) 
}
