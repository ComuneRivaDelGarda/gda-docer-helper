# gda-docer-helper
Libreria di accesso DOCER Api SOAP

## API DOC

- [https://comunerivadelgarda.github.io/gda-docer-helper/](https://comunerivadelgarda.github.io/gda-docer-helper/)
- [https://comunerivadelgarda.github.io/gda-docer-helper/apidocs/](https://comunerivadelgarda.github.io/gda-docer-helper/apidocs/)

## ESEMPIO

```java
DocerHelper helper = new DocerHelper(docerUrl, username, password);
// creazione 
helper.createFolder("TEST");
List<Map<String, String>> res = helper.searchFolders("TEST*");
```

config.properties richiesto nel classpath con configurazione connessione a DOCER

	url=http://192.168.64.22:8080/
	username=admin
	password=admin

## API HELPER

Ripilogo API Helper:
- [x] VERSAMENTO (testate ed usate nell'Explorer)*
  - [x] createFolder
  - [x] createDocument
  - [x] addToFolderDocuments
  - [x] removeFromFolderDocuments
  - [x] deleteDocument
  - [x] setACLDocument**
  - [x] addRelated
  - [x] searchFolders
  - [x] getFolderDocuments

> *dalla versione 1.1 dell'Helper (numerazione virtuale usata solo per comunicarvi via email gli aggiornamenti, perché internamente il progetto è sempre 1.0.0 per non crearvi problemi con le dipendenze nei progetti) sono disponibili le aggiunte ed adeguamenti per EXTERNAL_ID
> - createDocument
> - updateProfileDocumentExternalId
> - createDocumentTypeDocumentoAndRelateToExternalId
> - searchDocumentsByExternalId

> **non richiamata da Explorer
   
- [x] VERSIONAMENTO (testate ed usate nell'Explorer)
  - [x] addNewVersion
  - [x] getVersions
  - [x] downloadVersion

- [x] PROTOCOLLAZIONE E ARCHIVIAZIONE (pronte ma non richiamate)*
  - [x] protocollaDocumento
  - [x] classificaDocumenti
  - [x] archiviaDocumenti

> *non essendo ancora state usate potrebbe rivelarsi utile, come avvenuto per VERSAMENTO e VERSIONAMENTO uno strato ulteriore di incapsulamento dei parametri previsti\gestiti per semplificare ulteriormente le chiamate (basta valutare insieme se e dove può essere utile).

## DOCER
﻿
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

## COMPILAZIONE/AGGIORNAMENTI PROGETTO

i file generati con axis2 wsdl2code sono inclusi nei sorgenti

### CREAZIONE PACCHETTO

### package
	mvn clean package

### axis2 generazione codice da wsdl
vedi riferimenti su pom.xml (decommentare plugin opportunamente configurato)

1. esecuzione manuale
va eseguita per ogni wsdl

	mvn axis2-wsdl2code:wsdl2code
	
2. esecuzione automatica 

	mvn clean compile

### documentazione (per pubblicazione su repository)

- https://comunerivadelgarda.github.io/gda-docer-helper/
- https://comunerivadelgarda.github.io/gda-docer-helper/apidocs/

	mvn site
	mvn javadoc:javadoc
	rsync -avz --delete target/site/ docs/
