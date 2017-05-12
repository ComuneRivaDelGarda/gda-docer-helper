package it.tn.rivadelgarda.comune.gda.docer;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import org.apache.axiom.attachments.ByteArrayDataSource;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import it.kdm.docer.webservices.DocerServicesStub.CreateGroup;
import it.kdm.docer.webservices.DocerServicesStub.CreateGroupResponse;
import it.kdm.docer.webservices.DocerServicesStub.CreateUser;
import it.kdm.docer.webservices.DocerServicesStub.CreateUserResponse;
import it.kdm.docer.webservices.DocerServicesStub.DeleteDocument;
import it.kdm.docer.webservices.DocerServicesStub.DeleteDocumentResponse;
import it.kdm.docer.webservices.DocerServicesStub.DownloadVersion;
import it.kdm.docer.webservices.DocerServicesStub.DownloadVersionResponse;
import it.kdm.docer.webservices.DocerServicesStub.GetACLDocument;
import it.kdm.docer.webservices.DocerServicesStub.GetACLDocumentResponse;
import it.kdm.docer.webservices.DocerServicesStub.GetFolderDocuments;
import it.kdm.docer.webservices.DocerServicesStub.GetFolderDocumentsResponse;
import it.kdm.docer.webservices.DocerServicesStub.GetGroup;
import it.kdm.docer.webservices.DocerServicesStub.GetGroupResponse;
import it.kdm.docer.webservices.DocerServicesStub.GetGroupsOfUser;
import it.kdm.docer.webservices.DocerServicesStub.GetGroupsOfUserResponse;
import it.kdm.docer.webservices.DocerServicesStub.GetProfileDocument;
import it.kdm.docer.webservices.DocerServicesStub.GetProfileDocumentResponse;
import it.kdm.docer.webservices.DocerServicesStub.GetRelatedDocuments;
import it.kdm.docer.webservices.DocerServicesStub.GetRelatedDocumentsResponse;
import it.kdm.docer.webservices.DocerServicesStub.GetUser;
import it.kdm.docer.webservices.DocerServicesStub.GetUserResponse;
import it.kdm.docer.webservices.DocerServicesStub.GetUsersOfGroup;
import it.kdm.docer.webservices.DocerServicesStub.GetUsersOfGroupResponse;
import it.kdm.docer.webservices.DocerServicesStub.GetVersions;
import it.kdm.docer.webservices.DocerServicesStub.GetVersionsResponse;
import it.kdm.docer.webservices.DocerServicesStub.KeyValuePair;
import it.kdm.docer.webservices.DocerServicesStub.ProtocollaDocumento;
import it.kdm.docer.webservices.DocerServicesStub.ProtocollaDocumentoResponse;
import it.kdm.docer.webservices.DocerServicesStub.RemoveFromFolderDocuments;
import it.kdm.docer.webservices.DocerServicesStub.RemoveFromFolderDocumentsResponse;
import it.kdm.docer.webservices.DocerServicesStub.SearchDocuments;
import it.kdm.docer.webservices.DocerServicesStub.SearchDocumentsResponse;
import it.kdm.docer.webservices.DocerServicesStub.SearchFolders;
import it.kdm.docer.webservices.DocerServicesStub.SearchFoldersResponse;
import it.kdm.docer.webservices.DocerServicesStub.SearchGroups;
import it.kdm.docer.webservices.DocerServicesStub.SearchGroupsResponse;
import it.kdm.docer.webservices.DocerServicesStub.SearchItem;
import it.kdm.docer.webservices.DocerServicesStub.SearchUsers;
import it.kdm.docer.webservices.DocerServicesStub.SearchUsersResponse;
import it.kdm.docer.webservices.DocerServicesStub.SetACLDocument;
import it.kdm.docer.webservices.DocerServicesStub.SetACLDocumentResponse;
import it.kdm.docer.webservices.DocerServicesStub.SetGroupsOfUser;
import it.kdm.docer.webservices.DocerServicesStub.SetGroupsOfUserResponse;
import it.kdm.docer.webservices.DocerServicesStub.StreamDescriptor;
import it.kdm.docer.webservices.DocerServicesStub.UpdateGroup;
import it.kdm.docer.webservices.DocerServicesStub.UpdateGroupResponse;
import it.kdm.docer.webservices.DocerServicesStub.UpdateGroupsOfUser;
import it.kdm.docer.webservices.DocerServicesStub.UpdateGroupsOfUserResponse;
import it.kdm.docer.webservices.DocerServicesStub.UpdateProfileDocument;
import it.kdm.docer.webservices.DocerServicesStub.UpdateProfileDocumentResponse;
import it.kdm.docer.webservices.DocerServicesStub.UpdateUser;
import it.kdm.docer.webservices.DocerServicesStub.UpdateUserResponse;
import it.tn.rivadelgarda.comune.gda.docer.keys.MetadatiDocumento;
import it.tn.rivadelgarda.comune.gda.docer.keys.MetadatiDocumento.ARCHIVE_TYPE_VALUES;
import it.tn.rivadelgarda.comune.gda.docer.keys.MetadatiDocumento.TIPO_COMPONENTE_VALUES;
import it.tn.rivadelgarda.comune.gda.docer.keys.MetadatiFolder;
import it.tn.rivadelgarda.comune.gda.docer.keys.MetadatiGruppi;
import it.tn.rivadelgarda.comune.gda.docer.keys.MetadatiUtente;
import it.tn.rivadelgarda.comune.gda.docer.keys.MetadatoDocer;
import it.tn.rivadelgarda.comune.gda.docer.values.ACL_VALUES;

