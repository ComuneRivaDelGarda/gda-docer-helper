package it.tn.rivadelgarda.comune.gda.docer;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import it.kdm.docer.core.authentication.AuthenticationServiceStub;
import it.kdm.docer.core.authentication.AuthenticationServiceStub.Login;
import it.kdm.docer.core.authentication.AuthenticationServiceStub.LoginResponse;
import it.kdm.docer.webservices.DocerServicesStub;
import it.kdm.docer.webservices.DocerServicesStub.AddToFolderDocuments;
import it.kdm.docer.webservices.DocerServicesStub.AddToFolderDocumentsResponse;
import it.kdm.docer.webservices.DocerServicesStub.CreateDocument;
import it.kdm.docer.webservices.DocerServicesStub.CreateDocumentResponse;
import it.kdm.docer.webservices.DocerServicesStub.CreateFolder;
import it.kdm.docer.webservices.DocerServicesStub.CreateFolderResponse;
import it.kdm.docer.webservices.DocerServicesStub.DeleteDocument;
import it.kdm.docer.webservices.DocerServicesStub.DeleteDocumentResponse;
import it.kdm.docer.webservices.DocerServicesStub.GetACLDocument;
import it.kdm.docer.webservices.DocerServicesStub.GetACLDocumentResponse;
import it.kdm.docer.webservices.DocerServicesStub.KeyValuePair;
import it.kdm.docer.webservices.DocerServicesStub.RemoveFromFolderDocuments;
import it.kdm.docer.webservices.DocerServicesStub.RemoveFromFolderDocumentsResponse;
import it.kdm.docer.webservices.DocerServicesStub.SearchFolders;
import it.kdm.docer.webservices.DocerServicesStub.SearchFoldersResponse;
import it.kdm.docer.webservices.DocerServicesStub.SearchItem;
import it.tn.rivadelgarda.comune.gda.docer.DocumentKeyValuePairEnum.TIPO_COMPONENTE;
import sample.axisversion.VersionStub;
import sample.axisversion.VersionStub.GetVersion;
import sample.axisversion.VersionStub.GetVersionResponse;

public class DocerHelper implements Closeable {

	private final static String AuthenticationService = "docersystem/services/AuthenticationService";
	private final static String DocerServices = "WSDocer/services/DocerServices";
	private final static String WSProtocollazione = "WSProtocollazione/services/WSProtocollazione";
	private final static String WSFascicolazione = "WSFascicolazione/services/WSFascicolazione";
	private final static String VersionService = "docersystem/services/Version";

	private String docerSerivcesUrl;
	private String docerUsername;
	private String docerPassword;

	private final String docerCodiceENTE = "C_H330";
	private final String docerCodiceAOO = "RSERVIZI";
	private final String docerApplication = "GDA";

	private String loginResponse;
	private String tockenSessione;

	/**
	 * 
	 * @param docerSerivcesUrl
	 * @param docerUsername
	 * @param docerPassword
	 */
	public DocerHelper(String docerSerivcesUrl, String docerUsername, String docerPassword) {
		super();
		if (!docerSerivcesUrl.endsWith("/")) {
			docerSerivcesUrl = docerSerivcesUrl + "/";
		}
		this.docerSerivcesUrl = docerSerivcesUrl;
		this.docerUsername = docerUsername;
		this.docerPassword = docerPassword;
	}

	private Map<String, String> parse(String inString) {
		Map<String, String> res = new HashMap<String, String>();
		// res = Splitter.on("\\|").withKeyValueSeparator(":").split(inString);
		if (StringUtils.isNotBlank(inString)) {
			String[] pairs = inString.split("\\|");
			for (int i = 0; i < pairs.length; i++) {
				String pair = pairs[i];
				String[] keyValue = pair.split(":");
				if (keyValue.length == 2)
					res.put(keyValue[0], keyValue[1]);
				else
					res.put(keyValue[0], null);
			}
		}
		return res;
	}

	/**
	 * 
	 * @return
	 * @throws DocerHelperException
	 */
	public String login() throws Exception {
		// try {
		AuthenticationServiceStub service = new AuthenticationServiceStub(docerSerivcesUrl + AuthenticationService);
		Login login = new Login();
		login.setUsername(docerUsername);
		login.setPassword(docerPassword);
		login.setCodiceEnte(docerCodiceENTE);
		login.setApplication(docerApplication);

		LoginResponse response = service.login(login);

		loginResponse = response.get_return();
		tockenSessione = loginResponse; // parse(loginResponse).get("ticketDocerServices");

		return tockenSessione;
		// } catch (Exception ex) {
		// throw new DocerHelperException(ex);
		// }
	}

	public String getVersion() throws Exception {

		VersionStub service = new VersionStub(docerSerivcesUrl + VersionService);
		GetVersion getversion = new GetVersion();
		GetVersionResponse response = service.getVersion(getversion);
		return response.get_return();

	}

	public String getLoginTicket() {
		return tockenSessione;
	}

	private boolean isLoggedIn() {
		return StringUtils.isNotBlank(tockenSessione);
	}

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
		KeyValuePair[] folderinfo = KeyValuePairFactory.create(FolderKeyValuePairEnum.FOLDER_NAME, folderName)
				.add(FolderKeyValuePairEnum.COD_ENTE, docerCodiceENTE)
				.add(FolderKeyValuePairEnum.COD_AOO, docerCodiceAOO).asArray();

