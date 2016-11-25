package it.tn.rivadelgarda.comune.gda.docer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import org.apache.axiom.attachments.ByteArrayDataSource;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import it.kdm.docer.webservices.DocerServicesStub;
import it.kdm.docer.webservices.DocerServicesStub.AddNewVersion;
import it.kdm.docer.webservices.DocerServicesStub.AddNewVersionResponse;
import it.kdm.docer.webservices.DocerServicesStub.AddRelated;
import it.kdm.docer.webservices.DocerServicesStub.AddRelatedResponse;
import it.kdm.docer.webservices.DocerServicesStub.AddToFolderDocuments;
import it.kdm.docer.webservices.DocerServicesStub.AddToFolderDocumentsResponse;
import it.kdm.docer.webservices.DocerServicesStub.ArchiviaDocumento;
import it.kdm.docer.webservices.DocerServicesStub.ArchiviaDocumentoResponse;
import it.kdm.docer.webservices.DocerServicesStub.ClassificaDocumento;
import it.kdm.docer.webservices.DocerServicesStub.ClassificaDocumentoResponse;
import it.kdm.docer.webservices.DocerServicesStub.CreateDocument;
import it.kdm.docer.webservices.DocerServicesStub.CreateDocumentResponse;
import it.kdm.docer.webservices.DocerServicesStub.CreateFolder;
import it.kdm.docer.webservices.DocerServicesStub.CreateFolderResponse;
import it.kdm.docer.webservices.DocerServicesStub.DeleteDocument;
import it.kdm.docer.webservices.DocerServicesStub.DeleteDocumentResponse;
import it.kdm.docer.webservices.DocerServicesStub.DownloadVersion;
import it.kdm.docer.webservices.DocerServicesStub.DownloadVersionResponse;
import it.kdm.docer.webservices.DocerServicesStub.GetACLDocument;
import it.kdm.docer.webservices.DocerServicesStub.GetACLDocumentResponse;
import it.kdm.docer.webservices.DocerServicesStub.GetFolderDocuments;
import it.kdm.docer.webservices.DocerServicesStub.GetFolderDocumentsResponse;
import it.kdm.docer.webservices.DocerServicesStub.GetProfileDocument;
import it.kdm.docer.webservices.DocerServicesStub.GetProfileDocumentResponse;
import it.kdm.docer.webservices.DocerServicesStub.GetVersions;
import it.kdm.docer.webservices.DocerServicesStub.GetVersionsResponse;
import it.kdm.docer.webservices.DocerServicesStub.KeyValuePair;
import it.kdm.docer.webservices.DocerServicesStub.ProtocollaDocumento;
import it.kdm.docer.webservices.DocerServicesStub.ProtocollaDocumentoResponse;
import it.kdm.docer.webservices.DocerServicesStub.RemoveFromFolderDocuments;
import it.kdm.docer.webservices.DocerServicesStub.RemoveFromFolderDocumentsResponse;
import it.kdm.docer.webservices.DocerServicesStub.SearchFolders;
import it.kdm.docer.webservices.DocerServicesStub.SearchFoldersResponse;
import it.kdm.docer.webservices.DocerServicesStub.SearchItem;
import it.kdm.docer.webservices.DocerServicesStub.SetACLDocument;
import it.kdm.docer.webservices.DocerServicesStub.SetACLDocumentResponse;
import it.kdm.docer.webservices.DocerServicesStub.StreamDescriptor;
import it.tn.rivadelgarda.comune.gda.docer.keys.DocerKey;
import it.tn.rivadelgarda.comune.gda.docer.keys.DocumentKeysEnum;
import it.tn.rivadelgarda.comune.gda.docer.keys.DocumentKeysEnum.ARCHIVE_TYPE;
import it.tn.rivadelgarda.comune.gda.docer.keys.DocumentKeysEnum.TIPO_COMPONENTE;
import it.tn.rivadelgarda.comune.gda.docer.keys.FolderKeysEnum;
import it.tn.rivadelgarda.comune.gda.docer.values.ACLValuesEnum;

public class DocerHelper extends AbstractDocerHelper {

	public DocerHelper(String docerSerivcesUrl, String docerUsername, String docerPassword) {
		super(docerSerivcesUrl, docerUsername, docerPassword);
	}

	/** VERSAMENTO */

