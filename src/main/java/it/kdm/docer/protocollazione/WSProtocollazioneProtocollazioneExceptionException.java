/**
 * WSProtocollazioneProtocollazioneExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.4  Built on : Oct 21, 2016 (10:47:34 BST)
 */
package it.kdm.docer.protocollazione;

public class WSProtocollazioneProtocollazioneExceptionException extends java.lang.Exception {
    private static final long serialVersionUID = 1480006629095L;
    private it.kdm.docer.protocollazione.WSProtocollazioneStub.WSProtocollazioneProtocollazioneException faultMessage;

    public WSProtocollazioneProtocollazioneExceptionException() {
        super("WSProtocollazioneProtocollazioneExceptionException");
    }

    public WSProtocollazioneProtocollazioneExceptionException(
        java.lang.String s) {
        super(s);
    }

    public WSProtocollazioneProtocollazioneExceptionException(
        java.lang.String s, java.lang.Throwable ex) {
        super(s, ex);
    }

    public WSProtocollazioneProtocollazioneExceptionException(
        java.lang.Throwable cause) {
        super(cause);
    }

    public void setFaultMessage(
        it.kdm.docer.protocollazione.WSProtocollazioneStub.WSProtocollazioneProtocollazioneException msg) {
        faultMessage = msg;
    }

    public it.kdm.docer.protocollazione.WSProtocollazioneStub.WSProtocollazioneProtocollazioneException getFaultMessage() {
        return faultMessage;
    }
}
