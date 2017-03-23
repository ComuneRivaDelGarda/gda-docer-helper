package it.tn.rivadelgarda.comune.gda.docer;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import it.kdm.docer.webservices.DocerServicesStub.GetRelatedDocuments;
import it.kdm.docer.webservices.DocerServicesStub.GetRelatedDocumentsResponse;
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
import it.kdm.docer.webservices.DocerServicesStub.SearchItem;
import it.kdm.docer.webservices.DocerServicesStub.SetACLDocument;
import it.kdm.docer.webservices.DocerServicesStub.SetACLDocumentResponse;
import it.kdm.docer.webservices.DocerServicesStub.StreamDescriptor;
import it.kdm.docer.webservices.DocerServicesStub.UpdateProfileDocument;
import it.kdm.docer.webservices.DocerServicesStub.UpdateProfileDocumentResponse;
import it.tn.rivadelgarda.comune.gda.docer.keys.MetadatiFolder;
import it.tn.rivadelgarda.comune.gda.docer.keys.MetadatiDocumento;
import it.tn.rivadelgarda.comune.gda.docer.keys.MetadatiDocumento.ARCHIVE_TYPE;
import it.tn.rivadelgarda.comune.gda.docer.keys.MetadatiDocumento.TIPO_COMPONENTE;
import it.tn.rivadelgarda.comune.gda.docer.keys.MetadatoDocer;
import it.tn.rivadelgarda.comune.gda.docer.values.ACLValuesEnum;

public class DocerHelper extends AbstractDocerHelper {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	/**
	 * Crea l'istanza di helper (chiamata a {@link #login()} effettuata
	 * automaticamente alla chiamata di uno dei metodi)
	 * 
	 * <pre>
	 * {
	 * 	&#64;code
	 * 	DocerHelper helper = new DocerHelper(url, username, password);
	 * }
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
	 * @param file
	 * @param TIPO_COMPONENTE
	 *            uno dei TIPO_COMPONENTE validi
	 * @return
	 * @throws Exception
	 */
	public String createDocument(String TYPE_ID, String DOCNAME, DataSource dataSource, TIPO_COMPONENTE TIPO_COMPONENTE,
			String ABSTRACT, String EXTERNAL_ID) throws Exception {
		KeyValuePairFactory<MetadatiDocumento> params = KeyValuePairFactory.createDocumentKeys(TYPE_ID, DOCNAME,
				docerCodiceENTE, docerCodiceAOO);

		DataHandler dataHandler = new DataHandler(dataSource);

		params.add(MetadatiDocumento.APP_VERSANTE_KEY, docerApplication);
		String md5 = DigestUtils.md5Hex(dataSource.getInputStream());
		params.add(MetadatiDocumento.DOC_HASH_KEY, md5);
		params.add(MetadatiDocumento.TIPO_COMPONENTE_KEY, TIPO_COMPONENTE.getValue());
		if (StringUtils.isNotBlank(ABSTRACT))
			params.add(MetadatiDocumento.ABSTRACT_KEY, ABSTRACT);
		params.add(MetadatiDocumento.ARCHIVE_TYPE_KEY, ARCHIVE_TYPE.ARCHIVE_TYPE_ARCHIVE);
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
	 *            nuovo valore del metadato EXTERNAL_ID {@link MetadatiDocumento}
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
	public String createDocument(String TYPE_ID, String DOCNAME, File file, TIPO_COMPONENTE TIPO_COMPONENTE,
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
	public String createDocument(String TYPE_ID, String DOCNAME, byte[] bytes, TIPO_COMPONENTE TIPO_COMPONENTE,
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
	public String createDocumentTypeDocumento(String DOCNAME, File file, TIPO_COMPONENTE TIPO_COMPONENTE,
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
	public String createDocumentTypeDocumento(String DOCNAME, byte[] bytes, TIPO_COMPONENTE TIPO_COMPONENTE,
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
			TIPO_COMPONENTE TIPO_COMPONENTE, String ABSTRACT, String EXTERNAL_ID) throws Exception {
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
	 * Questo metodo permette di recuperare la lista dei Documenti correlati a
	 * uno specifico externalId
	 * 
	 * @param externalId
	 *            valore del metadato EXTERNAL_ID
	 * @return
	 * @throws Exception
	 */
	private SearchItem[] searchDocumentsNative(KeyValuePair[] searchCriteria, KeyValuePair[] orderBy) throws Exception {
		logger.debug("searchDocumentNative {} {}", searchCriteria, orderBy);
		// cerco tutti
		SearchItem[] result = searchDocumentsNative(searchCriteria, orderBy, -1);
		return result;
	}

	private SearchItem[] searchDocumentsNative(KeyValuePair[] searchCriteria, KeyValuePair[] orderBy, int maxRows)
			throws Exception {
		logger.debug("searchDocumentNative {} {} {}", searchCriteria, orderBy, maxRows);
		DocerServicesStub service = getDocerService();
		SearchDocuments request = new SearchDocuments();
		request.setToken(getLoginTicket());
		request.setSearchCriteria(searchCriteria);
		request.setMaxRows(maxRows);
		request.setOrderby(orderBy);
		SearchDocumentsResponse response = service.searchDocuments(request);
		SearchItem[] result = response.get_return();
		return result;
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
		SearchItem[] result = searchDocumentsNative(searchCriteria, orderBy, -1);
		return KeyValuePairFactory.asListMap(result);
	}

	public Map<String, String> searchDocumentsByExternalIdFirst(String externalId) throws Exception {
		Map<String, String> profile = new HashMap<>();
		logger.debug("searchDocumentsByExternalIdFirst {}", externalId);
		KeyValuePair[] searchCriteria = KeyValuePairFactory.build(MetadatiDocumento.EXTERNAL_ID, externalId).get();
		KeyValuePair[] orderBy = KeyValuePairFactory.build(MetadatiDocumento.CREATION_DATE, MetadatoDocer.SORT_ASC)
				.get();
		SearchItem[] result = searchDocumentsNative(searchCriteria, orderBy, 1);
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
	 *            Livelli di Accesso ai documenti e anagrafiche): • 2 se si
	 *            vuole assegnare “Read Only Access” • 1 se si vuole assegnare
	 *            “Normal Access” • 0 se si vuole assegnare “Full Access”
	 * @return
	 * @throws Exception
	 */
	public boolean setACLDocument(String documentId, KeyValuePair[] acls) throws Exception {
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
}
