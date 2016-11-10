package it.tn.rivadelgarda.comune.gda.docer;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Splitter;

import it.kdm.docer.core.authentication.AuthenticationServiceStub;
import it.kdm.docer.core.authentication.AuthenticationServiceStub.Login;
import it.kdm.docer.core.authentication.AuthenticationServiceStub.LoginResponse;
import it.kdm.docer.webservices.DocerServicesStub;
import it.kdm.docer.webservices.DocerServicesStub.CreateFolder;
import it.kdm.docer.webservices.DocerServicesStub.CreateFolderResponse;
import it.kdm.docer.webservices.DocerServicesStub.KeyValuePair;
import it.kdm.docer.webservices.DocerServicesStub.SearchFolders;
import it.kdm.docer.webservices.DocerServicesStub.SearchFoldersResponse;
import it.kdm.docer.webservices.DocerServicesStub.SearchItem;
import sample.axisversion.VersionStub;
import sample.axisversion.VersionStub.GetVersion;
import sample.axisversion.VersionStub.GetVersionResponse;

public class DocerHelper {

	private final static String AuthenticationService = "docersystem/services/AuthenticationService";
	private final static String DocerServices = "WSDocer/services/DocerServices";
	private final static String WSProtocollazione = "WSProtocollazione/services/WSProtocollazione";
	private final static String WSFascicolazione = "WSFascicolazione/services/WSFascicolazione";
	private final static String VersionService = "docersystem/services/Version";

	private String docerSerivcesUrl;
	private String docerUsername;
	private String docerPassword;

	private String docerCodiceENTE = "C_H330";
	private String docerCodiceAOO = "RSERVIZI";
	private String docerApplication = "GDA";

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

	/**
	 * 
	 * @param folderName
	 * @return
	 * @throws DocerHelperException
	 */
	public String createFolder(String folderName) throws Exception {

		KeyValuePair[] folderinfo = new KeyValuePair[3];
		folderinfo[0] = KeyValuePairFactory.create(KeyValuePairFactory.FOLDER_NAME, folderName);
		folderinfo[1] = KeyValuePairFactory.create(KeyValuePairFactory.COD_ENTE, docerCodiceENTE);
		folderinfo[2] = KeyValuePairFactory.create(KeyValuePairFactory.COD_AOO, docerCodiceAOO);

		DocerServicesStub service = new DocerServicesStub(docerSerivcesUrl + DocerServices);
		CreateFolder createFolderRequest = new CreateFolder();
		createFolderRequest.setToken(getLoginTicket());
		createFolderRequest.setFolderInfo(folderinfo);
		CreateFolderResponse createFolderResponse = service.createFolder(createFolderRequest);
		String folderId = createFolderResponse.get_return();
		return folderId;

	}
	
	public Object searchFolders(String folderName) throws Exception {

		KeyValuePair[] param = new KeyValuePair[3];
		param[0] = KeyValuePairFactory.create(KeyValuePairFactory.FOLDER_NAME, "*");
		param[1] = KeyValuePairFactory.create(KeyValuePairFactory.COD_ENTE, docerCodiceENTE);
		param[2] = KeyValuePairFactory.create(KeyValuePairFactory.COD_AOO, docerCodiceAOO);
		
		KeyValuePair[] search = new KeyValuePair[1];
		param[0] = KeyValuePairFactory.create(KeyValuePairFactory.FOLDER_NAME, KeyValuePairFactory.ASC);
		
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
	
}