	/**
	 * Questo metodo permette la creazione di una Folder nel DMS.
	 * 
	 * @param folderName
	 * @return
	 * @throws Exception
	 */
	public String createFolder(String folderName) throws Exception {
		/*
		 * KeyValuePair[] folderinfo = new KeyValuePair[3]; folderinfo[0] =
		 * KeyValuePairFactory.createKey(GenericKeyValuePair.FOLDER_NAME,
		 * folderName); folderinfo[1] =
		 * KeyValuePairFactory.createKey(GenericKeyValuePair.COD_ENTE,
		 * docerCodiceENTE); folderinfo[2] =
		 * KeyValuePairFactory.createKey(GenericKeyValuePair.COD_AOO,
		 * docerCodiceAOO);
		 */
		KeyValuePair[] folderinfo = KeyValuePairFactory.build(FolderKeysEnum.FOLDER_NAME, folderName)
				.add(FolderKeysEnum.COD_ENTE, docerCodiceENTE).add(FolderKeysEnum.COD_AOO, docerCodiceAOO).get();

		DocerServicesStub service = getDocerService();
		CreateFolder request = new CreateFolder();
		request.setToken(getLoginTicket());
		request.setFolderInfo(folderinfo);
		CreateFolderResponse response = service.createFolder(request);
		String folderId = response.get_return();
		return folderId;
	}

	/**
	 * Questo metodo permette di eseguire le ricerche sulle Folder del DMS.
	 * 
	 * @param folderName
	 * @return
	 * @throws Exception
	 */
	public SearchItem[] searchFolders(String folderName) throws Exception {
		KeyValuePair[] param = new KeyValuePair[3];
		if (StringUtils.isNoneEmpty(folderName))
			param[0] = KeyValuePairFactory.createKey(DocerKey.FOLDER_NAME, folderName);
		else
			param[0] = KeyValuePairFactory.createKey(DocerKey.FOLDER_NAME, "*");
		param[1] = KeyValuePairFactory.createKey(DocerKey.COD_ENTE, docerCodiceENTE);
		param[2] = KeyValuePairFactory.createKey(DocerKey.COD_AOO, docerCodiceAOO);

		KeyValuePair[] search = new KeyValuePair[1];
		search[0] = KeyValuePairFactory.createKeyOrderByAsc(DocerKey.FOLDER_NAME);

		DocerServicesStub service = getDocerService();
		SearchFolders request = new SearchFolders();
		request.setToken(getLoginTicket());
		request.setSearchCriteria(param);
		request.setMaxRows(-1);
		request.setOrderby(search);

		SearchFoldersResponse response = service.searchFolders(request);
		SearchItem[] folders = response.get_return();
		return folders;
	}

	/**
	 * Questo metodo permette la creazione di un Documento nel DMS.
	 * 
	 * @param typeId
	 * @param documentName
	 * @param file
	 * @param tipoComponente
	 *            uno dei TIPO_COMPONENTE validi
	 * @return
	 * @throws Exception
	 */
	public String createDocument(String typeId, String documentName, DataSource dataSource,
			TIPO_COMPONENTE tipoComponente, String description) throws Exception {
		KeyValuePairFactory params = KeyValuePairFactory.createDocumentKeys(typeId, documentName, docerCodiceENTE,
				docerCodiceAOO);

		DataHandler dataHandler = new DataHandler(dataSource);

		params.add(DocumentKeysEnum.APP_VERSANTE, docerApplication);
		String md5 = DigestUtils.md5Hex(dataSource.getInputStream());
		params.add(DocumentKeysEnum.DOC_HASH, md5);
		params.add(DocumentKeysEnum.TIPO_COMPONENTE, tipoComponente.getValue());
		params.add(DocumentKeysEnum.ABSTRACT, description);
		params.add(DocumentKeysEnum.ARCHIVE_TYPE, ARCHIVE_TYPE.ARCHIVE);

		DocerServicesStub service = getDocerService();
		CreateDocument request = new CreateDocument();
		request.setToken(getLoginTicket());
		request.setMetadata(params.get());
		request.setFile(dataHandler);
		CreateDocumentResponse response = service.createDocument(request);
		String documentId = response.get_return();
		return documentId;
	}

	public String createDocument(String typeId, String documentName, File file, TIPO_COMPONENTE tipoComponente,
			String description) throws Exception {
		FileDataSource fileDataSource = new FileDataSource(file);
		return createDocument(typeId, documentName, fileDataSource, tipoComponente, description);
	}

	public String createDocument(String typeId, String documentName, byte[] bytes, TIPO_COMPONENTE tipoComponente,
			String description) throws Exception {
		ByteArrayDataSource rawData = new ByteArrayDataSource(bytes);
		return createDocument(typeId, documentName, rawData, tipoComponente, description);
	}

