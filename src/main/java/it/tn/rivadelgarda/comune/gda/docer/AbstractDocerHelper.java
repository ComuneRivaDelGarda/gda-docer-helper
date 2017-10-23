package it.tn.rivadelgarda.comune.gda.docer;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import it.kdm.docer.core.authentication.AuthenticationServiceStub;
import it.kdm.docer.core.authentication.AuthenticationServiceStub.Login;
import it.kdm.docer.core.authentication.AuthenticationServiceStub.LoginResponse;
import it.kdm.docer.core.authentication.AuthenticationServiceStub.Logout;
import it.kdm.docer.core.authentication.AuthenticationServiceStub.LogoutResponse;
import it.kdm.docer.webservices.DocerServicesStub;
import sample.axisversion.VersionStub;
import sample.axisversion.VersionStub.GetVersion;
import sample.axisversion.VersionStub.GetVersionResponse;

public abstract class AbstractDocerHelper implements Closeable {

	/**
	 * Indirizzo predefinito WS di Autenticazione
	 */
	protected final static String AuthenticationService = "docersystem/services/AuthenticationService";
	/**
	 * Indirizzo predefinito WS DocerServices
	 */
	protected final static String DocerServices = "WSDocer/services/DocerServices";
	/**
	 * Indirizzo predefinito WS Protocollazione
	 */
	protected final static String WSProtocollazione = "WSProtocollazione/services/WSProtocollazione";
	/**
	 * Indirizzo predefinito WS Fascicolazione
	 */
	protected final static String WSFascicolazione = "WSFascicolazione/services/WSFascicolazione";
	/**
	 * Indirizzo predefinito WS Version
	 */
	protected final static String VersionService = "docersystem/services/Version";

	protected String docerSerivcesUrl;
	protected String docerUsername;
	protected String docerPassword;

	/**
	 * Codice Ente predefinito
	 */
	protected final String docerCodiceENTE = "C_H330";
	/**
	 * Codice AOO predefinito
	 */
	protected final String docerCodiceAOO = "RSERVIZI";
	/**
	 * Nome Applicazione predefinito
	 */
	protected final String docerApplication = "GDA";

	private String loginResponse;
	private String tokenSessione;

	DocerServicesStub docerService;

	@Override
	public void close() throws IOException {
		if (isLoggedIn()) {
			try {
				logout();
			} catch (Exception ex) {
				throw new IOException("Impossibile eseguire Logout", ex);
			}
		}
	}

	/**
	 * 
	 * @param docerSerivcesUrl
	 * @param docerUsername
	 * @param docerPassword
	 */
	public AbstractDocerHelper(String docerSerivcesUrl, String docerUsername, String docerPassword) {
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
	 * Effettua il login a Docer per ottenere il token di sessione (da utilizzare nelle chiamate ai web services)
	 * @return token di sessione
	 * @throws Exception
	 */
	public String login() throws Exception {
		AuthenticationServiceStub service = new AuthenticationServiceStub(docerSerivcesUrl + AuthenticationService);
		Login login = new Login();
		login.setUsername(docerUsername);
		login.setPassword(docerPassword);
		login.setCodiceEnte(docerCodiceENTE);
		login.setApplication(docerApplication);
		LoginResponse response = service.login(login);
		loginResponse = response.get_return();
		tokenSessione = loginResponse; // parse(loginResponse).get("ticketDocerServices");
		return tokenSessione;
	}

	/**
	 * Effettua il logout da Docer
	 * @return
	 * @throws Exception
	 */
	public boolean logout() throws Exception {
		AuthenticationServiceStub service = new AuthenticationServiceStub(docerSerivcesUrl + AuthenticationService);
		Logout request = new Logout();
		request.setToken(getLoginToken());
		LogoutResponse response = service.logout(request);
		boolean res = response.get_return();
		return res;
	}

	public String getVersion() throws Exception {
		VersionStub service = new VersionStub(docerSerivcesUrl + VersionService);
		GetVersion getversion = new GetVersion();
		GetVersionResponse response = service.getVersion(getversion);
		return response.get_return();

	}

	/**
	 * ritorna il token di sessione corrente, oppure effettua login se non presente
	 */
	public String getLoginToken() throws Exception {
		if (!isLoggedIn()) {
			login();
		}
		return tokenSessione;
	}

//	@Deprecated
//	public String getLoginTicket() throws Exception {
//		return getLoginToken();
//	}
	
	private boolean isLoggedIn() {
		return StringUtils.isNotBlank(tokenSessione);
	}

	protected DocerServicesStub getDocerService() throws Exception {
		if (docerService == null)
			docerService = new DocerServicesStub(docerSerivcesUrl + DocerServices);
		return docerService;
	}

}
