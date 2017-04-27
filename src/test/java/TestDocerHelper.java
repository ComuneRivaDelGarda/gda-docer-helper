import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.tn.rivadelgarda.comune.gda.docer.DocerHelper;
import it.tn.rivadelgarda.comune.gda.docer.KeyValuePairFactory;
import it.tn.rivadelgarda.comune.gda.docer.keys.MetadatiDocumento;
import it.tn.rivadelgarda.comune.gda.docer.keys.MetadatiDocumento.TIPO_COMPONENTE_VALUES;
import it.tn.rivadelgarda.comune.gda.docer.keys.MetadatiGruppi;

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

//	/**
//	 * crea una foder
//	 */
//	@Test
//	public void test30() throws Exception {
//		String folderName = "test-root-" + new Date().getTime();
//		logger.info("createFolder {}", folderName);
//		//
//		init();
//		// //token = helper.login();
//		String folderId = helper.createFolder(folderName);
//		Assert.assertNotNull(folderId);
//		logger.info(folderId);
//	}

//	/**
//	 * Crea una folder con owner utente loggato
//	 */
//	@Test
//	public void test31() throws Exception {
//		String folderName = "test-root-owner-" + new Date().getTime();
//		logger.info("createFolderOwner {}", folderName);
//		init();
//		// //token = helper.login();
//		String folderId = helper.createFolderOwner(folderName);
//		Assert.assertNotNull(folderId);
//		logger.info(folderId);
//	}

	/*
	 * crea una foder e crea una subfolder
	 */
//	@Test
//	public void test32() throws Exception {
//		logger.info("test32 {}", "crea una foder e crea una subfolder");
//		String folderName = "test-root-" + new Date().getTime();
//
//		init();
//		logger.info("createFolder {}", folderName);
//		String folderId = helper.createFolder(folderName);
//
//		folderName = "test-sub-" + new Date().getTime();
//		logger.info("SUBFOLDER createFolder {}", folderName);
//		String subFolderId = helper.createFolder(folderName, folderId);
//		Assert.assertNotNull(subFolderId);
//		logger.info(subFolderId);
//	}
	
	/*
	 * crea una foder e crea una subfolder con owner utente loggato
	 */	
//	@Test
//	public void test33() throws Exception {
//		logger.info("test33 {}", "crea una foder e crea una subfolder con owner utente loggato");
//		String folderName = "test-root-" + new Date().getTime();
//
//		init();
//		String folderId = helper.createFolder(folderName);
//
//		folderName = "test-sub-owner-" + new Date().getTime();
//		logger.info("SUBFOLDER createFolderOwner {}", folderName);
//		String subFolderId = helper.createFolderOwner(folderName, folderId);
//		Assert.assertNotNull(subFolderId);
//		logger.info(subFolderId);
//	}	

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

//	@Test
//	public void test60() throws Exception {
//		// String typeId = "DOCUMENTO";
//		String timestamp = String.valueOf(new Date().getTime());
//		String fileName = "test" + timestamp + ".pdf";
//		String file = "stuff/Integrazione DOCER 1.1.pdf";
//
//		String criteria = "test*";
//		// logger.info("createDocument {} {} - {}", typeId, fileName, file);
//
//		init();
//		// token = helper.login();
//		String res = helper.createDocumentTypeDocumento(fileName, new File(file), TIPO_COMPONENTE_VALUES.PRINCIPALE,
//				"descrizione con spazi", "TEST");
//		Assert.assertNotNull(res);
//		logger.info("{}", res);
//	}
	
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
		List<Map<String, String>> res = helper.searchDocumentsByExternalIdAll("TEST");
		Assert.assertNotNull(res);
		logger.info("{}", res);
	}	

//	@Test
//	public void test70() throws Exception {
//		try {
//			init();
//			// token = helper.login();
//			boolean res = helper.addToFolderDocument("885227", "885231");
//			Assert.assertNotNull(res);
//			logger.info("{}", res);
//		} catch (Exception ex) {
//			logger.error(ex.getMessage(), ex);
//		}
//	}

	/**
	 * crea una foder, e carica un documento
	 */
