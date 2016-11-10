package it.tn.rivadelgarda.comune.gda.docer;

import it.kdm.docer.core.authentication.AuthenticationServiceStub;
import it.kdm.docer.core.authentication.AuthenticationServiceStub.Login;
import it.kdm.docer.core.authentication.AuthenticationServiceStub.LoginResponse;
import sample.axisversion.VersionStub;
import sample.axisversion.VersionStub.GetVersion;
import sample.axisversion.VersionStub.GetVersionResponse;

public class DocerHelper {

	private final static String AuthenticationService = "AuthenticationService";
	private final static String DocerServices = "DocerServices";
	private final static String WSProtocollazione = "WSProtocollazione";
	private final static String WSFascicolazione = "WSFascicolazione";
	private final static String VersionService = "Version";

	private String docerSerivcesUrl;
	private String docerUsername;
	private String docerPassword;

	private String docerCodiceEnte = "";
	private String docerApplication = "GDA";

	private String loginToken;

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

	/**
	 * 
	 * @return
	 * @throws DocerHelperException
	 */
	public String login() throws DocerHelperException {
		try {
			AuthenticationServiceStub service = new AuthenticationServiceStub(docerSerivcesUrl + AuthenticationService);
			Login login = new Login();
			login.setUsername(docerUsername);
			login.setPassword(docerPassword);
			login.setCodiceEnte(docerCodiceEnte);
			login.setApplication(docerApplication);

			LoginResponse response = service.login(login);

			loginToken = response.get_return();
			return loginToken;
		} catch (Exception ex) {
			throw new DocerHelperException(ex);
		}
	}

	public String version() throws DocerHelperException {
		try {
			VersionStub service = new VersionStub(docerSerivcesUrl + VersionService);
			GetVersion getversion = new GetVersion();
			GetVersionResponse response = service.getVersion(getversion);
			return response.get_return();
		} catch (Exception ex) {
			throw new DocerHelperException(ex);
		}
	}

	public String getLoginToken() {
		return loginToken;
	}
}
