import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.tn.rivadelgarda.comune.gda.docer.DocerHelper;

public class TestDocerHelper {

	final static Logger logger = LoggerFactory.getLogger(TestDocerHelper.class);

	private String url;
	private String username;
	private String password;

	DocerHelper helper;
	private String token;

	private void init() throws Exception {
		Properties p = new Properties();
		// p.load(new FileReader(new File("config.properties")));
		p.load(getClass().getResourceAsStream("config.properties"));

		// String url =
		// "http://192.168.64.22:8080/docersystem/services/AuthenticationService";
		url = p.getProperty("url");
		username = p.getProperty("username");
		password = p.getProperty("password");

		helper = new DocerHelper(url, username, password);
	}

	@Test
	public void test1() throws Exception {
//		// AuthenticationServiceStub service = new
//		// AuthenticationServiceStub(url);
//		// Login login = new Login();
//		// login.setUsername("admin");
//		// login.setPassword("admin");
//		// login.setCodiceEnte("");
//		// login.setApplication("GDA");
//		// LoginResponse response = service.login(login);
//		// Assert.assertNotNull(response);
//		// Assert.assertNotNull(response.get_return());
//		// logger.info(response.get_return());
//		init();
//		token = helper.login();
//		Assert.assertNotNull(token);
//		logger.info(token);
	}

	@Test
	public void test2() throws Exception {
//		test1();
//		String versione = helper.getVersion();
//		Assert.assertNotNull(versione);
//		logger.info(versione);
	}
	
	@Test
	public void test3() throws Exception {
//		init();
//		token = helper.login();
//		String versione = helper.createFolder("test1");
//		Assert.assertNotNull(versione);
//		logger.info(versione);
	}
	
	@Test
	public void test4() throws Exception {
		init();
		token = helper.login();
		Object res = helper.searchFolders("test1");
		Assert.assertNotNull(res);
		logger.info(res.toString());
	}

}