	/**
	 * Questo metodo permette di aggiungere Documenti ad una Folder del DMS.
	 * 
	 * @param folderId
	 * @param documentsIds
	 * @return
	 * @throws Exception
	 */
	public boolean addToFolderDocuments(String folderId, List<String> documentsIds) throws Exception {
		DocerServicesStub service = getDocerService();
		AddToFolderDocuments request = new AddToFolderDocuments();
		request.setToken(getLoginTicket());
		request.setFolderId(folderId);
		request.setDocument(documentsIds.toArray(new String[documentsIds.size()]));
		AddToFolderDocumentsResponse response = service.addToFolderDocuments(request);
		boolean esito = response.get_return();
		return esito;
	}

	/**
	 * Questo metodo permette di aggiungere Documenti ad una Folder del DMS.
	 * 
	 * @param folderId
	 * @param documentsIds
	 * @return
	 * @throws Exception
	 */
	public boolean addToFolderDocuments(String folderId, String documentsIds) throws Exception {
		return addToFolderDocuments(folderId, Arrays.asList(documentsIds));
	}

	/**
	 * Questo metodo permette di togliere da una Folder uno o più Documenti del
	 * DMS.
	 * 
	 * @param folderId
	 * @param documentsIds
	 * @return
	 * @throws Exception
	 */
	public boolean removeFromFolderDocuments(String folderId, List<String> documentsIds) throws Exception {
		DocerServicesStub service = getDocerService();
		RemoveFromFolderDocuments request = new RemoveFromFolderDocuments();
		request.setToken(getLoginTicket());
		request.setFolderId(folderId);
		request.setDocument(documentsIds.toArray(new String[documentsIds.size()]));
		RemoveFromFolderDocumentsResponse response = service.removeFromFolderDocuments(request);
		boolean esito = response.get_return();
		return esito;
	}

	/**
	 * Questo metodo permette di togliere da una Folder uno o più Documenti del
	 * DMS.
	 * 
	 * @param folderId
	 * @param documentsIds
	 * @return
	 * @throws Exception
	 */
	public boolean removeFromFolderDocuments(String folderId, String documentsIds) throws Exception {
		return removeFromFolderDocuments(folderId, Arrays.asList(documentsIds));
	}

	/**
	 * Questo metodo permette di eliminare un Documento del DMS. E’ possibile
	 * eliminare solo i Documenti che siano STATO_ARCHIVISTICO 0 (Generico
	 * Document).
	 * 
	 * @param folderId
	 * @param documentId
	 *            id del Documento
	 * @return
	 * @throws Exception
	 */
	public boolean deleteDocument(String documentId) throws Exception {
		DocerServicesStub service = getDocerService();
		DeleteDocument request = new DeleteDocument();
		request.setToken(getLoginTicket());
		request.setDocId(documentId);
		DeleteDocumentResponse response = service.deleteDocument(request);
		boolean esito = response.get_return();
		return esito;
	}

	/**
	 * Questo metodo permette di recuperare la lista dei Documenti contenuti in
	 * una Folder del DMS.
	 * 
	 * @param folderId
	 *            id della
	 * @return
	 * @throws Exception
	 */
	public List<String> getFolderDocuments(String folderId) throws Exception {
		List<String> res = new ArrayList<>();
		DocerServicesStub service = getDocerService();
		GetFolderDocuments request = new GetFolderDocuments();
		request.setToken(getLoginTicket());
		request.setFolderId(folderId);
		GetFolderDocumentsResponse response = service.getFolderDocuments(request);
		if (response.get_return() != null) {
			res = Arrays.asList(response.get_return());
		}
		return res;
	}

	/**
	 * Questo metodo permette di recuperare il profilo di un Documento del DMS.
	 * 
	 * @param documentId
	 *            id del Documento
	 * @return Profilo del Documento
	 */
	public KeyValuePair[] getProfileDocument(String documentId) throws Exception {
		DocerServicesStub service = getDocerService();
		GetProfileDocument request = new GetProfileDocument();
		request.setToken(getLoginTicket());
		request.setDocId(documentId);
		GetProfileDocumentResponse response = service.getProfileDocument(request);
		KeyValuePair[] res = response.get_return();
		return res;
	}

	/**
	 * Questo metodo permette di recuperare i diritti di un Documento del DMS.
	 * 
	 * @param documentId
	 *            id del Documento
	 * @return
	 * @throws Exception
	 */
	public KeyValuePair[] getACLDocument(String documentId) throws Exception {
		DocerServicesStub service = getDocerService();
		GetACLDocument request = new GetACLDocument();
		request.setToken(getLoginTicket());
		request.setDocId(documentId);
		GetACLDocumentResponse response = service.getACLDocument(request);
		KeyValuePair[] res = response.get_return();
		return res;
	}

