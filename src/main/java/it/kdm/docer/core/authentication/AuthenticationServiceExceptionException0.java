
/**
 * AuthenticationServiceExceptionException0.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

package it.kdm.docer.core.authentication;

public class AuthenticationServiceExceptionException0 extends java.lang.Exception{
    
    private it.kdm.docer.core.authentication.AuthenticationServiceStub.AuthenticationServiceException faultMessage;
    
    public AuthenticationServiceExceptionException0() {
        super("AuthenticationServiceExceptionException0");
    }
           
    public AuthenticationServiceExceptionException0(java.lang.String s) {
       super(s);
    }
    
    public AuthenticationServiceExceptionException0(java.lang.String s, java.lang.Throwable ex) {
      super(s, ex);
    }
    
    public void setFaultMessage(it.kdm.docer.core.authentication.AuthenticationServiceStub.AuthenticationServiceException msg){
       faultMessage = msg;
    }
    
    public it.kdm.docer.core.authentication.AuthenticationServiceStub.AuthenticationServiceException getFaultMessage(){
       return faultMessage;
    }
}
    