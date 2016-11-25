/**
 * AuthenticationServiceExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.4  Built on : Oct 21, 2016 (10:47:34 BST)
 */
package it.kdm.docer.core.authentication;

public class AuthenticationServiceExceptionException extends java.lang.Exception {
    private static final long serialVersionUID = 1480006622357L;
    private it.kdm.docer.core.authentication.AuthenticationServiceStub.AuthenticationServiceException faultMessage;

    public AuthenticationServiceExceptionException() {
        super("AuthenticationServiceExceptionException");
    }

    public AuthenticationServiceExceptionException(java.lang.String s) {
        super(s);
    }

    public AuthenticationServiceExceptionException(java.lang.String s,
        java.lang.Throwable ex) {
        super(s, ex);
    }

    public AuthenticationServiceExceptionException(java.lang.Throwable cause) {
        super(cause);
    }

    public void setFaultMessage(
        it.kdm.docer.core.authentication.AuthenticationServiceStub.AuthenticationServiceException msg) {
        faultMessage = msg;
    }

    public it.kdm.docer.core.authentication.AuthenticationServiceStub.AuthenticationServiceException getFaultMessage() {
        return faultMessage;
    }
}
