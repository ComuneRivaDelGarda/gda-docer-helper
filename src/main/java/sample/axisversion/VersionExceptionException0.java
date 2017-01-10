
/**
 * VersionExceptionException0.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

package sample.axisversion;

public class VersionExceptionException0 extends java.lang.Exception{
    
    private sample.axisversion.VersionStub.VersionException faultMessage;
    
    public VersionExceptionException0() {
        super("VersionExceptionException0");
    }
           
    public VersionExceptionException0(java.lang.String s) {
       super(s);
    }
    
    public VersionExceptionException0(java.lang.String s, java.lang.Throwable ex) {
      super(s, ex);
    }
    
    public void setFaultMessage(sample.axisversion.VersionStub.VersionException msg){
       faultMessage = msg;
    }
    
    public sample.axisversion.VersionStub.VersionException getFaultMessage(){
       return faultMessage;
    }
}
    