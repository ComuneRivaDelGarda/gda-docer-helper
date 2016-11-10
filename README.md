#gda-docer-helper

### CREAZIONE PACCHETTO
	mvn clean compile assembly:single launch4j:launch4j
	
### single 
	mvn clean compile assembly:single

### package
	mvn clean package

### axis2 generazione codice da wsdl (non piu necessario perche' ora configurato in automatico)
	mvn axis2-wsdl2code:wsdl2code
	mvn clean axis2-wsdl2code:wsdl2code compile

mvn clean validate axis2-wsdl2code:wsdl2code compile assembly:single launch4j:launch4j

per funzionare richiede service.wsdl in src/main/axis2

ATTENZIONE al WSDL:
http://mail-archives.apache.org/mod_mbox/axis-java-user/200803.mbox/%3C20080330231916.u0blerruisgs0ogo@veithen.no-ip.org%3E

that replaces "\p{Is" by "\p{In" before feeding the schema to wsdl2java.

-Dorg.apache.commons.logging.Log=org.apache.commons.logging.impl.SimpleLog -Dorg.apache.commons.logging.simplelog.showdatetime=true -Dorg.apache.commons.logging.simplelog.log.httpclient.wire=debug -Dorg.apache.commons.logging.simplelog.log.org.apache.commons.httpclient=debug

### launch4j + maven
http://launch4j.sourceforge.net/docs.html#Running_launch4j
http://www.hascode.com/2012/08/creating-a-windows-executable-from-a-jar-using-maven/

### README.TXT
﻿
Buonasera Michela,
la pila Docer è installata e raggiungibile all' indirizzo :
*http://192.168.64.22:8080/docer/XForms?action=search
<http://192.168.64.22:8080/docer/XForms?action=search>*

Le credenziali di accesso sono admin/admin

http://192.168.64.22:8080/docersystem/services/listServices

Per quanto riguarda i servizi soap questi sono gli indirizzi dei wsdl

Autenticazione
http://192.168.64.22:8080/docersystem/services/AuthenticationService?wsdl

Servizio Documentale
http://192.168.64.22:8080/WSDocer/services/DocerServices?wsdl

Servizio Protocollazione
http://192.168.64.22:8080/WSProtocollazione/services/WSProtocollazione?wsdl

Servizio Fascicolazione
http://192.168.64.22:8080/WSFascicolazione/services/WSFascicolazione?wsdl

Possono iniziare anche senza una base dati , ho preconfigurato ENTE/AOO
tramite i codici IPA , quindi è possibile inserire documenti , creare
titolari / fascicoli .. insomma tutto il necessario

Buona serata
Alessio

