package it.tn.rivadelgarda.comune.gda.docer;

import java.io.Closeable;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.apache.axis2.AxisFault;
import org.apache.axis2.description.TwoChannelAxisOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.kdm.docer.core.authentication.AuthenticationServiceExceptionException0;
import it.kdm.docer.core.authentication.AuthenticationServiceStub;
import it.kdm.docer.core.authentication.AuthenticationServiceStub.Login;
import it.kdm.docer.core.authentication.AuthenticationServiceStub.LoginResponse;
import it.kdm.docer.core.authentication.AuthenticationServiceStub.Logout;
import it.kdm.docer.core.authentication.AuthenticationServiceStub.LogoutResponse;
import it.kdm.docer.webservices.DocerServicesDocerExceptionException0;
import it.kdm.docer.webservices.DocerServicesStub;
import it.tn.rivadelgarda.comune.gda.docer.exceptions.DocerHelperException;
import sample.axisversion.VersionExceptionException0;
import sample.axisversion.VersionStub;
import sample.axisversion.VersionStub.GetVersion;
import sample.axisversion.VersionStub.GetVersionResponse;

public abstract class AbstractDocerHelper implements Closeable {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
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
	public String login() throws DocerHelperException {
		try {
			AuthenticationServiceStub service;
			service = new AuthenticationServiceStub(docerSerivcesUrl + AuthenticationService);
			Login login = new Login();
			login.setUsername(docerUsername);
			login.setPassword(docerPassword);
			login.setCodiceEnte(docerCodiceENTE);
			login.setApplication(docerApplication);
			LoginResponse response;
			response = service.login(login);
			loginResponse = response.get_return();
			tokenSessione = loginResponse; // parse(loginResponse).get("ticketDocerServices");
		} catch (AuthenticationServiceExceptionException0 | RemoteException e) {
			manageDocerException(e);
		}			
		return tokenSessione;
	}

	protected void manageDocerException(Exception ex) throws DocerHelperException {
		if (ex instanceof DocerHelperException) {
			DocerHelperException e = (DocerHelperException) ex;
			logger.error("errore docer helper", e);
			throw e;
		} else if (ex instanceof AxisFault) {
			AxisFault e = (AxisFault) ex;
			logger.error("errore axis", e);
			throw new DocerHelperException(e.getMessage(), e);
		} else if (ex instanceof VersionExceptionException0) {
			VersionExceptionException0 e = (VersionExceptionException0) ex;
			logger.error("errore versione docer", e);
			throw new DocerHelperException(e.getFaultMessage().getVersionException().getMessage(), e);
		} else if (ex instanceof AuthenticationServiceExceptionException0) {
			AuthenticationServiceExceptionException0 e = (AuthenticationServiceExceptionException0) ex;
			logger.error("errore autenticazione su docer", e);
			throw new DocerHelperException(e.getFaultMessage().getAuthenticationServiceException().getMessage(), e);
		} else if (ex instanceof DocerServicesDocerExceptionException0) {
			DocerServicesDocerExceptionException0 e = (DocerServicesDocerExceptionException0) ex;
			logger.error("errore metodo docer", e);
			throw new DocerHelperException(e.getFaultMessage().getDocerException().getErrDescription(), e);
		} else {
			logger.error("errore generico", ex);
			throw new DocerHelperException(ex.getMessage(), ex);
		}
	}


	
	/**
	 * Effettua il logout da Docer
	 * @return
	 * @throws Exception
	 */
	public boolean logout() throws DocerHelperException {
		boolean res = false;
		try {
			AuthenticationServiceStub service = new AuthenticationServiceStub(docerSerivcesUrl + AuthenticationService);
			Logout request = new Logout();
			request.setToken(getLoginToken());
			LogoutResponse response = service.logout(request);
			res = response.get_return();
		} catch (RemoteException | AuthenticationServiceExceptionException0 ex) {
			manageDocerException(ex);
		}
		return res;
	}

	public String getVersion() throws DocerHelperException {
		String res = null;
		try {
			VersionStub service = new VersionStub(docerSerivcesUrl + VersionService);
			GetVersion getversion = new GetVersion();
			GetVersionResponse response = service.getVersion(getversion);
			res = response.get_return();
		} catch (RemoteException | VersionExceptionException0 ex) {
			manageDocerException(ex);
		}
		return res;
	}

	/**
	 * ritorna il token di sessione corrente, oppure effettua login se non presente
	 */
	public String getLoginToken() throws DocerHelperException {
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

	protected DocerServicesStub getDocerService() throws DocerHelperException {
		try {
			if (docerService == null) {
				docerService = new DocerServicesStub(docerSerivcesUrl + DocerServices);
			}
		} catch (AxisFault ex) {
			manageDocerException(ex);
		}
		return docerService;
	}

}
