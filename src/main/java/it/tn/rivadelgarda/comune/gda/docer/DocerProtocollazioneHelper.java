package it.tn.rivadelgarda.comune.gda.docer;

import it.kdm.docer.webservices.DocerServicesStub;
import it.kdm.docer.webservices.DocerServicesStub.ArchiviaDocumento;
import it.kdm.docer.webservices.DocerServicesStub.ArchiviaDocumentoResponse;
import it.kdm.docer.webservices.DocerServicesStub.ClassificaDocumento;
import it.kdm.docer.webservices.DocerServicesStub.ClassificaDocumentoResponse;
import it.kdm.docer.webservices.DocerServicesStub.KeyValuePair;
import it.kdm.docer.webservices.DocerServicesStub.ProtocollaDocumento;
import it.kdm.docer.webservices.DocerServicesStub.ProtocollaDocumentoResponse;

public class DocerProtocollazioneHelper extends AbstractDocerHelper {

	public DocerProtocollazioneHelper(String docerSerivcesUrl, String docerUsername, String docerPassword) {
		super(docerSerivcesUrl, docerUsername, docerPassword);
	}

	/**
	 * Questo metodo permette l’assegnazione dei metadati di protocollazione ad
	 * un Documento del DMS
	 * 
	 * @param documentId
	 *            La variabile docId è l’id del Documento nel DMS.
	 * @param related
	 *            L’oggetto metadata[] è una collezione di nodi metadata. Ogni
	 *            nodo metadata contiene una KeyValuePair ovvero due nodi, key e
	 *            value, di tipo string dove i valori ammessi per i nodi key
	 *            sono i nomi dei metadati del profilo relativi alla
	 *            protocollazione di un documento(si veda paragrafo 4.4. Profilo
	 *            di un documento).
	 * @return true se l’operazione è andata a buon fine
	 * @throws Exception
	 */
	public boolean protocollaDocumento(String documentId, KeyValuePair[] metadata) throws Exception {
		DocerServicesStub service = new DocerServicesStub(docerSerivcesUrl + DocerServices);
		ProtocollaDocumento request = new ProtocollaDocumento();
		request.setToken(getLoginTicket());
		request.setDocId(documentId);
		request.setMetadata(metadata);
		ProtocollaDocumentoResponse response = service.protocollaDocumento(request);
		boolean esito = response.get_return();
		return esito;
	}

	/**
	 * Questo metodo permette la classificazione di un Documento e di tutti i
	 * suoi related nel DMS.
	 * 
	 * @param documentId
	 *            id del Documento
	 * @param metadata
	 *            L’oggetto metadata[] è una collezione di nodi metadata. Ogni
	 *            nodo metadata contiene una KeyValuePair ovvero due nodi, key e
	 *            value, di tipo string dove i valori ammessi per i nodi key
	 *            sono i nomi dei metadati del profilo relativi alla
	 *            classificazione (si veda paragrafo 4.4. Profilo di un
	 *            documento).
	 * @return
	 * @throws Exception
	 */
	public boolean classificaDocumento(String documentId, KeyValuePair[] metadata) throws Exception {
		DocerServicesStub service = new DocerServicesStub(docerSerivcesUrl + DocerServices);
		ClassificaDocumento request = new ClassificaDocumento();
		request.setToken(getLoginTicket());
		request.setDocId(documentId);
		request.setMetadata(metadata);
		ClassificaDocumentoResponse response = service.classificaDocumento(request);
		boolean esito = response.get_return();
		return esito;
	}

	/**
	 * Questo metodo permette l’archiviazione (in archivio di deposito) di un
	 * Documento e di tutti i suoi related nel DMS.
	 * 
	 * @param documentId
	 *            id del Documento
	 * @param metadata
	 *            L’oggetto metadata[] è una collezione di nodi metadata. Ogni
	 *            nodo metadata contiene una KeyValuePair ovvero due nodi, key e
	 *            value, di tipo string dove i valori ammessi per i nodi key
	 *            sono i nomi dei metadati del profilo relativi all’archivio di
	 *            deposito (si veda paragrafo 4.4. Profilo di un documento).
	 * @return
	 * @throws Exception
	 */
	public boolean archiviaDocumento(String documentId, KeyValuePair[] metadata) throws Exception {
		DocerServicesStub service = new DocerServicesStub(docerSerivcesUrl + DocerServices);
		ArchiviaDocumento request = new ArchiviaDocumento();
		request.setToken(getLoginTicket());
		request.setDocId(documentId);
		request.setMetadata(metadata);
		ArchiviaDocumentoResponse response = service.archiviaDocumento(request);
		boolean esito = response.get_return();
		return esito;
	}
}