	/**
	 * Questo metodo permette di assegnare le ACL di un Documento del DMS, nel
	 * caso di documenti con ACL già definite i diritti assegnati sovrascrivono
	 * quelli precedenti.
	 * 
	 * @param documentId
	 *            id del Documento
	 * @param acls
	 *            L’oggetto acls[] è una collezione di nodi acls. Ogni nodo acls
	 *            contiene una KeyValuePair ovvero due nodi, key e value, di
	 *            tipo string dove un nodo key contiene un GROUP_ID di un Gruppo
	 *            o la USER_ID di un Utente del DMS ed il relativo value
	 *            contiene il diritto da assegnare al Documento. Per i diritti è
	 *            assunta la seguente convenzione (si veda il paragrafo 4.6
	 *            Livelli di Accesso ai documenti e anagrafiche): • 2 se si
	 *            vuole assegnare “Read Only Access” • 1 se si vuole assegnare
	 *            “Normal Access” • 0 se si vuole assegnare “Full Access”
	 * @return
	 * @throws Exception
	 */
	public boolean setACLDocument(String documentId, KeyValuePair[] acls) throws Exception {
		DocerServicesStub service = getDocerService();
		SetACLDocument request = new SetACLDocument();
		request.setToken(getLoginTicket());
		request.setDocId(documentId);
		request.setAcls(acls);
		SetACLDocumentResponse response = service.setACLDocument(request);
		boolean esito = response.get_return();
		return esito;
	}

	/**
	 * 
	 * @param documentId
	 *            id del Documento
	 * @param GROUP_USER_ID
	 *            groupId or userId
	 * @param acl
	 * @return
	 * @throws Exception
	 */
	public boolean setACLDocument(String documentId, String GROUP_USER_ID, ACLValuesEnum acl) throws Exception {
		return setACLDocument(documentId, KeyValuePairFactory.build(GROUP_USER_ID, acl.getValue()).get());
	}

	/**
	 * Questo metodo permette di correlare un Documento ad uno o più Documenti
	 * nel DMS.
	 * 
	 * @param documentId
	 *            id del Documento
	 * @param related
	 *            La variabile related[] è una collezione di nodi related. Ogni
	 *            nodo related contiene un id di un Documento del DMS da
	 *            correlare al documento di riferimento. Per il concetto di
	 *            “correlazione” si veda il paragrafo 4.6 Gestione della
	 *            correlazione tra documenti.
	 * @return
	 * @throws Exception
	 */
	public boolean addRelated(String documentId, String[] related) throws Exception {
		DocerServicesStub service = getDocerService();
		AddRelated request = new AddRelated();
		request.setToken(getLoginTicket());
		request.setDocId(documentId);
		request.setRelated(related);
		AddRelatedResponse response = service.addRelated(request);
		boolean esito = response.get_return();
		return esito;
	}

	/** VERSIONAMENTO */

	/**
	 * Questo metodo permette di creare una nuova versione del file (o documento
	 * elettronico) di un Documento nel DMS. Il metodo è applicabile per la sola
	 * gestione del versioning standard.
	 * 
	 * @param documentId
	 *            id del Documento
	 * @param file
	 *            La nuova versione del file o documento elettronico
	 * @return Il version number della versione creata
	 * @throws Exception
	 */
	public String addNewVersion(String documentId, DataSource dataSource) throws Exception {
		// FileDataSource fileDataSource = new FileDataSource(file);
		DataHandler dataHandler = new DataHandler(dataSource);

		DocerServicesStub service = getDocerService();
		AddNewVersion request = new AddNewVersion();
		request.setToken(getLoginTicket());
		request.setDocId(documentId);
		request.setFile(dataHandler);
		AddNewVersionResponse response = service.addNewVersion(request);
		String version = response.get_return();
		return version;
	}

	/**
	 * Questo metodo permette di recuperare la lista dei version number ovvero
	 * delle versioni dei file di un Documento del DMS. Il metodo è applicabile
	 * per la sola gestione del versioning standard.
	 * 
	 * @param documentId
	 *            id del Documento di riferimento
	 * @return collezione dei version number
	 * @throws Exception
	 */
	public List<String> getVersions(String documentId) throws Exception {
		List<String> versions = new ArrayList<>();
		DocerServicesStub service = getDocerService();
		GetVersions request = new GetVersions();
		request.setToken(getLoginTicket());
		request.setDocId(documentId);
		GetVersionsResponse response = service.getVersions(request);
		if (response.get_return() != null)
			versions = Arrays.asList(response.get_return());
		return versions;
	}

