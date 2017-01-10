
/**
 * DocerServicesDocerExceptionException0.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

package it.kdm.docer.webservices;

public class DocerServicesDocerExceptionException0 extends java.lang.Exception{
    
    private it.kdm.docer.webservices.DocerServicesStub.DocerServicesDocerException faultMessage;
    
    public DocerServicesDocerExceptionException0() {
        super("DocerServicesDocerExceptionException0");
    }
           
    public DocerServicesDocerExceptionException0(java.lang.String s) {
       super(s);
    }
    
    public DocerServicesDocerExceptionException0(java.lang.String s, java.lang.Throwable ex) {
      super(s, ex);
    }
    
    public void setFaultMessage(it.kdm.docer.webservices.DocerServicesStub.DocerServicesDocerException msg){
       faultMessage = msg;
    }
    
    public it.kdm.docer.webservices.DocerServicesStub.DocerServicesDocerException getFaultMessage(){
       return faultMessage;
    }
}
    