public class DocerHelper extends AbstractDocerHelper {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	/**
	 * Crea l'istanza di helper (chiamata a {@link #login()} effettuata
	 * automaticamente alla chiamata di uno dei metodi)
	 * 
	 * <pre>
	 * DocerHelper helper = new DocerHelper(url, username, password);
	 * </pre>
	 * 
	 * @param docerSerivcesUrl
	 *            indirizzo http:// del server Docer al quale connettersi
	 *            (esempio: http://192.168.1.1:8080/)
	 * @param docerUsername
	 *            Docer username per autenticazione
	 * @param docerPassword
	 *            Docer password per autenticazione
	 */
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
		return createFolder(folderName, "", false);
	}

	public String createFolderOwner(String folderName) throws Exception {
		return createFolder(folderName, "", true);
	}

	public String createFolderOwner(String folderName, String parentFolderId) throws Exception {
		return createFolder(folderName, parentFolderId, true);
	}

	public String createFolder(String folderName, String parentFolderId) throws Exception {
		return createFolder(folderName, parentFolderId, false);
	}

	/**
	 * Questo metodo permette la creazione di una Folder nel DMS specificando
	 * anche PARENT_FOLDER_ID
	 * 
	 * @param folderName
	 *            attributo FOLDER_NAME
	 * @param parentFolderId
	 *            attributo PARENT_FOLDER_ID
	 * @return
	 * @throws Exception
	 */
	public String createFolder(String folderName, String parentFolderId, boolean owner) throws Exception {
		KeyValuePairFactory<MetadatiFolder> keyBuilder = KeyValuePairFactory
				.build(MetadatiFolder.FOLDER_NAME, folderName).add(MetadatiFolder.COD_ENTE, docerCodiceENTE)
				.add(MetadatiFolder.COD_AOO, docerCodiceAOO);
		if (StringUtils.isNotBlank(parentFolderId)) {
			keyBuilder.add(MetadatiFolder.PARENT_FOLDER_ID, parentFolderId);
		}
		if (owner) {
			keyBuilder.add(MetadatiFolder.FOLDER_OWNER, docerUsername);
		}

		KeyValuePair[] folderinfo = keyBuilder.get();

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
	 *            specifica il nome della folder da cercare
	 * @param PARENT_FOLDER_ID
	 *            specifica id cartella padre su cui cercare
	 * @return
	 * @throws Exception
	 */
	private SearchItem[] searchFoldersNative(String folderName, String PARENT_FOLDER_ID) throws Exception {
		List<KeyValuePair> builder = new ArrayList<>();
		if (StringUtils.isNoneEmpty(folderName))
			builder.add(KeyValuePairFactory.createKey(MetadatoDocer.FOLDER_NAME_KEY, folderName));
		else
			builder.add(KeyValuePairFactory.createKey(MetadatoDocer.FOLDER_NAME_KEY, "*"));
		builder.add(KeyValuePairFactory.createKey(MetadatoDocer.COD_ENTE_KEY, docerCodiceENTE));
		builder.add(KeyValuePairFactory.createKey(MetadatoDocer.COD_AOO_KEY, docerCodiceAOO));
		if (StringUtils.isNoneEmpty(PARENT_FOLDER_ID)) {
			builder.add(KeyValuePairFactory.createKey(MetadatoDocer.PARENT_FOLDER_ID_KEY, PARENT_FOLDER_ID));
		}
		KeyValuePair[] param = builder.toArray(new KeyValuePair[builder.size()]);

		KeyValuePair[] search = new KeyValuePair[1];
		search[0] = KeyValuePairFactory.createKeyOrderByAsc(MetadatoDocer.FOLDER_NAME_KEY);

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

	// private SearchItem[] searchFoldersByParent(String PARENT_FOLDER_ID)
	// throws Exception {
	// /*
	// * KeyValuePair[] param = new KeyValuePair[4]; param[0] =
	// * KeyValuePairFactory.createKey(DocerKey.FOLDER_NAME, "*"); param[1] =
	// * KeyValuePairFactory.createKey(DocerKey.COD_ENTE, docerCodiceENTE);
	// * param[2] = KeyValuePairFactory.createKey(DocerKey.COD_AOO,
	// * docerCodiceAOO); param[3] =
	// * KeyValuePairFactory.createKey(DocerKey.PARENT_FOLDER_ID,
	// * PARENT_FOLDER_ID);
	// *
	// * KeyValuePair[] search = new KeyValuePair[1]; search[0] =
	// * KeyValuePairFactory.createKeyOrderByAsc(DocerKey.FOLDER_NAME);
	// *
	// * DocerServicesStub service = getDocerService(); SearchFolders request
	// * = new SearchFolders(); request.setToken(getLoginTicket());
	// * request.setSearchCriteria(param); request.setMaxRows(-1);
	// * request.setOrderby(search);
	// *
	// * SearchFoldersResponse response = service.searchFolders(request);
	// * SearchItem[] folders = response.get_return(); return folders;
	// */
	// return searchFoldersNative(null, PARENT_FOLDER_ID);
	// }

	/**
	 * Questo metodo permette di eseguire le ricerche sulle Folder del DMS.
	 * 
	 * @param folderName
	 *            specifica il nome della folder da cercare (null o empty per
	 *            qualsiasi folder)
	 * @param PARENT_FOLDER_ID
	 *            specifica id cartella padre su cui cercare
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> searchFolders(String folderName, String PARENT_FOLDER_ID) throws Exception {
		SearchItem[] data = searchFoldersNative(folderName, PARENT_FOLDER_ID);
		return KeyValuePairFactory.asListMap(data);
	}

	/**
	 * Questo metodo permette di eseguire le ricerche sulle Folder del DMS.
	 * 
	 * @param folderName
	 *            specifica il nome della folder da cercare
	 * @return
	 */
	public List<Map<String, String>> searchFolders(String folderName) throws Exception {
		return searchFolders(folderName, null);
	}

	/**
	 * Questo metodo permette la creazione di un Documento nel DMS.
	 * 
	 * @param TYPE_ID
	 * @param DOCNAME
	 * @param dataSource
	 * @param TIPO_COMPONENTE
	 *            uno dei TIPO_COMPONENTE validi
	 * @return
	 * @throws Exception
	 */
	public String createDocument(String TYPE_ID, String DOCNAME, DataSource dataSource,
			TIPO_COMPONENTE_VALUES TIPO_COMPONENTE, String ABSTRACT, String EXTERNAL_ID) throws Exception {
		KeyValuePairFactory<MetadatiDocumento> params = KeyValuePairFactory.createDocumentKeys(TYPE_ID, DOCNAME,
				docerCodiceENTE, docerCodiceAOO);

		DataHandler dataHandler = new DataHandler(dataSource);

		params.add(MetadatiDocumento.APP_VERSANTE_KEY, docerApplication);
		String md5 = DigestUtils.md5Hex(dataSource.getInputStream());
		params.add(MetadatiDocumento.DOC_HASH_KEY, md5);
		params.add(MetadatiDocumento.TIPO_COMPONENTE_KEY, TIPO_COMPONENTE.getValue());
		if (StringUtils.isNotBlank(ABSTRACT))
			params.add(MetadatiDocumento.ABSTRACT_KEY, ABSTRACT);
		params.add(MetadatiDocumento.ARCHIVE_TYPE_KEY, ARCHIVE_TYPE_VALUES.ARCHIVE_TYPE_ARCHIVE);
		if (StringUtils.isNotBlank(EXTERNAL_ID))
			params.add(MetadatiDocumento.EXTERNAL_ID_KEY, EXTERNAL_ID);

		DocerServicesStub service = getDocerService();
		CreateDocument request = new CreateDocument();
		request.setToken(getLoginTicket());
		request.setMetadata(params.get());
		request.setFile(dataHandler);
		CreateDocumentResponse response = service.createDocument(request);
		String documentId = response.get_return();
		return documentId;
	}

	/**
	 * 
	 * @param docId
	 * @param metadata
	 * @return
	 * @throws Exception
	 */
	private boolean updateProfileDocumentNative(String docId, KeyValuePair[] metadata) throws Exception {
		DocerServicesStub service = getDocerService();
		UpdateProfileDocument request = new UpdateProfileDocument();
		request.setToken(getLoginTicket());
		request.setDocId(docId);
		request.setMetadata(metadata);
		UpdateProfileDocumentResponse response = service.updateProfileDocument(request);
		boolean success = response.get_return();
		return success;
	}

	/**
	 * aggiorna i metadati specificati
	 * 
	 * @param docId
	 *            id del documento
	 * @param metadata
	 *            metadati da aggiornare {@link MetadatiDocumento}
	 * @return
	 * @throws Exception
	 */
	public <F extends MetadatoDocer> boolean updateProfileDocument(String docId, KeyValuePairFactory<F> metadata)
			throws Exception {
		return updateProfileDocumentNative(docId, metadata.get());
	}

	/**
	 * aggiorna il metadato EXTERNAL_ID
	 * 
	 * @param docId
	 *            id del documento
	 * @param externalId
	 *            nuovo valore del metadato EXTERNAL_ID
	 *            {@link MetadatiDocumento}
	 * @return
	 * @throws Exception
	 */
	public boolean updateProfileDocumentExternalId(String docId, String externalId) throws Exception {
		KeyValuePairFactory<MetadatiDocumento> metadata = KeyValuePairFactory.build(MetadatiDocumento.EXTERNAL_ID,
				externalId);
		return updateProfileDocument(docId, metadata);
	}

	/**
	 * Create un Document con Tipo personalizzabile
	 * 
	 * @param TYPE_ID
	 *            set di metadati da utilizzare
	 * @param DOCNAME
	 * @param file
	 * @param TIPO_COMPONENTE
	 * @param ABSTRACT
	 * @param EXTERNAL_ID
	 * @return
	 * @throws Exception
	 */
	public String createDocument(String TYPE_ID, String DOCNAME, File file, TIPO_COMPONENTE_VALUES TIPO_COMPONENTE,
			String ABSTRACT, String EXTERNAL_ID) throws Exception {
		FileDataSource fileDataSource = new FileDataSource(file);
		return createDocument(TYPE_ID, DOCNAME, fileDataSource, TIPO_COMPONENTE, ABSTRACT, EXTERNAL_ID);
	}

	/**
	 * Create un Document con Tipo personalizzabile
	 * 
	 * @param TYPE_ID
	 *            set di metadati da utilizzare
	 * @param DOCNAME
	 * @param bytes
	 * @param TIPO_COMPONENTE
	 * @param ABSTRACT
	 * @param EXTERNAL_ID
	 * @return
	 * @throws Exception
	 */
	public String createDocument(String TYPE_ID, String DOCNAME, byte[] bytes, TIPO_COMPONENTE_VALUES TIPO_COMPONENTE,
			String ABSTRACT, String EXTERNAL_ID) throws Exception {
		ByteArrayDataSource rawData = new ByteArrayDataSource(bytes);
		return createDocument(TYPE_ID, DOCNAME, rawData, TIPO_COMPONENTE, ABSTRACT, EXTERNAL_ID);
	}

	/**
	 * Crea un Documento con il tipo fisso a DOCUMENTO
	 * 
	 * @param DOCNAME
	 * @param file
	 * @param TIPO_COMPONENTE
	 * @param ABSTRACT
	 * @param EXTERNAL_ID
	 * @return
	 * @throws Exception
	 */
	public String createDocumentTypeDocumento(String DOCNAME, File file, TIPO_COMPONENTE_VALUES TIPO_COMPONENTE,
			String ABSTRACT, String EXTERNAL_ID) throws Exception {
		return createDocument(MetadatoDocer.TYPE_ID_DOCUMENTO, DOCNAME, file, TIPO_COMPONENTE, ABSTRACT, EXTERNAL_ID);
	}

	/**
	 * Crea un Documento con il tipo fisso a DOCUMENTO
	 * 
	 * @param DOCNAME
	 * @param bytes
	 * @param TIPO_COMPONENTE
	 * @param ABSTRACT
	 * @param EXTERNAL_ID
	 * @return
	 * @throws Exception
	 */
	public String createDocumentTypeDocumento(String DOCNAME, byte[] bytes, TIPO_COMPONENTE_VALUES TIPO_COMPONENTE,
			String ABSTRACT, String EXTERNAL_ID) throws Exception {
		return createDocument(MetadatoDocer.TYPE_ID_DOCUMENTO, DOCNAME, bytes, TIPO_COMPONENTE, ABSTRACT, EXTERNAL_ID);
	}

	/**
	 * 
	 * @param DOCNAME
	 * @param bytes
	 * @param TIPO_COMPONENTE
	 * @param ABSTRACT
	 * @param EXTERNAL_ID
	 * @return
	 * @throws Exception
	 */
	public String createDocumentTypeDocumentoAndRelateToExternalId(String DOCNAME, byte[] bytes,
			TIPO_COMPONENTE_VALUES TIPO_COMPONENTE, String ABSTRACT, String EXTERNAL_ID) throws Exception {
		String DOCNUM = createDocumentTypeDocumento(DOCNAME, bytes, TIPO_COMPONENTE, ABSTRACT, EXTERNAL_ID);
		// ricerco documenti per EXTERNAL_ID
		Map<String, String> documentByExternalId = searchDocumentsByExternalIdFirst(EXTERNAL_ID);
		if (documentByExternalId != null) {
			String documentToRelateTo = KeyValuePairFactory.getMetadata(documentByExternalId, MetadatiDocumento.DOCNUM);
			if (StringUtils.isNotBlank(documentToRelateTo)) {
				// relaziono il documento appena creato al con altro con stesso
				// EXTERNAL_ID
				addRelated(documentToRelateTo, DOCNUM);
			}
		}
		return DOCNUM;
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
	 *            id della cartella dove aggiungere il documento
	 * @param documentId
	 *            id del documento da aggiungere
	 * @return
	 * @throws Exception
	 */
	public boolean addToFolderDocument(String folderId, String documentId) throws Exception {
		return addToFolderDocuments(folderId, Arrays.asList(documentId));
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
	 * Questo metodo permette di recuperare la lista dei Documenti correlati a
	 * uno specifico externalId
	 *
     * @param searchCriteria
     *            criteri di ricerca
     * @param orderBy
     *            criteri di ordinamento
	 * @return
	 * @throws Exception
	 */
	private SearchItem[] searchDocumentsNative(KeyValuePair[] searchCriteria, String[] keywords, KeyValuePair[] orderBy)
			throws Exception {
		logger.debug("searchDocumentNative {} {}", searchCriteria, orderBy);
		// cerco tutti
		SearchItem[] result = searchDocumentsNative(searchCriteria, keywords, orderBy, -1);
		return result;
	}

	private SearchItem[] searchDocumentsNative(KeyValuePair[] searchCriteria, String[] keywords, KeyValuePair[] orderBy,
			int maxRows) throws Exception {
		logger.debug("searchDocumentNative {} {} {}", searchCriteria, orderBy, maxRows);
		DocerServicesStub service = getDocerService();
		SearchDocuments request = new SearchDocuments();
		request.setToken(getLoginTicket());
		request.setSearchCriteria(searchCriteria);
		request.setKeywords(keywords);
		request.setMaxRows(maxRows);
		request.setOrderby(orderBy);
		SearchDocumentsResponse response = service.searchDocuments(request);
		SearchItem[] result = response.get_return();
		return result;
	}

	/**
	 * Questo metodo permette di eseguire le ricerche sui Documenti del DMS.
	 * <p>
	 * L’oggetto searchCriteria[] è una collezione di nodi searchCriteria. Ogni
	 * nodo searchCriteria contiene una KeyValuePair ovvero due nodi key e value
	 * di tipo string dove i valori ammessi per i nodi key sono i nomi dei
	 * metadati del profilo del Documento. Una stessa key può essere ripetuta
	 * più volte nei searchCriteria: criteri con le stesse chiavi sono in OR
	 * logico tra loro; per chiavi diverse vale l’operatore AND logico. La
	 * sintassi di ricerca segue le regole esposte nel paragrafo 4.10 Regole
	 * sintattiche per i metodi di ricerca. <br>
	 * L’oggetto keywords[] è una collezione di stringhe dove ogni stringa è una
	 * parola chiave da ricercare nel file del documento. <br>
	 * L’oggetto maxRows è un intero che indica il massimo numero di risultati
	 * che devono essere restituiti dalla ricerca. Se maxRows è posto a “-1” il
	 * limite è quello implicito del DMS. L’oggetto orderby[] è una collezione
	 * di nodi KeyValuePair ovvero una collezione di coppie di nodi key e value
	 * di tipo string dove i valori ammessi per i nodi key corrispondono ai nomi
	 * dei metadati del profilo con cui si desiderano ordinare i risultati e i
	 * valori ammessi per i nodi value sono ASC o DESC se si vuole un
	 * ordinamento crescente o decrescente rispettivamente. <br>
	 * L’oggetto di ritorno SearchItem[] è una collezione di nodi SearchItem.
	 * Ogni nodo SearchItem è un sottoinsieme del profilo del Documento trovato
	 * dalla ricerca e contiene una collezione di nodi metadata. Ogni nodo
	 * metadata contiene una KeyValuePair ovvero i nodi key e value di tipo
	 * string dove i valori ammessi per i nodi key sono i nomi dei metadati del
	 * profilo di ogni document- type.
	 * <p>
	 * 4.10 Regole sintattiche per i metodi di ricerca.<br>
	 * Regole sintattiche per i metodi di ricerca <br>
	 * Tutti i metodi di ricerca adottano la seguente sintassi:
	 * <li>Il carattere “*” è la wildcard per le ricerche (applicabile ai tipi
	 * stringa),
	 * <li>il range, o intervallo di ricerca, è nel formato case sensitive "[min
	 * TO max]" con gli estremi inclusi nei risultati.
	 * 
	 * @param searchCriteria
	 *            Collezione dei criteri di ricerca.<br>
	 *            {@link MetadatiDocumento}
	 * @param keywords
	 *            Collezione delle “parole chiave” da ricercare
	 * @param orderBy
	 *            Criteri di ordinamento dei risultati
	 * @param maxRows
	 *            Numero massimo di risultati da restituire
	 * @return Collezione di SearchItem. Ogni SearchItem è una collezione di
	 *         coppie chiave-valore (KeyValuePair) contenente il profilo comune
	 *         di ogni documento trovato dalla ricerca
	 * @throws Exception
	 *             In tutti i casi di errore il metodo solleva una SOAPException
	 *             contenente il messaggio di errore.
	 */
	public List<Map<String, String>> searchDocuments(List<Map<MetadatiDocumento, String>> searchCriteria,
			List<String> keywords, List<Map<MetadatiDocumento, String>> orderBy, int maxRows) throws Exception {
		logger.debug("searchDocuments {} {} {} {}", searchCriteria, keywords, orderBy, maxRows);
		SearchItem[] result = searchDocumentsNative(KeyValuePairFactory.toArray(searchCriteria),
				KeyValuePairFactory.toArrayString(keywords), KeyValuePairFactory.toArray(orderBy), maxRows);
		return KeyValuePairFactory.asListMap(result);
	}

	/**
	 * Questo metodo permette di eseguire le ricerche sui Documenti del DMS
	 * (maxRows è posto a “-1” il limite è quello implicito del DMS).<br>
	 * Vedi documentazione {@link DocerHelper#searchDocuments}.
	 * 
	 * @see DocerHelper#searchDocuments(List, List, int)
	 * @param searchCriteria
	 *            Collezione dei criteri di ricerca.<br>
	 *            {@link MetadatiDocumento}
     * @param keywords
     *            Collezione delle “parole chiave” da ricercare
	 * @param orderBy
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> searchDocuments(List<Map<MetadatiDocumento, String>> searchCriteria,
			List<String> keywords, List<Map<MetadatiDocumento, String>> orderBy) throws Exception {
		return searchDocuments(searchCriteria, keywords, orderBy, -1);
	}

	/**
	 * Questo metodo permette di eseguire le ricerche sui Documenti del DMS
	 * (maxRows è posto a “-1” il limite è quello implicito del DMS).<br>
	 * Vedi documentazione {@link DocerHelper#searchDocuments}.
	 * 
	 * @see DocerHelper#searchDocuments(List, List, int)
	 * @param searchCriteria
	 *            Collezione dei criteri di ricerca.<br>
	 *            {@link MetadatiDocumento}
	 * @param orderBy
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> searchDocuments(List<Map<MetadatiDocumento, String>> searchCriteria,
			List<Map<MetadatiDocumento, String>> orderBy) throws Exception {
		return searchDocuments(searchCriteria, null, orderBy, -1);
	}

	/**
	 * Questo metodo permette di eseguire le ricerche sui Documenti del DMS <br>
	 * Vedi documentazione {@link DocerHelper#searchDocuments}.
	 * 
	 * @see DocerHelper#searchDocuments(List, List, int)
	 * @param searchCriteria
	 *            Collezione dei criteri di ricerca.<br>
	 *            {@link MetadatiDocumento}
	 * @param orderBy
     * @param maxRows
     *            Numero massimo di risultati da restituire
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> searchDocuments(List<Map<MetadatiDocumento, String>> searchCriteria,
			List<Map<MetadatiDocumento, String>> orderBy, int maxRows) throws Exception {
		return searchDocuments(searchCriteria, null, orderBy, maxRows);
	}

	/**
	 * effettua una ricera per EXTERNAL_ID ordinata per CREATION_DATE
	 * 
	 * @param externalId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> searchDocumentsByExternalIdAll(String externalId) throws Exception {
		logger.debug("searchDocumentsByExternalIdAll {}", externalId);
		KeyValuePair[] searchCriteria = KeyValuePairFactory.build(MetadatiDocumento.EXTERNAL_ID, externalId).get();
		KeyValuePair[] orderBy = KeyValuePairFactory.build(MetadatiDocumento.CREATION_DATE, MetadatoDocer.SORT_ASC)
				.get();
		SearchItem[] result = searchDocumentsNative(searchCriteria, null, orderBy, -1);
		return KeyValuePairFactory.asListMap(result);
	}

	public Map<String, String> searchDocumentsByExternalIdFirst(String externalId) throws Exception {
		Map<String, String> profile = new HashMap<>();
		logger.debug("searchDocumentsByExternalIdFirst {}", externalId);
		KeyValuePair[] searchCriteria = KeyValuePairFactory.build(MetadatiDocumento.EXTERNAL_ID, externalId).get();
		KeyValuePair[] orderBy = KeyValuePairFactory.build(MetadatiDocumento.CREATION_DATE, MetadatoDocer.SORT_ASC)
				.get();
		SearchItem[] result = searchDocumentsNative(searchCriteria, null, orderBy, 1);
		List<Map<String, String>> profiles = KeyValuePairFactory.asListMap(result);
		if (!profiles.isEmpty())
			profile = profiles.get(0);
		return profile;
	}

	/**
	 * Ricerca tutti i documenti related partendo da EXTERNAL_ID. Ricerca i
	 * documenti con metadato EXTERNAL_ID, in ordine di data di inserimento in
	 * DOCER. Ricerca successivamente tutti i related al primo dei risultati
	 * precedenti (che potrebbero potenzialmente essere related da un'altra app
	 * senza valorizzazione di EXTERNAL_ID
	 * 
	 * @param externalId
	 *            valore del metadato EXTERNAL_ID da cercare
	 * @return lista dei metadati del primo documento trovato e tutti i
	 *         documenti related
	 * @throws Exception
	 */
	public List<Map<String, String>> searchDocumentsByExternalIdFirstAndRelated(String externalId) throws Exception {
		logger.debug("searchDocumentsByExternalIdFirstAndRelated {}", externalId);
		// carico i metadati di tutti i documenti risultati ricerca + documenti
		// related
		List<Map<String, String>> relatedMetadata = new ArrayList<>();

		Map<String, String> documentByExternalId = searchDocumentsByExternalIdFirst(externalId);
		String firstDocNum = KeyValuePairFactory.getMetadata(documentByExternalId, MetadatiDocumento.DOCNUM);
		if (firstDocNum != null && !"".equals(firstDocNum)) {
			// carico i related al primo docnum
			List<String> relatedDocuments = getRelatedDocuments(firstDocNum);
			// aggiunta metadati risultato ricerca
			Map<String, String> documentProfile = getProfileDocumentMap(firstDocNum);
			relatedMetadata.add(documentProfile);
			// aggiunta metadati related
			for (String documentId : relatedDocuments) {
				documentProfile = getProfileDocumentMap(documentId);
				relatedMetadata.add(documentProfile);
			}
		}
		return relatedMetadata;
	}

	// /**
	// * ricerca tutti i document per EXTERNAL_ID e tutti i related
	// *
	// * @param externalId
	// * valore del metadato EXTERNAL_ID da cercare
	// * @return lista dei metadati di tutti i documenti trovato e tutti i
	// * documenti related a questi
	// * @throws Exception
	// */
	// @Deprecated
	// public List<Map<String, String>>
	// searchDocumentsByExternalIdAllAndRelatedAll(String externalId) throws
	// Exception {
	// logger.debug("searchDocumentsByExternalIdAllAndRelatedAll {}",
	// externalId);
	// List<Map<String, String>> documentsByExternalId =
	// searchDocumentsByExternalIdAll(externalId);
	// // lista dei DOCNUM risultati dalla ricerca
	// String[] firstDocNum =
	// KeyValuePairFactory.joinMetadata(documentsByExternalId,
	// DocumentoMetadatiGenericiEnum.DOCNUM);
	//
	// // carico i metadati di tutti i documenti risultati ricerca + documenti
	// // related
	// List<Map<String, String>> relatedMetadata = new ArrayList<>();
	// for (String docNum : firstDocNum) {
	// // carico i related al primo docnum
	// List<String> relatedDocuments = getRelatedDocuments(docNum);
	//
	// // aggiunta metadati risultato ricerca
	// Map<String, String> documentProfile = getProfileDocumentMap(docNum);
	// relatedMetadata.add(documentProfile);
	// // aggiunta metadati related
	// for (String documentId : relatedDocuments) {
	// documentProfile = getProfileDocumentMap(documentId);
	// relatedMetadata.add(documentProfile);
	// }
	// }
	// return relatedMetadata;
	// }

	/**
	 * 
	 * @param DOCNUM
	 * @return
	 * @throws Exception
	 */
	private String[] getRelatedDocumentsNative(String DOCNUM) throws Exception {
		logger.debug("getRelated {}", DOCNUM);
		DocerServicesStub service = getDocerService();
		GetRelatedDocuments request = new GetRelatedDocuments();
		request.setToken(getLoginTicket());
		request.setDocId(DOCNUM);
		GetRelatedDocumentsResponse response = service.getRelatedDocuments(request);
		String[] result = response.get_return();
		return result;
	}

	/**
	 * 
	 * @param DOCNUM
	 * @return
	 * @throws Exception
	 */
	private List<String> getRelatedDocuments(String DOCNUM) throws Exception {
		List<String> res = new ArrayList<>();
		String[] documents = getRelatedDocumentsNative(DOCNUM);
		if (documents != null) {
			res = Arrays.asList(documents);
		}
		return res;
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
		logger.debug("getFolderDocuments {}", folderId);
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
	private KeyValuePair[] getProfileDocumentNative(String documentId) throws Exception {
		logger.debug("getProfileDocument {}", documentId);
		DocerServicesStub service = getDocerService();
		GetProfileDocument request = new GetProfileDocument();
		request.setToken(getLoginTicket());
		request.setDocId(documentId);
		GetProfileDocumentResponse response = service.getProfileDocument(request);
		KeyValuePair[] res = response.get_return();
		return res;
	}

	/**
	 * Questo metodo permette di recuperare il profilo di un Documento del DMS.
	 * Elabora hashmap dei metadati per il documento specificato
	 * 
	 * @param documentId
	 * @return mappa dei metadati
	 * @throws Exception
	 */
	public Map<String, String> getProfileDocumentMap(String documentId) throws Exception {
		logger.debug("getProfileDocumentMap {}", documentId);
		KeyValuePair[] data = getProfileDocumentNative(documentId);
		Map<String, String> res = new HashMap<>();
		for (KeyValuePair kv : data) {
			// verifico se è un chiave fra quelle del profilo base
			// if
			// (DocumentoMetadatiGenericiEnum.getKeyList().contains(kv.getKey()))
			// {
			res.put(kv.getKey(), kv.getValue());
			// }
		}
		// Map<String, String> test =
		// HashMultimap.create(FluentIterable.from(data).transform(new
		// Function<KeyValuePair, Map<String, String>>() {
		// @Override
		// public Map<String, String> apply(KeyValuePair input) {
		// return
		// }
		// }));
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
		logger.debug("getACLDocument {}", documentId);
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
	 *            Livelli di Accesso ai documenti e anagrafiche):
	 *            <li>2 se si vuole assegnare “Read Only Access”
	 *            <li>1 se si vuole assegnare “Normal Access”
	 *            <li>0 se si vuole assegnare “Full Access”
	 * @return
	 * @throws Exception
	 */
	private boolean setACLDocumentNative(String documentId, KeyValuePair[] acls) throws Exception {
		logger.debug("setACLDocument {} {}", documentId, acls);
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
	 * Imposta ACL
	 * 
	 * @param documentId
	 *            id del Documento
	 * @param GROUP_USER_ID
	 *            groupId or userId
	 * @param acl
	 * @return
	 * @throws Exception
	 */
	public boolean setACLDocument(String documentId, String GROUP_USER_ID, ACL_VALUES acl) throws Exception {
		return setACLDocumentNative(documentId, KeyValuePairFactory.build(GROUP_USER_ID, acl.getValue()).get());
	}

	/**
	 * Sovrascrive le ACLs attuali
	 * 
	 * @param documentId
	 *            su cui applicare le acl
	 * @param acls
	 *            mapps di groupId or userId come chiavi e valori ACL_VALUES
	 *            come valore acl
	 * @return
	 * @throws Exception
	 */
	public boolean setACLDocument(String documentId, Map<String, ACL_VALUES> acls) throws Exception {
		// KeyValuePairFactory.build(GROUP_USER_ID, acl.getValue()).get();
		KeyValuePairFactory<ACL_VALUES> keyBuilder = new KeyValuePairFactory<>();
		for (Entry<String, ACL_VALUES> entry : acls.entrySet()) {
			keyBuilder.add(entry.getKey(), entry.getValue());
		}
		return setACLDocumentNative(documentId, keyBuilder.get());
	}

	public boolean setACLDocument(String documentId, ACLsFactory aclsFactory) throws Exception {
		return setACLDocument(documentId, aclsFactory.get());
	}

	/**
	 * Sovrascrive le ACLs attuali
	 * 
	 * @param documentId
	 *            su cui applicare le acl
	 * @param acls
	 *            mapps di groupId or userId come chiavi e valori interi come
	 *            valore acl
	 * @return
	 * @throws Exception
	 */
	public boolean setACLDocumentConvert(String documentId, Map<String, Integer> acls) throws Exception {
		KeyValuePairFactory<ACL_VALUES> keyBuilder = new KeyValuePairFactory<>();
		for (Entry<String, Integer> entry : acls.entrySet()) {
			keyBuilder.add(entry.getKey(), ACL_VALUES.values()[entry.getValue()]);
		}
		return setACLDocumentNative(documentId, keyBuilder.get());
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

	public boolean addRelated(String documentId, String related) throws Exception {
		return addRelated(documentId, new String[] { related });
	}

	/** VERSIONAMENTO */

	/**
	 * Questo metodo permette di creare una nuova versione del file (o documento
	 * elettronico) di un Documento nel DMS. Il metodo è applicabile per la sola
	 * gestione del versioning standard.
	 * 
	 * @param documentId
	 *            id del Documento
	 * @param dataSource
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
	 * @return collezione dei version number (ordine decrescente dal maggiore al
	 *         minore)
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
		Collections.sort(versions);
		Collections.reverse(versions);
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
	 * @param metadata
	 *            L’oggetto metadata[] è una collezione di nodi metadata. Ogni
	 *            nodo metadata contiene una KeyValuePair ovvero due nodi, key e
	 *            value, di tipo string dove i valori ammessi per i nodi key
	 *            sono i nomi dei metadati del profilo relativi alla
	 *            protocollazione di un documento(si veda paragrafo 4.4. Profilo
	 *            di un documento).
	 * @return true se l’operazione è andata a buon fine
	 * @throws Exception
	 */
	private boolean protocollaDocumentoNative(String documentId, KeyValuePair[] metadata) throws Exception {
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
	 * Questo metodo permette l’assegnazione dei metadati di protocollazione ad
	 * un Documento del DMS.
	 * <p>
	 * La variabile docId è l’id del Documento nel DMS. L’oggetto metadata[] è
	 * una collezione di nodi metadata. Ogni nodo metadata contiene una
	 * KeyValuePair ovvero due nodi, key e value, di tipo string dove i valori
	 * ammessi per i nodi key sono i nomi dei metadati del profilo relativi alla
	 * protocollazione di un documento(si veda paragrafo 4.4. Profilo di un
	 * documento). Per questo metodo i metadati specificabili sono:
	 * <li>COD_ENTE: codice dell’Ente assegnato al documento
	 * <li>COD_AOO: codice della AOO assegnata al documento
	 * <li>NUM_PG: numero di protocollo assegnato al documento
	 * <li>ANNO_PG: anno di protocollo assegnato al documento, se assente viene
	 * ricavata da DATA_PG; se specificato deve coincidere con l’anno presente
	 * in DATA_PG
	 * <li>OGGETTO_PG: oggetto del protocollo
	 * <li>REGISTRO_PG: registro del protocollo
	 * <li>DATA_PG: data di protocollazione
	 * <li>TIPO_PROTOCOLLAZIONE: tipo protocollazione o E (entrata) o I
	 * (interna( o U (uscita) o ND (non definita)
	 * <li>MITTENTI: i mittenti (è anche metadato di Registrazione)
	 * <li>DESTINATARI: i destinatari (è anche metadato di Registrazione)
	 * <li>TIPO_FIRMA: tipo firma (è anche metadato di Registrazione)
	 * <li>FIRMATARIO: firmatario : firmatario (è anche metadato di
	 * Registrazione)
	 * <p>
	 * Il metodo controlla il metadato TIPO_COMPONENTE per tutti gli elementi
	 * della catena dei correlati e verifica che nessun correlato, ad esclusione
	 * del documento individuato da docId abbia TIPO_COMPONENTE uguale a
	 * PRINCIPALE. Al documento oggetto del metodo viene impostato
	 * TIPO_COMPONENTE uguale a PRINCIPALE; agli altri correlati con
	 * TIPO_COMPONENTE non valorizzato viene impostato il valore ALLEGATO. Il
	 * metodo verifica inoltre il valore del metadato DOCNUM_PRINC per tutta la
	 * catena dei correlati: deve essere vuoto o coincidere con docId.
	 * <p>
	 * Se DOCNUM_PRINC non è assegnato viene impostato a docId per tutta la
	 * catena dei correlati ad eccezione del documento individuato da docId per
	 * il quale viene lasciato vuoto. Se uno o più dei metadati condivisi con la
	 * Registrazione (MITTENTI, DESTINATARI, TIPO_FIRMA, FIRMATARIO) risultano
	 * valorizzati, il metodo controlla che nessuna modifica venga apportata a
	 * questi metadati.
	 * <p>
	 * Il metadato STATO_ARCHIVISTICO viene impostato implicitamente a 3
	 * (Protocollato), per tutta la catena dei correlati, solo se il documento
	 * non possiede una classifica o un fascicolo principale 74assegnato,
	 * altrimenti viene impostato al valore 4 (Classificato) o 5 (Fascicolato)
	 * rispettivamente. Questo metodo non è invocabile per i documenti in
	 * STATO_ARCHIVISTICO 6 (in archivio di deposito)
	 * 
	 * @param documentId
	 *            id del Documento
	 * @param metadati
	 *            Collezione dei metadati del profilo da modificare
	 * @return true se l’operazione è andata a buon fine
	 * @throws Exception
	 *             In tutti i casi di errore il metodo solleva una SOAPException
	 *             contenente il messaggio di errore.
	 */
	public boolean protocollaDocumento(String documentId, List<Map<MetadatiDocumento, String>> metadati)
			throws Exception {
		return protocollaDocumentoNative(documentId, KeyValuePairFactory.toArray(metadati));
	}

	/**
	 * Questo metodo permette l’assegnazione dei metadati di protocollazione ad
	 * un Documento del DMS.
	 * <p>
	 * La variabile docId è l’id del Documento nel DMS. L’oggetto metadata[] è
	 * una collezione di nodi metadata. Ogni nodo metadata contiene una
	 * KeyValuePair ovvero due nodi, key e value, di tipo string dove i valori
	 * ammessi per i nodi key sono i nomi dei metadati del profilo relativi alla
	 * protocollazione di un documento(si veda paragrafo 4.4. Profilo di un
	 * documento). Per questo metodo i metadati specificabili sono:
	 * <li>COD_ENTE: codice dell’Ente assegnato al documento
	 * <li>COD_AOO: codice della AOO assegnata al documento
	 * <li>NUM_PG: numero di protocollo assegnato al documento
	 * <li>ANNO_PG: anno di protocollo assegnato al documento, se assente viene
	 * ricavata da DATA_PG; se specificato deve coincidere con l’anno presente
	 * in DATA_PG
	 * <li>OGGETTO_PG: oggetto del protocollo
	 * <li>REGISTRO_PG: registro del protocollo
	 * <li>DATA_PG: data di protocollazione
	 * <li>TIPO_PROTOCOLLAZIONE: tipo protocollazione o E (entrata) o I
	 * (interna( o U (uscita) o ND (non definita)
	 * <li>MITTENTI: i mittenti (è anche metadato di Registrazione)
	 * <li>DESTINATARI: i destinatari (è anche metadato di Registrazione)
	 * <li>TIPO_FIRMA: tipo firma (è anche metadato di Registrazione)
	 * <li>FIRMATARIO: firmatario : firmatario (è anche metadato di
	 * Registrazione)
	 * <p>
	 * Il metodo controlla il metadato TIPO_COMPONENTE per tutti gli elementi
	 * della catena dei correlati e verifica che nessun correlato, ad esclusione
	 * del documento individuato da docId abbia TIPO_COMPONENTE uguale a
	 * PRINCIPALE. Al documento oggetto del metodo viene impostato
	 * TIPO_COMPONENTE uguale a PRINCIPALE; agli altri correlati con
	 * TIPO_COMPONENTE non valorizzato viene impostato il valore ALLEGATO. Il
	 * metodo verifica inoltre il valore del metadato DOCNUM_PRINC per tutta la
	 * catena dei correlati: deve essere vuoto o coincidere con docId.
	 * <p>
	 * Se DOCNUM_PRINC non è assegnato viene impostato a docId per tutta la
	 * catena dei correlati ad eccezione del documento individuato da docId per
	 * il quale viene lasciato vuoto. Se uno o più dei metadati condivisi con la
	 * Registrazione (MITTENTI, DESTINATARI, TIPO_FIRMA, FIRMATARIO) risultano
	 * valorizzati, il metodo controlla che nessuna modifica venga apportata a
	 * questi metadati.
	 * <p>
	 * Il metadato STATO_ARCHIVISTICO viene impostato implicitamente a 3
	 * (Protocollato), per tutta la catena dei correlati, solo se il documento
	 * non possiede una classifica o un fascicolo principale 74assegnato,
	 * altrimenti viene impostato al valore 4 (Classificato) o 5 (Fascicolato)
	 * rispettivamente. Questo metodo non è invocabile per i documenti in
	 * STATO_ARCHIVISTICO 6 (in archivio di deposito)
	 * 
	 * @param documentId
	 *            id del Documento
	 * @param factory
	 *            Collezione dei metadati del profilo da modificare
	 * @return true se l’operazione è andata a buon fine
	 * @throws Exception
	 *             In tutti i casi di errore il metodo solleva una SOAPException
	 *             contenente il messaggio di errore.
	 */
	public boolean protocollaDocumento(String documentId, KeyValuePairFactory<MetadatiDocumento> factory)
			throws Exception {
		return protocollaDocumentoNative(documentId, factory.get());
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
	private boolean classificaDocumentoNative(String documentId, KeyValuePair[] metadata) throws Exception {
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
	 * Questo metodo permette la classificazione di un Documento e di tutti i
	 * suoi related nel DMS.
	 * <p>
	 * La variabile docId è l’id del Documento nel DMS.
	 * <p>
	 * L’oggetto metadata[] è una collezione di nodi metadata. Ogni nodo
	 * metadata contiene una KeyValuePair ovvero due nodi, key e value, di tipo
	 * string dove i valori ammessi per i nodi key sono i nomi dei metadati del
	 * profilo relativi alla classificazione (si veda paragrafo 4.4. Profilo di
	 * un documento).
	 * <p>
	 * Per questo metodo i metadati specificabili sono:
	 * <li>COD_ENTE: codice dell’Ente assegnato al documento
	 * <li>COD_AOO: codice della AOO assegnata al documento
	 * <li>CLASSIFICA: classifica principale assegnata al documento
	 * <p>
	 * Il metadato STATO_ARCHIVISTICO viene impostato implicitamente a 4
	 * (Classificato) per tutta la catena dei correlati solo se il documento
	 * principale si trova in STATO_ARCHIVISTICO 2 (Registrato) o 3
	 * (Protocollato).
	 * <p>
	 * Questo metodo non è invocabile per i documenti in STATO_ARCHIVISTICO 6
	 * (in archivio di deposito).
	 * 
	 * @param documentId
	 *            id del Documento
	 * @param metadati
	 *            Collezione dei metadati del profilo da modificare
	 * @return true se l’operazione è andata a buon fine
	 * @throws Exception
	 *             In tutti i casi di errore il metodo solleva una SOAPException
	 *             contenente il messaggio di errore.
	 */
	public boolean classificaDocumento(String documentId, List<Map<MetadatiDocumento, String>> metadati)
			throws Exception {
		return classificaDocumentoNative(documentId, KeyValuePairFactory.toArray(metadati));
	}

	/**
	 * Questo metodo permette la classificazione di un Documento e di tutti i
	 * suoi related nel DMS.
	 * <p>
	 * La variabile docId è l’id del Documento nel DMS.
	 * <p>
	 * L’oggetto metadata[] è una collezione di nodi metadata. Ogni nodo
	 * metadata contiene una KeyValuePair ovvero due nodi, key e value, di tipo
	 * string dove i valori ammessi per i nodi key sono i nomi dei metadati del
	 * profilo relativi alla classificazione (si veda paragrafo 4.4. Profilo di
	 * un documento).
	 * <p>
	 * Per questo metodo i metadati specificabili sono:
	 * <li>COD_ENTE: codice dell’Ente assegnato al documento
	 * <li>COD_AOO: codice della AOO assegnata al documento
	 * <li>CLASSIFICA: classifica principale assegnata al documento
	 * <p>
	 * Il metadato STATO_ARCHIVISTICO viene impostato implicitamente a 4
	 * (Classificato) per tutta la catena dei correlati solo se il documento
	 * principale si trova in STATO_ARCHIVISTICO 2 (Registrato) o 3
	 * (Protocollato).
	 * <p>
	 * Questo metodo non è invocabile per i documenti in STATO_ARCHIVISTICO 6
	 * (in archivio di deposito).
	 * 
	 * @param documentId
	 *            id del Documento
	 * @param factory
	 *            Collezione dei metadati del profilo da modificare
	 * @return true se l’operazione è andata a buon fine
	 * @throws Exception
	 *             In tutti i casi di errore il metodo solleva una SOAPException
	 *             contenente il messaggio di errore.
	 */
	public boolean classificaDocumento(String documentId, KeyValuePairFactory<MetadatiDocumento> factory)
			throws Exception {
		return classificaDocumentoNative(documentId, factory.get());
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
	private boolean archiviaDocumentoNative(String documentId, KeyValuePair[] metadata) throws Exception {
		DocerServicesStub service = getDocerService();
		ArchiviaDocumento request = new ArchiviaDocumento();
		request.setToken(getLoginTicket());
		request.setDocId(documentId);
		request.setMetadata(metadata);
		ArchiviaDocumentoResponse response = service.archiviaDocumento(request);
		boolean esito = response.get_return();
		return esito;
	}

	/**
	 * Questo metodo permette l’archiviazione (in archivio di deposito) di un
	 * Documento e di tutti i suoi related nel DMS.
	 * <p>
	 * La variabile docId è l’id del Documento nel DMS.
	 * <p>
	 * L’oggetto metadata[] è una collezione di nodi metadata. Ogni nodo
	 * metadata contiene una KeyValuePair ovvero due nodi, key e value, di tipo
	 * string dove i valori ammessi per i nodi key sono i nomi dei metadati del
	 * profilo relativi all’archivio di deposito (si veda paragrafo 4.4. Profilo
	 * di un documento).
	 * <p>
	 * Il metadato STATO_ARCHIVISTICO viene impostato implicitamente a 6
	 * (Archiviato) per tutta la catena dei correlati.
	 * 
	 * @param documentId
	 *            id del Documento
	 * @param metadati
	 *            Collezione dei metadati del profilo da modificare
	 * @return true se l’operazione è andata a buon fine
	 * @throws Exception
	 */
	public boolean archiviaDocumento(String documentId, List<Map<MetadatiDocumento, String>> metadati)
			throws Exception {
		return archiviaDocumentoNative(documentId, KeyValuePairFactory.toArray(metadati));
	}

	/**
	 * Questo metodo permette l’archiviazione (in archivio di deposito) di un
	 * Documento e di tutti i suoi related nel DMS.
	 * <p>
	 * La variabile docId è l’id del Documento nel DMS.
	 * <p>
	 * L’oggetto metadata[] è una collezione di nodi metadata. Ogni nodo
	 * metadata contiene una KeyValuePair ovvero due nodi, key e value, di tipo
	 * string dove i valori ammessi per i nodi key sono i nomi dei metadati del
	 * profilo relativi all’archivio di deposito (si veda paragrafo 4.4. Profilo
	 * di un documento).
	 * <p>
	 * Il metadato STATO_ARCHIVISTICO viene impostato implicitamente a 6
	 * (Archiviato) per tutta la catena dei correlati.
	 * 
	 * @param documentId
	 *            id del Documento
	 * @param factory
	 *            Collezione dei metadati del profilo da modificare
	 * @return true se l’operazione è andata a buon fine
	 * @throws Exception
	 */
	public boolean archiviaDocumento(String documentId, KeyValuePairFactory<MetadatiDocumento> factory)
			throws Exception {
		return archiviaDocumentoNative(documentId, factory.get());
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
	private List<KeyValuePair> chilren(String folderId) throws Exception {
		List<KeyValuePair> res = new ArrayList<>();
		List<String> folderDocuments = getFolderDocuments(folderId);
		for (String documentId : folderDocuments) {
			KeyValuePair[] profiloDocumento = getProfileDocumentNative(documentId);
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
	 * restituisce il numero di documenti con specifico EXTERNAL_ID
	 *
	 * @param externalId
	 * @return
	 */
	public int numberOfDocumentByExternalId(String externalId) throws Exception {
		List<Map<String, String>> documents = searchDocumentsByExternalIdAll(externalId);
		return documents.size();
	}



	// /**
	// * crea un nuovo documento nella cartella
	// *
	// * @param folderId
	// * @param name
	// * @param content
	// * @param title
	// * @param description
	// * @return
	// */
	// public String createDocument(String folderId, String name, byte[]
	// content, String title, String description) {
	// logger.debug("createDocument folderId={} name={} content.length={}",
	// folderId, name, content.length);
	// return createDocument(folderId, name, content, title, description);
	// }

	/**
	 * crea una nuova versione per il documento
	 * 
	 * @param documentId
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public String createVersion(String documentId, byte[] content) throws Exception {
		logger.debug("createVersion documentId={} content.length={}", documentId, content.length);
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
		logger.debug("getDocumentStream {} {}", documentId, versionNumber);
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
		logger.debug("getDocument {} {}", documentId, versionNumber);
		return IOUtils.toByteArray(getDocumentStream(documentId, versionNumber));
	}

	/**
	 * 
	 * @param PARENT_FOLDER_ID
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> getProfileDocumentMapByParentFolder(String PARENT_FOLDER_ID) throws Exception {
		logger.debug("getProfileDocumentMapByParentFolder {}", PARENT_FOLDER_ID);
		List<Map<String, String>> data = new ArrayList<>();
		List<String> documents = getFolderDocuments(PARENT_FOLDER_ID);
		if (documents != null) {
			for (String documentId : documents) {
				Map<String, String> documentProfile = getProfileDocumentMap(documentId);
				// data.put(documentId, documentProfile);
				data.add(documentProfile);
			}
		}
		return data;
	}

	/**
	 * Questo metodo permette la creazione degli utenti nel DMS.
	 * <p>
	 * L’oggetto userInfo[] è una collezione di nodi userInfo. Ogni nodo
	 * userInfo contiene una KeyValuePair ovvero due nodi key e value di tipo
	 * string dove i valori ammessi per i nodi key sono (si veda paragrafo 4.3
	 * Profilo degli Utenti):
	 * <li>USER_ID (id dell’utente)
	 * <li>FULL_NAME (nome completo dell’utente)
	 * <li>COD_ENTE (l’Ente primario di appartenenza)
	 * <li>COD_AOO (la AOO primaria di appartenenza)
	 * <li>USER_PASSWORD (è possibile assegnare un default in fase di creazione
	 * ma non fa parte del profilo)
	 * <li>FIRST_NAME (nome dell’utente)
	 * <li>LAST_NAME (cognome dell’utente)
	 * <li>EMAIL_ADDRESS (indirizzo email dell’utente)
	 * <li>metadati extra
	 * 
	 * @param USER_ID
	 *            id dell’utente
	 * @param password
	 *            password dell’utente
	 * @param nome
	 *            nome dell’utente
	 * @param cognome
	 *            cognome dell’utente
	 * @param fullName
	 *            nome completo dell’utente
	 * @param email
	 *            email dell’utente
	 * @return true se il metodo è andato a buon fine
	 * @throws Exception
	 *             In tutti i casi di errore il metodo solleva una SOAPException
	 *             contenente il messaggio di errore.
	 */
	public boolean createUser(String USER_ID, String password, String nome, String cognome, String fullName,
			String email) throws Exception {
		logger.debug("createUser {} {} {} {} {} {}", USER_ID, password, nome, cognome, fullName, email);
		KeyValuePairFactory<MetadatiUtente> keyBuilder = new KeyValuePairFactory<>();
		keyBuilder.add(MetadatiUtente.USER_ID, USER_ID).add(MetadatiUtente.COD_ENTE, docerCodiceENTE)
				.add(MetadatiUtente.COD_AOO, docerCodiceAOO);
		if (StringUtils.isNotEmpty(fullName))
			keyBuilder.add(MetadatiUtente.FULL_NAME, fullName);

		if (StringUtils.isNotEmpty(password))
			keyBuilder.add(MetadatiUtente.USER_PASSWORD, password);
		if (StringUtils.isNotEmpty(nome))
			keyBuilder.add(MetadatiUtente.FIRST_NAME, nome);
		if (StringUtils.isNotEmpty(cognome))
			keyBuilder.add(MetadatiUtente.LAST_NAME, cognome);
		if (StringUtils.isNotEmpty(email))
			keyBuilder.add(MetadatiUtente.EMAIL_ADDRESS, email);

		KeyValuePair[] userInfo = keyBuilder.get();

		DocerServicesStub service = getDocerService();
		CreateUser request = new CreateUser();
		request.setToken(getLoginTicket());
		request.setUserInfo(userInfo);
		CreateUserResponse response = service.createUser(request);
		boolean esito = response.get_return();
		return esito;
	}

	/**
	 * Questo metodo permette la modifica del profilo di un utente nel DMS.
	 * <p>
	 * Il metodo non permette la modifica della USER_ID degli utenti.
	 * 
	 * @param userId
	 *            id dell'utente da modificare
	 * @param metadati
	 *            lista di metadati Utente da modificare
	 * @return
	 * @throws Exception
	 *             In tutti i casi di errore il metodo solleva una SOAPException
	 *             contenente il messaggio di errore.
	 */
	public boolean updateUser(String userId, Map<MetadatiUtente, String> metadati) throws Exception {
		logger.debug("updateUser {} {}", userId, metadati);
		KeyValuePairFactory<MetadatiUtente> keyBuilder = new KeyValuePairFactory<>();
		keyBuilder.add(MetadatiFolder.USER_ID_KEY, userId).add(MetadatiUtente.COD_ENTE, docerCodiceENTE)
				.add(MetadatiUtente.COD_AOO, docerCodiceAOO);

		if (metadati != null && !metadati.isEmpty()) {
			for (Entry<MetadatiUtente, String> metadato : metadati.entrySet()) {
				keyBuilder.add(metadato.getKey(), metadato.getValue());
			}
		}

		KeyValuePair[] userInfo = keyBuilder.get();

		DocerServicesStub service = getDocerService();
		UpdateUser request = new UpdateUser();
		request.setToken(getLoginTicket());
		request.setUserId(userId);
		request.setUserInfo(userInfo);
		UpdateUserResponse response = service.updateUser(request);
		boolean esito = response.get_return();
		return esito;
	}

	/**
	 * Questo metodo permette di recuperare il profilo di un utente del DMS.
	 * 
	 * @param userId
	 *            id dell’utente di riferimento
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getUser(String userId) throws Exception {
		logger.debug("getUser {}", userId);
		Map<MetadatiUtente, String> res = new HashMap<>();

		DocerServicesStub service = getDocerService();
		GetUser request = new GetUser();
		request.setToken(getLoginTicket());
		request.setUserId(userId);
		GetUserResponse response = service.getUser(request);
		KeyValuePair[] metadati = response.get_return();
		return KeyValuePairFactory.asMap(metadati);
	}

	/**
	 * Questo metodo permette di eseguire delle ricerche sulla collezione degli
	 * utenti del DMS.
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> searchUsers(String userId) throws Exception {
		logger.debug("searchUsers {}", userId);
		KeyValuePairFactory<MetadatiUtente> searchCriteria = new KeyValuePairFactory<>();
		if (StringUtils.isNotEmpty(userId)) {
			KeyValuePairFactory.build(MetadatiUtente.USER_ID_KEY, userId).get();
		}
		DocerServicesStub service = getDocerService();
		SearchUsers request = new SearchUsers();
		request.setToken(getLoginTicket());
		request.setSearchCriteria(searchCriteria.get());
		SearchUsersResponse response = service.searchUsers(request);
		SearchItem[] data = response.get_return();
		return KeyValuePairFactory.asListMap(data);
	}

	/**
	 * Questo metodo permette la creazione dei gruppi nel DMS.
	 * <p>
	 * L’oggetto groupInfo[] è una collezione di nodi groupInfo. Ogni nodo
	 * groupInfo contiene una KeyValuePair ovvero due nodi key e value di tipo
	 * string dove i valori ammessi per i nodi key sono (si veda il paragrafo
	 * 4.2 Profilo dei Gruppi):
	 * <li>GROUP_ID (id del gruppo)
	 * <li>GROUP_NAME (nome del gruppo)
	 * <li>PARENT_GROUP_ID (id del gruppo padre)
	 * <li>metadati extra Tale lista coincide con il profilo minimo richiesto
	 * per la creazione. Per i gruppi di primo livello la chiave PARENT_GROUP_ID
	 * deve indicare l’id del gruppo “Ente” di appartenenza (si veda paragrafo
	 * il 4.1.1 Anagrafica Ente).
	 * 
	 * @param GROUP_ID
	 * @param GROUP_NAME
	 * @param PARENT_GROUP_ID
	 * @return true se il metodo è andato a buon fine
	 * @throws Exception
	 *             In tutti i casi di errore il metodo solleva una SOAPException
	 *             contenente il messaggio di errore.
	 */
	public boolean createGroup(String GROUP_ID, String GROUP_NAME, String PARENT_GROUP_ID) throws Exception {
		logger.debug("createGroup {} {} {}", GROUP_ID, GROUP_NAME, PARENT_GROUP_ID);
		KeyValuePairFactory<MetadatiGruppi> keyBuilder = new KeyValuePairFactory<>();
		keyBuilder.add(MetadatiGruppi.GROUP_ID, GROUP_ID).add(MetadatiGruppi.GROUP_NAME, GROUP_NAME)
				.add(MetadatiGruppi.PARENT_GROUP_ID, PARENT_GROUP_ID);

		KeyValuePair[] userInfo = keyBuilder.get();

		DocerServicesStub service = getDocerService();
		CreateGroup request = new CreateGroup();
		request.setToken(getLoginTicket());
		request.setGroupInfo(userInfo);
		CreateGroupResponse response = service.createGroup(request);
		boolean esito = response.get_return();
		return esito;
	}

	/**
	 * Questo metodo permette la modifica del profilo di un gruppo nel DMS.
	 * 
	 * @param groupId
	 *            id del gruppo da modificare
	 * @param metadati
	 *            Collezione dei metadati da aggiornare nel profilo del gruppo
	 * @return
	 * @throws Exception
	 */
	public boolean updateGroup(String groupId, Map<MetadatiGruppi, String> metadati) throws Exception {
		logger.debug("updateGroup {} {}", groupId, metadati);
		KeyValuePairFactory<MetadatiGruppi> keyBuilder = new KeyValuePairFactory<>();
		if (metadati != null && !metadati.isEmpty()) {
			for (Entry<MetadatiGruppi, String> metadato : metadati.entrySet()) {
				keyBuilder.add(metadato.getKey(), metadato.getValue());
			}
		}

		KeyValuePair[] groupInfo = keyBuilder.get();

		DocerServicesStub service = getDocerService();
		UpdateGroup request = new UpdateGroup();
		request.setToken(getLoginTicket());
		request.setGroupId(groupId);
		request.setGroupInfo(groupInfo);
		UpdateGroupResponse response = service.updateGroup(request);
		boolean esito = response.get_return();
		return esito;
	}

	/**
	 * Questo metodo permette di recuperare il profilo di un gruppo del DMS.
	 * <p>
	 * L’oggetto di ritorno è una collezione di nodi KeyValuePair . Ogni
	 * KeyValuePair contiene una coppia di nodi key e value, entrambi di tipo
	 * string. Per ogni nodo key è specificato il nome di un metadato del
	 * profilo dell’utente recuperato.
	 * <p>
	 * Il profilo del gruppo non contiene l’elenco degli utenti assegnati al
	 * gruppo; per recuperare la lista degli utenti del gruppo si veda il metodo
	 * getUsersOfGroup.
	 * 
	 * @param groupId
	 *            id del gruppo di riferimento
	 * @return Profilo del gruppo di riferimento
	 * @throws Exception
	 *             In tutti i casi di errore il metodo solleva una SOAPException
	 *             contenente il messaggio di errore.
	 */
	public Map<String, String> getGroup(String groupId) throws Exception {
		logger.debug("getUser {}", groupId);
		Map<MetadatiUtente, String> res = new HashMap<>();

		DocerServicesStub service = getDocerService();
		GetGroup request = new GetGroup();
		request.setToken(getLoginTicket());
		request.setGroupId(groupId);
		GetGroupResponse response = service.getGroup(request);
		KeyValuePair[] metadati = response.get_return();
		return KeyValuePairFactory.asMap(metadati);
	}

	/**
	 * Questo metodo permette di assegnare i gruppi ad un utente del DMS.
	 * <p>
	 * L’oggetto groups[] è una collezione di nodi groups. Ogni nodo groups
	 * contiene un dato di tipo string dove i valori ammessi sono i GROUP_ID dei
	 * gruppi del DMS.
	 * 
	 * @param userId
	 *            id dell’utente di riferimento
	 * @param groups
	 *            Collezione degli id dei gruppi da assegnare all’utente
	 * @return true se il metodo è andato a buon fine
	 * @throws Exception
	 *             In tutti i casi di errore il metodo solleva una SOAPException
	 *             contenente il messaggio di errore.
	 */
	public boolean setGroupsOfUser(String userId, List<String> groups) throws Exception {
		logger.debug("setGroupsOfUser {} {}", userId, groups);
		DocerServicesStub service = getDocerService();
		SetGroupsOfUser request = new SetGroupsOfUser();
		request.setToken(getLoginTicket());
		request.setUserId(userId);
		if (groups != null)
			request.setGroups(groups.toArray(new String[groups.size()]));
		SetGroupsOfUserResponse response = service.setGroupsOfUser(request);
		boolean esito = response.get_return();
		return esito;
	}

	/**
	 * Questo metodo permette di modificare la lista dei gruppi di un utente del
	 * DMS.
	 * <p>
	 * Gli oggetti groupsToAdd[] e groupsToRemove[] sono delle sequenze di nodi
	 * groupsToAdd e groupsToRemove rispettivamente. Ogni nodo groupsToAdd o
	 * groupsToRemove contiene un dato di tipo string dove i valori ammessi sono
	 * i GROUP_ID dei gruppi del DMS.
	 * 
	 * @param userId
	 *            id dell’utente di riferimento
	 * @param groupsToAdd
	 *            Collezione degli id dei gruppi da assegnare all’utente
	 * @param groupsToRemove
	 *            Collezione degli id dei gruppi da rimuovere dall’utente
	 * @return true se il metodo è andato a buon fine
	 * @throws Exception
	 *             In tutti i casi di errore il metodo solleva una SOAPException
	 *             contenente il messaggio di errore.
	 */
	public boolean updateGroupsOfUser(String userId, List<String> groupsToAdd, List<String> groupsToRemove)
			throws Exception {
		logger.debug("updateGroupsOfUser {} {} {}", userId, groupsToAdd, groupsToRemove);
		DocerServicesStub service = getDocerService();
		UpdateGroupsOfUser request = new UpdateGroupsOfUser();
		request.setToken(getLoginTicket());
		request.setUserId(userId);
		if (groupsToAdd != null)
			request.setGroupsToAdd(groupsToAdd.toArray(new String[groupsToAdd.size()]));
		if (groupsToRemove != null)
			request.setGroupsToRemove(groupsToRemove.toArray(new String[groupsToRemove.size()]));
		UpdateGroupsOfUserResponse response = service.updateGroupsOfUser(request);
		boolean esito = response.get_return();
		return esito;
	}

	/**
	 * L’oggetto searchCriteria[] è una collezione di nodi searchCriteria. Ogni
	 * nodo searchCriteria contiene una KeyValuePair ovvero due nodi key e value
	 * di tipo string dove i valori ammessi per i nodi key sono (si veda
	 * paragrafo 4.2 Profilo dei Gruppi):
	 * <li>GROUP_ID (id del gruppo)
	 * <li>GROUP_NAME (nome del gruppo)
	 * <li>PARENT_GROUP_ID (l’id del gruppo padre)
	 * <li>metadati extra
	 * <p>
	 * Una stessa chiave può essere ripetuta più volte nella collezione: criteri
	 * con le stesse chiavi sono in OR logico tra loro; per chiavi diverse vale
	 * l’operatore AND logico. La sintassi di ricerca segue le regole esposte
	 * nel paragrafo Regole sintattiche per i metodi di ricerca. L’oggetto di
	 * ritorno SearchItem[] è una collezione di nodi SearchItem. Ogni nodo
	 * SearchItem rappresenta il profilo di un oggetto trovato dalla ricerca e
	 * contiene una collezione di nodi metadata. Ogni nodo metadata contiene una
	 * KeyValuePair ovvero i nodi key e value di tipo string dove i valori
	 * ammessi per i nodi key sono i nomi dei metadati del profilo di un gruppo:
	 * <li>GROUP_ID (id del gruppo)
	 * <li>GROUP_NAME (nome del gruppo)
	 * <li>PARENT_GROUP_ID (l’id del gruppo padre)
	 * <li>metadati extra
	 * <p>
	 * Un oggetto SearchItem non contiene, quindi, la collezione degli utenti
	 * appartenenti al gruppo trovato.
	 * 
	 * @param searchCriteria
	 *            Collezione dei criteri di ricerca
	 * @return Risultato della ricerca
	 * @throws Exception
	 *             In tutti i casi di errore il metodo solleva una SOAPException
	 *             contenente il messaggio di errore.
	 */
	public List<Map<String, String>> searchGroups(KeyValuePairFactory<MetadatiGruppi> searchCriteria) throws Exception {
		logger.debug("searchGroups {}", searchCriteria);
		DocerServicesStub service = getDocerService();
		SearchGroups request = new SearchGroups();
		request.setToken(getLoginTicket());
		request.setSearchCriteria(searchCriteria.get());
		SearchGroupsResponse response = service.searchGroups(request);
		SearchItem[] data = response.get_return();
		return KeyValuePairFactory.asListMap(data);
	}

	/**
	 * Questo metodo permette di recuperare la lista degli id dei gruppi di
	 * appartenenza di un utente del DMS.
	 * <p>
	 * L’oggetto di ritorno string[] è una collezione di nodi di tipo string
	 * contenente la collezione dei GROUP_ID dei gruppi assegnati all’utente.
	 * 
	 * @param userId
	 *            id dell’utente di riferimento
	 * @return collezione dei GROUP_ID dei gruppi assegnati all’utente
	 * @throws Exception
	 *             In tutti i casi di errore il metodo solleva una SOAPException
	 *             contenente il messaggio di errore.
	 */
	public List<String> getGroupsOfUser(String userId) throws Exception {
		logger.debug("getGroupsOfUser {}", userId);
		DocerServicesStub service = getDocerService();
		GetGroupsOfUser request = new GetGroupsOfUser();
		request.setToken(getLoginTicket());
		request.setUserId(userId);
		GetGroupsOfUserResponse response = service.getGroupsOfUser(request);
		String[] data = response.get_return();
		return Arrays.asList(data);
	}

	/**
	 * Questo metodo permette di recuperare la lista degli id degli utenti
	 * appartenenti ad un gruppo del DMS.
	 * <p>
	 * L’oggetto di ritorno string[] è una collezione di nodi di tipo string
	 * contenente la collezione dei USER_ID degli utenti appartenenti al gruppo.
	 * 
	 * @param groupId
	 *            id del gruppo di riferimento
	 * @return collezione delle USER_ID degli utenti appartenenti al gruppo
	 * @throws Exception
	 *             In tutti i casi di errore il metodo solleva una SOAPException
	 *             contenente il messaggio di errore.
	 */
	public List<String> getUsersOfGroup(String groupId) throws Exception {
		logger.debug("getUsersOfGroup {}", groupId);
		DocerServicesStub service = getDocerService();
		GetUsersOfGroup request = new GetUsersOfGroup();
		request.setToken(getLoginTicket());
		request.setGroupId(groupId);
		GetUsersOfGroupResponse response = service.getUsersOfGroup(request);
		String[] data = response.get_return();
		return Arrays.asList(data);
	}
}
