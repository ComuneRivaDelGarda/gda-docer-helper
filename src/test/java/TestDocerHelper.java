import java.io.File;
import java.util.Date;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import it.tn.rivadelgarda.comune.gda.docer.DocerHelper;
import it.tn.rivadelgarda.comune.gda.docer.keys.DocumentoMetadatiGenericiEnum.TIPO_COMPONENTE;

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
		// // AuthenticationServiceStub service = new
		// // AuthenticationServiceStub(url);
		// // Login login = new Login();
		// // login.setUsername("admin");
		// // login.setPassword("admin");
		// // login.setCodiceEnte("");
		// // login.setApplication("GDA");
		// // LoginResponse response = service.login(login);
		// // Assert.assertNotNull(response);
		// // Assert.assertNotNull(response.get_return());
		// // logger.info(response.get_return());
		// init();
		// token = helper.login();
		// Assert.assertNotNull(token);
		// logger.info(token);
	}

	@Test
	public void test2() throws Exception {
		// test1();
		// String versione = helper.getVersion();
		// Assert.assertNotNull(versione);
		// logger.info(versione);
	}

	@Test
	public void test3() throws Exception {
		String folderName = "test" + new Date().getTime();
		logger.info("createFolder {}", folderName);
		//
		init();
		token = helper.login();
		String folderId = helper.createFolder(folderName, "885221");
		// 885154
		Assert.assertNotNull(folderId);
		logger.info(folderId);
	}

	@Test
	public void test4() throws Exception {
		// init();
		// token = helper.login();
		// SearchItem[] res = helper.searchFolders("test1");
		// Assert.assertNotNull(res);
		// logger.info(res.toString());
	}

	@Test
	public void test5() throws Exception {
		// init();
		// token = helper.login();
		// SearchItem[] res = helper.searchFolders(null);
		// Assert.assertNotNull(res);
		// logger.info("{}", res);
	}

	@Test
	public void test6() throws Exception {
		init();
		token = helper.login();
		String timestamp = String.valueOf(new Date().getTime());
		Object res = helper.createDocument("DOCUMENTO", "test" + timestamp + ".pdf",
				new File("stuff/Integrazione DOCER 1.1.pdf"), TIPO_COMPONENTE.PRINCIPALE, "DESC");
		Assert.assertNotNull(res);
		logger.info("{}", new Gson().toJson(res));
		// documento 885159
	}

	@Test
	public void test7() throws Exception {
		try {
			init();
			token = helper.login();
			// Object res = helper.addToFolderDocuments("885160", "885159");
			Object res = helper.addToFolderDocument("885161", "885206");
			// Object res = helper.addToFolderDocument("885154", "885206");
			Assert.assertNotNull(res);
			logger.info("{}", res);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	@Test
	public void test8() throws Exception {
		// init();
		// token = helper.login();
		// Object res = helper.getFolderDocuments("885160");
		// Assert.assertNotNull(res);
		// logger.info("{}", new Gson().toJson(res));
	}

	@Test
	public void test9() throws Exception {
		// init();
		// token = helper.login();
		// KeyValuePair[] res = helper.getProfileDocument("885159");
		// Assert.assertNotNull(res);
		// logger.info("{}", new Gson().toJson(res));
	}

	@Test
	public void test10() throws Exception {
		// init();
		// token = helper.login();
		// SearchItem[] res = helper.searchFoldersByParent("885161");
		// Assert.assertNotNull(res);
		// logger.info("{}", new Gson().toJson(res));
	}
}
