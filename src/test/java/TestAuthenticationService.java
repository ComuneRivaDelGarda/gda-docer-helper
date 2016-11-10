import it.kdm.docer.core.authentication.AuthenticationServiceStub;
import it.kdm.docer.core.authentication.AuthenticationServiceStub.Login;
import it.kdm.docer.core.authentication.AuthenticationServiceStub.LoginResponse;
import it.tn.rivadelgarda.comune.gda.docer.DocerHelper;
import sample.axisversion.VersionStub;
import sample.axisversion.VersionStub.GetVersion;
import sample.axisversion.VersionStub.GetVersionResponse;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestAuthenticationService {

	final static Logger logger = LoggerFactory.getLogger(TestAuthenticationService.class);

	@Test
	public void test1() throws Exception {
		
//		Properties p = new Properties();
//		p.load(new FileReader(new File("config.properties")));
		
		// String url = "http://192.168.64.22:8080/docersystem/services/AuthenticationService";
		String url = "http://192.168.64.22:8080/docersystem/services/";
		
//		AuthenticationServiceStub service = new AuthenticationServiceStub(url);
//		Login login = new Login();
//		login.setUsername("admin");
//		login.setPassword("admin");
//		login.setCodiceEnte("");
//		login.setApplication("GDA");
//		LoginResponse response = service.login(login);
//		Assert.assertNotNull(response);
//		Assert.assertNotNull(response.get_return());
//		logger.info(response.get_return());
		
		DocerHelper h = new DocerHelper(url, "admin", "admin");
		String token = h.login();
		
		Assert.assertNotNull(token);
		logger.info(token);
	}

	@Test
	public void test2() throws Exception {
		String url = "http://192.168.64.22:8080/docersystem/services/";
		DocerHelper h = new DocerHelper(url, "admin", "admin");
		String versione = h.version();
		Assert.assertNotNull(versione);
		logger.info(versione);
	}
	
}
