/**
 * DocerServicesDocerExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.4  Built on : Oct 21, 2016 (10:47:34 BST)
 */
package it.kdm.docer.webservices;

public class DocerServicesDocerExceptionException extends java.lang.Exception {
    private static final long serialVersionUID = 1480006630506L;
    private it.kdm.docer.webservices.DocerServicesStub.DocerServicesDocerException faultMessage;

    public DocerServicesDocerExceptionException() {
        super("DocerServicesDocerExceptionException");
    }

    public DocerServicesDocerExceptionException(java.lang.String s) {
        super(s);
    }

    public DocerServicesDocerExceptionException(java.lang.String s,
        java.lang.Throwable ex) {
        super(s, ex);
    }

    public DocerServicesDocerExceptionException(java.lang.Throwable cause) {
        super(cause);
    }

    public void setFaultMessage(
        it.kdm.docer.webservices.DocerServicesStub.DocerServicesDocerException msg) {
        faultMessage = msg;
    }

    public it.kdm.docer.webservices.DocerServicesStub.DocerServicesDocerException getFaultMessage() {
        return faultMessage;
    }
}
