
/**
 * WSProtocollazioneProtocollazioneExceptionException0.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

package it.kdm.docer.protocollazione;

public class WSProtocollazioneProtocollazioneExceptionException0 extends java.lang.Exception{
    
    private it.kdm.docer.protocollazione.WSProtocollazioneStub.WSProtocollazioneProtocollazioneException faultMessage;
    
    public WSProtocollazioneProtocollazioneExceptionException0() {
        super("WSProtocollazioneProtocollazioneExceptionException0");
    }
           
    public WSProtocollazioneProtocollazioneExceptionException0(java.lang.String s) {
       super(s);
    }
    
    public WSProtocollazioneProtocollazioneExceptionException0(java.lang.String s, java.lang.Throwable ex) {
      super(s, ex);
    }
    
    public void setFaultMessage(it.kdm.docer.protocollazione.WSProtocollazioneStub.WSProtocollazioneProtocollazioneException msg){
       faultMessage = msg;
    }
    
    public it.kdm.docer.protocollazione.WSProtocollazioneStub.WSProtocollazioneProtocollazioneException getFaultMessage(){
       return faultMessage;
    }
}
    