	/**
	 * Questo metodo permette di recuperare una specifica versione del file (o
	 * documento elettronico) di un Documento nel DMS.
	 * 
	 * @param documentId
	 *            id del Documento
	 * @param versionNumber
	 *            Version number del file
	 * @return L’oggetto StreamDescriptor contiene una variabile “byteSize” di
	 *         tipo Long che rappresenta la dimensione del file e una variabile
	 *         “handler” che rappresenta il file o documento elettronico
	 *         relativo alla versione richiesta
	 * @throws Exception
	 */
	public StreamDescriptor downloadVersion(String documentId, String versionNumber) throws Exception {
		DocerServicesStub service = getDocerService();
		DownloadVersion request = new DownloadVersion();
		request.setToken(getLoginTicket());
		request.setDocId(documentId);
		request.setVersionNumber(versionNumber);
		DownloadVersionResponse response = service.downloadVersion(request);
		StreamDescriptor res = response.get_return();
		return res;
	}

	/**
	 * 
	 * @param documentId
	 *            documentsId id del Documento
	 * @param versionNumber
	 *            Version number del file
	 * @param file
	 *            file di destinazione
	 * @throws Exception
	 */
	public void downloadVersionTo(String documentId, String versionNumber, File file) throws Exception {
		StreamDescriptor data = downloadVersion(documentId, versionNumber);
		long size = data.getByteSize();
		DataHandler dh = data.getHandler();
		FileUtils.copyInputStreamToFile(dh.getInputStream(), file);
	}

	/** PROTOCOLLAZIONE */

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
		DocerServicesStub service = getDocerService();
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
		DocerServicesStub service = getDocerService();
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
		DocerServicesStub service = getDocerService();
		ArchiviaDocumento request = new ArchiviaDocumento();
		request.setToken(getLoginTicket());
		request.setDocId(documentId);
		request.setMetadata(metadata);
		ArchiviaDocumentoResponse response = service.archiviaDocumento(request);
		boolean esito = response.get_return();
		return esito;
	}

	/** HELPER */

	/**
	 * restituisce una lista di mappe con le coppie chiave/valore di tutti i
	 * metadati disponibili nel profilo documento dei documenti contenuti nella
	 * cartella
	 * 
	 * @param folderId
	 * @return
	 * @throws Exception
	 */
	public List<KeyValuePair> chilren(String folderId) throws Exception {
		List<KeyValuePair> res = new ArrayList<>();
		List<String> folderDocuments = getFolderDocuments(folderId);
		for (String documentId : folderDocuments) {
			KeyValuePair[] profiloDocumento = getProfileDocument(documentId);
			if (profiloDocumento != null)
				res.addAll(Arrays.asList(profiloDocumento));
		}
		return res;
	}

	/**
	 * restituisce il numero di documenti contenuti in una cartella
	 * 
	 * @param folderId
	 * @return
	 */
	public int numberOfDocument(String folderId) throws Exception {
		List<String> folderDocuments = getFolderDocuments(folderId);
		return folderDocuments.size();
	}

	/**
	 * crea un nuovo documento nella cartella
	 * 
	 * @param folderId
	 * @param name
	 * @param content
	 * @param title
	 * @param description
	 * @return
	 */
	public String createDocument(String folderId, String name, byte[] content, String title, String description) {
		return createDocument(folderId, name, content, title, description);
	}

	/**
	 * crea una nuova versione per il documento
	 * 
	 * @param documentId
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public String createVersion(String documentId, byte[] content) throws Exception {
		ByteArrayDataSource rawData = new ByteArrayDataSource(content);
		return addNewVersion(documentId, rawData);
	}

	/**
	 * 
	 * @param documentId
	 * @param versionNumber
	 * @return
	 * @throws Exception
	 */
	public InputStream getDocumentStream(String documentId, String versionNumber) throws Exception {
		StreamDescriptor data = downloadVersion(documentId, versionNumber);
		long size = data.getByteSize();
		DataHandler dh = data.getHandler();
		return dh.getInputStream();
	}

	/**
	 * restituisce il file del documento come binario;
	 * 
	 * @param documentId
	 * @param versionNumber
	 * @return
	 * @throws Exception 
	 */
	public byte[] getDocument(String documentId, String versionNumber) throws Exception {
		return IOUtils.toByteArray(getDocumentStream(documentId, versionNumber));
	}

}
