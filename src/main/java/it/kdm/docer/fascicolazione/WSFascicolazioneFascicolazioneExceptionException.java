/**
 * WSFascicolazioneFascicolazioneExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.4  Built on : Oct 21, 2016 (10:47:34 BST)
 */
package it.kdm.docer.fascicolazione;

public class WSFascicolazioneFascicolazioneExceptionException extends java.lang.Exception {
    private static final long serialVersionUID = 1480006624884L;
    private it.kdm.docer.fascicolazione.WSFascicolazioneStub.WSFascicolazioneFascicolazioneException faultMessage;

    public WSFascicolazioneFascicolazioneExceptionException() {
        super("WSFascicolazioneFascicolazioneExceptionException");
    }

    public WSFascicolazioneFascicolazioneExceptionException(java.lang.String s) {
        super(s);
    }

    public WSFascicolazioneFascicolazioneExceptionException(
        java.lang.String s, java.lang.Throwable ex) {
        super(s, ex);
    }

    public WSFascicolazioneFascicolazioneExceptionException(
        java.lang.Throwable cause) {
        super(cause);
    }

    public void setFaultMessage(
        it.kdm.docer.fascicolazione.WSFascicolazioneStub.WSFascicolazioneFascicolazioneException msg) {
        faultMessage = msg;
    }

    public it.kdm.docer.fascicolazione.WSFascicolazioneStub.WSFascicolazioneFascicolazioneException getFaultMessage() {
        return faultMessage;
    }
}
