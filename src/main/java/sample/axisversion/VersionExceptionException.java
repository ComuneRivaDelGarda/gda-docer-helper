/**
 * VersionExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.4  Built on : Oct 21, 2016 (10:47:34 BST)
 */
package sample.axisversion;

public class VersionExceptionException extends java.lang.Exception {
    private static final long serialVersionUID = 1480006557054L;
    private sample.axisversion.VersionStub.VersionException faultMessage;

    public VersionExceptionException() {
        super("VersionExceptionException");
    }

    public VersionExceptionException(java.lang.String s) {
        super(s);
    }

    public VersionExceptionException(java.lang.String s, java.lang.Throwable ex) {
        super(s, ex);
    }

    public VersionExceptionException(java.lang.Throwable cause) {
        super(cause);
    }

    public void setFaultMessage(
        sample.axisversion.VersionStub.VersionException msg) {
        faultMessage = msg;
    }

    public sample.axisversion.VersionStub.VersionException getFaultMessage() {
        return faultMessage;
    }
}
