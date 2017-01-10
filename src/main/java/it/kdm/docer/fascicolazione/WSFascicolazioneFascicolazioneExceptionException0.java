
/**
 * WSFascicolazioneFascicolazioneExceptionException0.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

package it.kdm.docer.fascicolazione;

public class WSFascicolazioneFascicolazioneExceptionException0 extends java.lang.Exception{
    
    private it.kdm.docer.fascicolazione.WSFascicolazioneStub.WSFascicolazioneFascicolazioneException faultMessage;
    
    public WSFascicolazioneFascicolazioneExceptionException0() {
        super("WSFascicolazioneFascicolazioneExceptionException0");
    }
           
    public WSFascicolazioneFascicolazioneExceptionException0(java.lang.String s) {
       super(s);
    }
    
    public WSFascicolazioneFascicolazioneExceptionException0(java.lang.String s, java.lang.Throwable ex) {
      super(s, ex);
    }
    
    public void setFaultMessage(it.kdm.docer.fascicolazione.WSFascicolazioneStub.WSFascicolazioneFascicolazioneException msg){
       faultMessage = msg;
    }
    
    public it.kdm.docer.fascicolazione.WSFascicolazioneStub.WSFascicolazioneFascicolazioneException getFaultMessage(){
       return faultMessage;
    }
}
    