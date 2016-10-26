package it.tn.rivadelgarda.comune.gda.docer;

import it.kdm.docer.core.authentication.AuthenticationServiceStub;
import it.kdm.docer.core.authentication.AuthenticationServiceStub.Login;
import it.kdm.docer.core.authentication.AuthenticationServiceStub.LoginResponse;

public class DocerHelper {

	private String docerServer;
	private String docerUsername;
	private String docerPassword;

	private String docerCodiceEnte = "";
	private String docerApplication = "GDA";

	private String loginTocken;

	public DocerHelper(String docerServer, String docerUsername, String docerPassword) {
		super();
		this.docerServer = docerServer;
		this.docerUsername = docerUsername;
		this.docerPassword = docerPassword;
	}

	public void login() throws DocerHelperException {
		try {
			AuthenticationServiceStub service = new AuthenticationServiceStub(docerServer);
			Login login = new Login();
			login.setUsername(docerUsername);
			login.setPassword(docerPassword);
			login.setCodiceEnte(docerCodiceEnte);
			login.setApplication(docerApplication);

			LoginResponse response = service.login(login);

			loginTocken = response.get_return();
		} catch (Exception ex) {
			throw new DocerHelperException(ex);
		}
	}

	public String getLoginTocken() {
		return loginTocken;
	}
}