		DocerServicesStub service = new DocerServicesStub(docerSerivcesUrl + DocerServices);
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
	public Object searchFolders(String folderName) throws Exception {

		KeyValuePair[] param = new KeyValuePair[3];
		param[0] = KeyValuePairFactory.createKey(KeyValuePairEnum.FOLDER_NAME, "*");
		param[1] = KeyValuePairFactory.createKey(KeyValuePairEnum.COD_ENTE, docerCodiceENTE);
		param[2] = KeyValuePairFactory.createKey(KeyValuePairEnum.COD_AOO, docerCodiceAOO);

		KeyValuePair[] search = new KeyValuePair[1];
		param[0] = KeyValuePairFactory.createKey(KeyValuePairEnum.FOLDER_NAME, KeyValuePairFactory.ASC);

		DocerServicesStub service = new DocerServicesStub(docerSerivcesUrl + DocerServices);
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
	 * @return
	 * @throws Exception
	 */
	public String createDocument(String typeId, String documentName, File file, TIPO_COMPONENTE tipoComponente)
			throws Exception {
		KeyValuePairFactory params = KeyValuePairFactory.createDocumentKeys(typeId, documentName, docerCodiceENTE,
				docerCodiceAOO);

		FileDataSource fileDataSource = new FileDataSource(file);
		DataHandler dataHandler = new DataHandler(fileDataSource);

		params.add(DocumentKeyValuePairEnum.APP_VERSANTE, docerApplication);
		String md5 = DigestUtils.md5Hex(new FileInputStream(file));
		params.add(DocumentKeyValuePairEnum.DOC_HASH, md5);
		params.add(DocumentKeyValuePairEnum.TIPO_COMPONENTE, tipoComponente.getValue());

		DocerServicesStub service = new DocerServicesStub(docerSerivcesUrl + DocerServices);
		CreateDocument request = new CreateDocument();
		request.setToken(getLoginTicket());
		request.setMetadata(params.asArray());
		request.setFile(dataHandler);
		CreateDocumentResponse response = service.createDocument(request);
		String documentId = response.get_return();

		return documentId;
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

		DocerServicesStub service = new DocerServicesStub(docerSerivcesUrl + DocerServices);
		AddToFolderDocuments request = new AddToFolderDocuments();
		request.setToken(getLoginTicket());
		request.setFolderId(folderId);
		request.setDocument(documentsIds.toArray(new String[documentsIds.size()]));
		AddToFolderDocumentsResponse response = service.addToFolderDocuments(request);
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
	public boolean removeFromFolderDocuments(String folderId, List<String> documentsIds) throws Exception {

		DocerServicesStub service = new DocerServicesStub(docerSerivcesUrl + DocerServices);
		RemoveFromFolderDocuments request = new RemoveFromFolderDocuments();
		request.setToken(getLoginTicket());
		request.setFolderId(folderId);
		request.setDocument(documentsIds.toArray(new String[documentsIds.size()]));
		RemoveFromFolderDocumentsResponse response = service.removeFromFolderDocuments(request);
		boolean esito = response.get_return();

		return esito;
	}

	/**
	 * Questo metodo permette di eliminare un Documento del DMS. E’ possibile
	 * eliminare solo i Documenti che siano STATO_ARCHIVISTICO 0 (Generico
	 * Document).
	 * 
	 * @param folderId
	 * @param documentsIds
	 * @return
	 * @throws Exception
	 */
	public boolean deleteDocument(String documentId) throws Exception {

		DocerServicesStub service = new DocerServicesStub(docerSerivcesUrl + DocerServices);
		DeleteDocument request = new DeleteDocument();
		request.setToken(getLoginTicket());
		request.setDocId(documentId);
		DeleteDocumentResponse response = service.deleteDocument(request);
		boolean esito = response.get_return();

		return esito;
	}

	/**
	 * Questo metodo permette di recuperare i diritti di un Documento del DMS.
	 * 
	 * @param documentId
	 * @return
	 * @throws Exception
	 */
	public KeyValuePair[] getACLDocument(String documentId) throws Exception {

		DocerServicesStub service = new DocerServicesStub(docerSerivcesUrl + DocerServices);
		GetACLDocument request = new GetACLDocument();
		request.setToken(getLoginTicket());
		request.setDocId(documentId);
		GetACLDocumentResponse response = service.getACLDocument(request);
		KeyValuePair[] esito = response.get_return();

		return esito;
	}

	/**
	 * Questo metodo permette di assegnare le ACL di un Documento del DMS, nel
	 * caso di documenti con ACL già definite i diritti assegnati sovrascrivono
	 * quelli precedenti.
	 * 
	 * @param documentId
	 * @return
	 * @throws Exception
	 */
	public KeyValuePair[] setACLDocument(String documentId) throws Exception {

		DocerServicesStub service = new DocerServicesStub(docerSerivcesUrl + DocerServices);
		GetACLDocument request = new GetACLDocument();
		request.setToken(getLoginTicket());
		request.setDocId(documentId);
		GetACLDocumentResponse response = service.getACLDocument(request);
		KeyValuePair[] esito = response.get_return();

		return esito;
	}

	@Override
	public void close() throws IOException {
		if (isLoggedIn()) {

		}
	}
}
