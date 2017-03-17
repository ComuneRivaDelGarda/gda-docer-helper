import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		if (helper == null) {
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
	}

	@Test
	public void test10() throws Exception {
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
		// //token = helper.login();
		// Assert.assertNotNull(token);
		// logger.info(token);
	}

	@Test
	public void test22() throws Exception {
		// test1();
		// String versione = helper.getVersion();
		// Assert.assertNotNull(versione);
		// logger.info(versione);
	}

	/**
	 * crea una foder
	 */
	@Test
	public void test30() throws Exception {
		String folderName = "test-root-" + new Date().getTime();
		logger.info("createFolder {}", folderName);
		//
		init();
		// //token = helper.login();
		String folderId = helper.createFolder(folderName);
		Assert.assertNotNull(folderId);
		logger.info(folderId);
	}

	/**
	 * Crea una folder con owner utente loggato
	 */
	@Test
	public void test31() throws Exception {
		String folderName = "test-root-owner-" + new Date().getTime();
		logger.info("createFolderOwner {}", folderName);
		init();
		// //token = helper.login();
		String folderId = helper.createFolderOwner(folderName);
		Assert.assertNotNull(folderId);
		logger.info(folderId);
	}

	/*
	 * crea una foder e crea una subfolder
	 */
	@Test
	public void test32() throws Exception {
		logger.info("test32 {}", "crea una foder e crea una subfolder");
		String folderName = "test-root-" + new Date().getTime();

		init();
		logger.info("createFolder {}", folderName);
		String folderId = helper.createFolder(folderName);

		folderName = "test-sub-" + new Date().getTime();
		logger.info("SUBFOLDER createFolder {}", folderName);
		String subFolderId = helper.createFolder(folderName, folderId);
		Assert.assertNotNull(subFolderId);
		logger.info(subFolderId);
	}
	
	/*
	 * crea una foder e crea una subfolder con owner utente loggato
	 */	
	@Test
	public void test33() throws Exception {
		logger.info("test33 {}", "crea una foder e crea una subfolder con owner utente loggato");
		String folderName = "test-root-" + new Date().getTime();

		init();
		String folderId = helper.createFolder(folderName);

		folderName = "test-sub-owner-" + new Date().getTime();
		logger.info("SUBFOLDER createFolderOwner {}", folderName);
		String subFolderId = helper.createFolderOwner(folderName, folderId);
		Assert.assertNotNull(subFolderId);
		logger.info(subFolderId);
	}	

	@Test
	public void test40() throws Exception {
		String criteria = "test*";
		logger.info("searchFolders {}", criteria);
		init();
		// token = helper.login();
		List<Map<String, String>> res = helper.searchFolders(criteria);
		Assert.assertNotNull(res);
		logger.info(res.toString());
	}

	@Test
	public void test50() throws Exception {
		logger.info("searchFolders");
		init();
		// token = helper.login();
		List<Map<String, String>> res = helper.searchFolders(null);
		Assert.assertNotNull(res);
		logger.info("{}", res);
	}

	@Test
	public void test60() throws Exception {
		// String typeId = "DOCUMENTO";
		String timestamp = String.valueOf(new Date().getTime());
		String fileName = "test" + timestamp + ".pdf";
		String file = "stuff/Integrazione DOCER 1.1.pdf";

		String criteria = "test*";
		// logger.info("createDocument {} {} - {}", typeId, fileName, file);

		init();
		// token = helper.login();
		String res = helper.createDocumentTypeDocumento(fileName, new File(file), TIPO_COMPONENTE.PRINCIPALE,
				"descrizione con spazi", "TEST");
		Assert.assertNotNull(res);
		logger.info("{}", res);
	}
	
	@Test
	public void test61() throws Exception {
		// String typeId = "DOCUMENTO";
//		String timestamp = String.valueOf(new Date().getTime());
//		String fileName = "test" + timestamp + ".pdf";
//		String file = "stuff/Integrazione DOCER 1.1.pdf";

		String criteria = "test*";
		// logger.info("createDocument {} {} - {}", typeId, fileName, file);

		init();
		// token = helper.login();
		List<Map<String, String>> res = helper.searchDocumentsByExternalId("TEST");
		Assert.assertNotNull(res);
		logger.info("{}", res);
	}	

	@Test
	public void test70() throws Exception {
		try {
			init();
			// token = helper.login();
			boolean res = helper.addToFolderDocument("885227", "885231");
			Assert.assertNotNull(res);
			logger.info("{}", res);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	/**
	 * crea una foder, e carica un documento
	 */
	@Test(expected = it.kdm.docer.webservices.DocerServicesDocerExceptionException0.class)
	public void test71() throws Exception {
		String folderName = "test" + new Date().getTime();
		logger.info("test71 {}", folderName);

		init();
		String folderId = helper.createFolder(folderName);
		Assert.assertNotNull(folderId);
		logger.info(folderId);

		String typeId = "DOCUMENTO";
		String timestamp = String.valueOf(new Date().getTime());
		String fileName = "test" + timestamp + ".pdf";
		String file = "stuff/Integrazione DOCER 1.1.pdf";

		String criteria = "test*";
		logger.info("createDocument {} {} - {}", typeId, fileName, file);

		init();
		// token = helper.login();
		String documentId = helper.createDocument(typeId, fileName, new File(file), TIPO_COMPONENTE.PRINCIPALE,
				"descrizione con spazi", "");

		try {
			boolean res = helper.addToFolderDocument(folderId, documentId);
		} catch (it.kdm.docer.webservices.DocerServicesDocerExceptionException0 ex) {
			
			helper.deleteDocument(documentId);
			
			throw ex;
		}
	}

	/**
	 * crea una foder, crea una subfolder e carica un documento
	 */
	@Test
	public void test72() throws Exception {
		String folderName = "test" + new Date().getTime();
		logger.info("test61 {}", folderName);

		init();
		String folderId = helper.createFolder(folderName);
		Assert.assertNotNull(folderId);
		logger.info(folderId);

		folderName = "test" + new Date().getTime();
		logger.info("SUBFOLDER createFolder {}", folderName);
		String subFolderId = helper.createFolder(folderName, folderId);
		Assert.assertNotNull(subFolderId);
		logger.info(subFolderId);

		String typeId = "DOCUMENTO";
		String timestamp = String.valueOf(new Date().getTime());
		String fileName = "test" + timestamp + ".pdf";
		String file = "stuff/Integrazione DOCER 1.1.pdf";

		String criteria = "test*";
		logger.info("createDocument {} {} - {}", typeId, fileName, file);

		init();
		// token = helper.login();
		String documentId = helper.createDocument(typeId, fileName, new File(file), TIPO_COMPONENTE.PRINCIPALE,
				"descrizione con spazi", "");

		boolean res = helper.addToFolderDocument(subFolderId, documentId);
	}

	@Test
	public void test80() throws Exception {
		// init();
		// //token = helper.login();
		// Object res = helper.getFolderDocuments("885160");
		// Assert.assertNotNull(res);
		// logger.info("{}", new Gson().toJson(res));
	}

	@Test
	public void test90() throws Exception {
		// init();
		// //token = helper.login();
		// KeyValuePair[] res = helper.getProfileDocument("885159");
		// Assert.assertNotNull(res);
		// logger.info("{}", new Gson().toJson(res));
	}

	@Test
	public void test100() throws Exception {
		// init();
		// //token = helper.login();
		// SearchItem[] res = helper.searchFoldersByParent("885161");
		// Assert.assertNotNull(res);
		// logger.info("{}", new Gson().toJson(res));
	}
}