//	@Test(expected = it.kdm.docer.webservices.DocerServicesDocerExceptionException0.class)
//	public void test71() throws Exception {
//		String folderName = "test" + new Date().getTime();
//		logger.info("test71 {}", folderName);
//
//		init();
//		String folderId = helper.createFolder(folderName);
//		Assert.assertNotNull(folderId);
//		logger.info(folderId);
//
//		String typeId = "DOCUMENTO";
//		String timestamp = String.valueOf(new Date().getTime());
//		String fileName = "test" + timestamp + ".pdf";
//		String file = "stuff/Integrazione DOCER 1.1.pdf";
//
//		String criteria = "test*";
//		logger.info("createDocument {} {} - {}", typeId, fileName, file);
//
//		init();
//		// token = helper.login();
//		String documentId = helper.createDocument(typeId, fileName, new File(file), TIPO_COMPONENTE_VALUES.PRINCIPALE,
//				"descrizione con spazi", "");
//
//		try {
//			boolean res = helper.addToFolderDocument(folderId, documentId);
//		} catch (it.kdm.docer.webservices.DocerServicesDocerExceptionException0 ex) {
//			
//			helper.deleteDocument(documentId);
//			
//			throw ex;
//		}
//	}

	/**
	 * crea una foder, crea una subfolder e carica un documento
	 */
//	@Test
//	public void test72() throws Exception {
//		String folderName = "test" + new Date().getTime();
//		logger.info("test61 {}", folderName);
//
//		init();
//		String folderId = helper.createFolder(folderName);
//		Assert.assertNotNull(folderId);
//		logger.info(folderId);
//
//		folderName = "test" + new Date().getTime();
//		logger.info("SUBFOLDER createFolder {}", folderName);
//		String subFolderId = helper.createFolder(folderName, folderId);
//		Assert.assertNotNull(subFolderId);
//		logger.info(subFolderId);
//
//		String typeId = "DOCUMENTO";
//		String timestamp = String.valueOf(new Date().getTime());
//		String fileName = "test" + timestamp + ".pdf";
//		String file = "stuff/Integrazione DOCER 1.1.pdf";
//
//		String criteria = "test*";
//		logger.info("createDocument {} {} - {}", typeId, fileName, file);
//
//		init();
//		// token = helper.login();
//		String documentId = helper.createDocument(typeId, fileName, new File(file), TIPO_COMPONENTE_VALUES.PRINCIPALE,
//				"descrizione con spazi", "");
//
//		boolean res = helper.addToFolderDocument(subFolderId, documentId);
//	}

//	@Test
//	public void test80() throws Exception {
		// init();
		// //token = helper.login();
		// Object res = helper.getFolderDocuments("885160");
		// Assert.assertNotNull(res);
		// logger.info("{}", new Gson().toJson(res));
//	}

//	@Test
//	public void test90() throws Exception {
		// init();
		// //token = helper.login();
		// KeyValuePair[] res = helper.getProfileDocument("885159");
		// Assert.assertNotNull(res);
		// logger.info("{}", new Gson().toJson(res));
//	}

//	@Test
//	public void test100() throws Exception {
		// init();
		// //token = helper.login();
		// SearchItem[] res = helper.searchFoldersByParent("885161");
		// Assert.assertNotNull(res);
		// logger.info("{}", new Gson().toJson(res));
//	}
	
	@Test
	public void test110() throws Exception {
		 init();
		 //token = helper.login();
		 boolean changed = false;
		 
//		 List<Map<String, String>> res = helper.searchDocumentsByExternalIdAll("TEST");
//		 for (Map<String, String> metadata : res) {
//			 String[] docnums = KeyValuePairFactory.joinMetadata(res, MetadatiDocumento.DOCNUM);
//			 for (String docnum : docnums) {
//				 changed = helper.updateProfileDocumentExternalId(docnum, "TEST2");
				 
				 List<Map<String, String>> res2 = helper.searchDocumentsByExternalIdAll("TEST2");
				 String[] docnums2 = KeyValuePairFactory.joinMetadata(res2, MetadatiDocumento.DOCNUM);
				 
//			 }
//		 }
//		 

		 Assert.assertNotNull(docnums2);
		 logger.info("{}", new Gson().toJson(docnums2));
	}
	
//	@Test
//	public void test200() throws Exception {
//		logger.info("test200 createUser {}");
//		init();
//		try {
//			// boolean res = helper.createUser("pivamichela", "pivamichela", "pivamichela", "pivamichela", "pivamichela", "michela@piva.it");
//			boolean res = helper.createUser("lattisitiziano", "lattisitiziano", "lattisitiziano", "lattisitiziano", "lattisitiziano", "lattisi@tiziano.it");
//			Assert.assertNotNull(res);
//			logger.info("{}", new Gson().toJson(res));
//		} catch (Exception ex) {
//			logger.error("test200", ex.getMessage());
//		}
//	}	
	
	@Test
	public void test210() throws Exception {
		logger.info("test200 searchUsers {}");
		init();
				 
		List<Map<String, String>> res = helper.searchUsers(null);
				 
		Assert.assertNotNull(res);
		logger.info("{}", new GsonBuilder().setPrettyPrinting().create().toJson(res));
	}
	
	@Test
	public void test300() throws Exception {
		logger.info("test300 searchGroups {}");
		init();
		try {
			KeyValuePairFactory criteri = new KeyValuePairFactory<MetadatiGruppi>().add(MetadatiGruppi.GROUP_NAME, "*");
			List<Map<String, String>> res = helper.searchGroups(criteri);
			Assert.assertNotNull(res);
			logger.info("{}", new GsonBuilder().setPrettyPrinting().create().toJson(res));
		} catch (Exception ex) {
			logger.error("test200", ex.getMessage());
		}
	}
	
//	@Test
//	public void test310() throws Exception {
//		logger.info("test310 createGroup {}");
//		init();
//		try {
//			// boolean res = helper.createGroup("10021", "SISTEMA INFORMATIVO COMUNALE", "C_H330");
//			// boolean res = helper.createGroup("20021", "SISTEMA INFORMATIVO COMUNALE - Riservato", "C_H330");
////			boolean res = helper.createGroup("10025", "SINDACO", "C_H330");
//			// boolean res = helper.createGroup("20025", "SINDACO - Riservato", "C_H330");
////			boolean res = helper.createGroup("10171", "RESPONSABILE U.O. SISTEMA INFORMATIVO COMUNALE", "C_H330");
//			boolean res = helper.createGroup("20171", "RESPONSABILE U.O. SISTEMA INFORMATIVO COMUNALE - Riservato", "C_H330");
//			Assert.assertNotNull(res);
//			logger.info("{}", new Gson().toJson(res));
//		} catch (Exception ex) {
//			logger.error("test200", ex.getMessage());
//		}
//	}
	
//	@Test
//	public void test400() throws Exception {
//		logger.info("test400 updateGroupsOfUser {}");
//		init();
//		try {
////			List<String> gruppi = Arrays.asList("10021", "20021", "10171", "20171");
////			Boolean res = helper.updateGroupsOfUser("pivamichela", gruppi, null);
//			List<String> gruppi = Arrays.asList("10021");
//			Boolean res = helper.updateGroupsOfUser("lattisitiziano", gruppi, null);			
//			Assert.assertNotNull(res);
//			logger.info("{}", new GsonBuilder().setPrettyPrinting().create().toJson(res));
//		} catch (Exception ex) {
//			logger.error("test200", ex.getMessage());
//		}
//	}	

	@Test
	public void test410() throws Exception {
		logger.info("test410 getGroupsOfUser {}");
		init();
		try {
//			List<String> res = helper.getGroupsOfUser("pivamichela");
			List<String> res = helper.getGroupsOfUser("lattisitiziano");
			Assert.assertNotNull(res);
			logger.info("{}", new GsonBuilder().setPrettyPrinting().create().toJson(res));
		} catch (Exception ex) {
			logger.error("test200", ex.getMessage());
		}
	}
	
	@Test
	public void test420() throws Exception {
		logger.info("test420 getUsersOfGroup {}");
		init();
		try {
			List<String> res = helper.getUsersOfGroup("10021");
			Assert.assertNotNull(res);
			logger.info("{}", new GsonBuilder().setPrettyPrinting().create().toJson(res));
		} catch (Exception ex) {
			logger.error("test200", ex.getMessage());
		}
	}
}
