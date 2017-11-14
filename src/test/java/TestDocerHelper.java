import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.kdm.docer.webservices.DocerServicesDocerExceptionException0;
import it.tn.rivadelgarda.comune.gda.docer.DocerHelper;
import it.tn.rivadelgarda.comune.gda.docer.KeyValuePairFactory;
import it.tn.rivadelgarda.comune.gda.docer.MetadatiHelper;
import it.tn.rivadelgarda.comune.gda.docer.keys.MetadatiDocumento;
import it.tn.rivadelgarda.comune.gda.docer.keys.MetadatiDocumento.TIPO_COMPONENTE_VALUES;
import it.tn.rivadelgarda.comune.gda.docer.keys.MetadatiFolder;
import it.tn.rivadelgarda.comune.gda.docer.values.ACL_VALUES;

public class TestDocerHelper {

	private static final String CONFIG_PROPERTIES = "config.properties";

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
			
			InputStream is = this.getClass().getResourceAsStream(CONFIG_PROPERTIES);
			// org.apache.commons.io.FileUtils.toFile(this.getClass().getResource("resourceFile.txt")‌​);

			if (is != null) {
				p.load(is);

				// String url =
				// "http://192.168.64.22:8080/docersystem/services/AuthenticationService";
				url = p.getProperty("url");
				username = p.getProperty("username");
				password = p.getProperty("password");
	
				helper = new DocerHelper(url, username, password);
			} else {
				throw new Exception("impossibile carcare configurazione da " + CONFIG_PROPERTIES);
			}
		}
	}

	@Test
	public void test10Login() throws Exception {
	 // AuthenticationServiceStub service = new
	 // AuthenticationServiceStub(url);
	 // Login login = new Login();
	 // login.setUsername("admin");
	 // login.setPassword("admin");
	 // login.setCodiceEnte("");
	 // login.setApplication("GDA");
	 // LoginResponse response = service.login(login);
	 // Assert.assertNotNull(response);
	 // Assert.assertNotNull(response.get_return());
	 // logger.info(response.get_return());
	 init();
	 token = helper.login();
	 Assert.assertNotNull(token);
	 logger.info(token);
	}

	@Test
	public void test22() throws Exception {
		logger.info("test versione");
		init();
		String versione = helper.getVersion();
		Assert.assertNotNull(versione);
		logger.info(versione);
	}

	/**
	 * crea una foder
	 * NOTA: un utente NON admin non può creare folder di primo livello
	 */
	@Test
	public void test30() throws Exception {
		String folderName = "test-root-" + getTimeStamp();
		logger.info("createFolder {}", folderName);
		init();
		// test utente admin di primo livello
		String folderId = helper.createFolder(folderName);
		// test utente non admin 
		// String folderId = helper.createFolder(folderName, "885221");
		Assert.assertNotNull(folderId);
		logger.info("creata {}", folderId);
	}

	/**
	 * Crea una folder con owner utente loggato
	 * NOTA: un utente NON admin non può creare folder di primo livello
	 */
	@Test
	public void test31() throws Exception {
		String folderName = "test-root-owner-" + getTimeStamp();
		logger.info("createFolderOwner {}", folderName);
		init();
		// test utente admin di primo livello
		String folderId = helper.createFolderOwner(folderName);
		// test utente non admin 
		// String folderId = helper.createFolderOwner(folderName, "885221");
		Assert.assertNotNull(folderId);
		logger.info("creata {}", folderId);
	}

	/**
	 * crea una folder e crea una subfolder
	 */
	@Test
	public void test32() throws Exception {
		logger.info("test32 {}", "crea una foder e crea una subfolder");
		String folderName = "test-root-" + getTimeStamp();
		init();
		logger.info("createFolder {}", folderName);
		String folderId = helper.createFolder(folderName);
		Assert.assertNotNull(folderId);
		logger.info("creata {}", folderId);
		folderName = "test-sub-" + getTimeStamp();
		logger.info("SUBFOLDER createFolder {}", folderName);
		String subFolderId = helper.createFolder(folderName, folderId);
		Assert.assertNotNull(subFolderId);
		logger.info(subFolderId);
	}
	
	/*
	 * crea una folder e crea una subfolder con owner utente loggato
	 */	
	@Test
	public void test33() throws Exception {
		logger.info("test33 {}", "crea una foder e crea una subfolder con owner utente loggato");
		String folderName = "test-root-" + getTimeStamp();
		init();
		String folderId = helper.createFolder(folderName);
		folderName = "test-sub-owner-" + getTimeStamp();
		logger.info("SUBFOLDER createFolderOwner {}", folderName);
		String subFolderId = helper.createFolderOwner(folderName, folderId);
		Assert.assertNotNull(subFolderId);
		logger.info(subFolderId);
	}	

	/**
	 * cerca tutte le cartelle che corrispondo al criterio test*
	 * @throws Exception
	 */
	@Test
	public void test40() throws Exception {
		String criteria = "test*";
		logger.info("searchFolders {}", criteria);
		init();
		List<Map<String, String>> res = helper.searchFolders(criteria);
		Assert.assertNotNull(res);
		logger.info("searchFolders {}", res.size());
		logger.info(res.toString());
		int count = 0;
		for (Map<String, String> folder : res) {
			count++;
			logger.info("{}) {}", count, KeyValuePairFactory.getMetadata(folder, MetadatiFolder.FOLDER_NAME));
		}
	}

	/**
	 * cerca tutte le cartelle 
	 * @throws Exception
	 */
	@Test
	public void test50() throws Exception {
		logger.info("searchFolders");
		init();
		List<Map<String, String>> res = helper.searchFolders(null);
		Assert.assertNotNull(res);
		logger.info("searchFolders {}", res.size());
		logger.info("{}", res);
		int count = 0;
		for (Map<String, String> folder : res) {
			count++;
			logger.info("{}) {}", count, KeyValuePairFactory.getMetadata(folder, MetadatiFolder.FOLDER_NAME));
		}
	}

	@Test
	public void test60() throws Exception {
		// String typeId = "DOCUMENTO";
		String timestamp = getTimeStamp();
		String fileName = "test" + timestamp + ".pdf";
		String file = "stuff/Integrazione DOCER 1.1.pdf";

		init();
		String res = helper.createDocumentTypeDocumento(fileName, new File(file), TIPO_COMPONENTE_VALUES.PRINCIPALE,
				"descrizione " + timestamp, "TEST-" + timestamp);
		Assert.assertNotNull(res);
		logger.info("{}", res);		
	}
	
//	@Test
//	public void test61() throws Exception {
//		// String typeId = "DOCUMENTO";
////		String timestamp = String.valueOf(new Date().getTime());
////		String fileName = "test" + timestamp + ".pdf";
////		String file = "stuff/Integrazione DOCER 1.1.pdf";
//
//		String criteria = "test*";
//		// logger.info("createDocument {} {} - {}", typeId, fileName, file);
//
//		init();
//		// token = helper.login();
//		List<Map<String, String>> res = helper.searchDocumentsByExternalIdAll("TEST");
//		Assert.assertNotNull(res);
//		logger.info("{}", res);
//	}	

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
	 * crea una foder, carica un documento ed elimina
	 */
	// @Test(expected = it.kdm.docer.webservices.DocerServicesDocerExceptionException0.class)
	@Test
	public void test71() throws Exception {

		init();
		
		String folderName = "test-carica-e-cancella-" + getTimeStamp();
		logger.info("test71 {}", folderName);
		String folderId = helper.createFolder(folderName, "885221");
		
//		 String folderName = "test-personale-carica-e-cancella-" + getTimeStamp();
//		 logger.info("test71 {}", folderName);
//		 String folderId = helper.createFolderOwner(folderName, "885221");
		 
		Assert.assertNotNull(folderId);
		logger.info(folderId);

		String typeId = "DOCUMENTO";
		String externalId = "test-" + getTimeStamp();
		String fileName = externalId + ".pdf";
		String file = "stuff/Integrazione DOCER 1.1.pdf";

		String criteria = "test*";
		logger.info("createDocument {} {} - {}", typeId, fileName, file);

		init();
		// token = helper.login();
		String documentId = helper.createDocument(typeId, fileName, new File(file), TIPO_COMPONENTE_VALUES.PRINCIPALE,
				"file su folder " + folderName, externalId);
		logger.info("creato {}", documentId);
				
		try {
			boolean res = helper.addToFolderDocument(folderId, documentId);
			logger.info("aggiunto {} a folder {}", documentId, folderId);
			
			// SET ACL to USER
			String userID = username;
			boolean acl = helper.setACLDocument(documentId, userID, ACL_VALUES.FULL_ACCESS);
			logger.info("acl {} con {} = {}", userID, ACL_VALUES.FULL_ACCESS, acl);
			
			helper.deleteDocument(documentId);
			logger.info("documento cancellato {}", documentId);
//		} catch (it.kdm.docer.webservices.DocerServicesDocerExceptionException0 ex) {
//			logger.error("test {}", ex.getFaultMessage().getDocerException().getErrDescription());
//			logger.error("test {}", ex.getFaultMessage().getDocerException().getErrNumber());
//			// helper.deleteDocument(documentId);
//			throw ex;
		} catch (Exception ex) {
			logger.error("test {}", ex.getMessage());
			throw ex;
		}
	}

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

	@Test
	public void test90() throws Exception {
		try  {
			String docNum = "885401";
			logger.info("test90 - getProfileDocumentMap {}", docNum);
			init();
			Map<String, String> res = helper.getProfileDocumentMap(docNum);
			Assert.assertNotNull(res);
			logger.info("{}", new Gson().toJson(res));
		} catch (Exception ex) {
			logger.error("test90", ex);
		}
	}

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
				 
				 List<Map<String, String>> res2 = helper.searchDocumentsByExternalIdAll("protocollo_808737");
				 String[] docnums2 = KeyValuePairFactory.joinMetadata(res2, MetadatiDocumento.DOCNUM);
				 
//			 }
//		 }
//		 

		 Assert.assertNotNull(docnums2);
		 logger.info("{}", new GsonBuilder().setPrettyPrinting().create().toJson(docnums2));
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
	
//	@Test
//	public void test210() throws Exception {
//		logger.info("test200 searchUsers {}");
//		init();
//				 
//		List<Map<String, String>> res = helper.searchUsers(null);
//				 
//		Assert.assertNotNull(res);
//		logger.info("{}", new GsonBuilder().setPrettyPrinting().create().toJson(res));
//	}
//	
//	@Test
//	public void test211() throws Exception {
//		logger.info("test200 searchUsers {}");
//		init();
//				 
//		List<Map<String, String>> res = helper.searchUsers("pivamichela3");
//				 
//		Assert.assertNotNull(res);
//		logger.info("{}", new GsonBuilder().setPrettyPrinting().create().toJson(res));
//	}	
	
//	@Test
//	public void test300() throws Exception {
//		logger.info("test300 searchGroups {}");
//		init();
//		try {
//			KeyValuePairFactory criteri = new KeyValuePairFactory<MetadatiGruppi>().add(MetadatiGruppi.GROUP_NAME, "*");
//			List<Map<String, String>> res = helper.searchGroups(criteri);
//			Assert.assertNotNull(res);
//			logger.info("{}", new GsonBuilder().setPrettyPrinting().create().toJson(res));
//		} catch (Exception ex) {
//			logger.error("test200", ex.getMessage());
//		}
//	}
	
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

//	@Test
//	public void test410() throws Exception {
//		logger.info("test410 getGroupsOfUser {}");
//		init();
//		try {
////			List<String> res = helper.getGroupsOfUser("pivamichela");
//			List<String> res = helper.getGroupsOfUser("lattisitiziano");
//			Assert.assertNotNull(res);
//			logger.info("{}", new GsonBuilder().setPrettyPrinting().create().toJson(res));
//		} catch (Exception ex) {
//			logger.error("test200", ex.getMessage());
//		}
//	}
//	
//	@Test
//	public void test420() throws Exception {
//		logger.info("test420 getUsersOfGroup {}");
//		init();
//		try {
//			List<String> res = helper.getUsersOfGroup("10021");
//			Assert.assertNotNull(res);
//			logger.info("{}", new GsonBuilder().setPrettyPrinting().create().toJson(res));
//		} catch (Exception ex) {
//			logger.error("test200", ex.getMessage());
//		}
//	}
//	
//	@Test
//	public void test500() throws Exception {
//		logger.info("test500 setACLDocumentConvert {}");
//		init();
//		try {
//			boolean res = helper.setACLDocument("885265", ACLFactory.create("lattisitiziano", ACL_VALUES.FULL_ACCESS).add("pivamichela", ACL_VALUES.READ_ONLY_ACCESS));
//			Assert.assertNotNull(res);
//			logger.info("{}", new GsonBuilder().setPrettyPrinting().create().toJson(res));
//		} catch (Exception ex) {
//			logger.error("test200", ex.getMessage());
//		}
//	}

	@Test
	public void test501() throws Exception {
		logger.info("test501 getACLDocument {}");
		init();
		try {
			Map<String, String> res = helper.getACLDocumentMap("886442");
			Assert.assertNotNull(res);
			logger.info("{}", new GsonBuilder().setPrettyPrinting().create().toJson(res));
		} catch (Exception ex) {
			logger.error("test200", ex.getMessage());
		}
	}

	
	/**
	 * ricerca un file per externalId
	 */
	@Test
	public void test600() throws Exception {
		String externalId = "protocollo_808775";
		logger.info("test600 searchDocumentsByExternalIdFirst {}", externalId);
		init();
		try {
			Map<String, String> res = helper.searchDocumentsByExternalIdFirst(externalId);
			Assert.assertNotNull(res);
			logger.info("{}", new GsonBuilder().setPrettyPrinting().create().toJson(res));
		} catch (Exception ex) {
			logger.error("test600", ex.getMessage());
		}
	}
	
	/**
	 * ricerca un file per externalId e riduce la mappa di metadati trovata
	 * @throws Exception
	 */
	@Test
	public void test601() throws Exception {
		String externalId = "protocollo_808737"; // "protocollo_808737"; // protocollo_808775
		logger.info("test601 searchDocumentsByExternalIdAll and reduce {}", externalId);
		init();
		try {
			Collection<Map<String, String>> res = helper.searchDocumentsByExternalIdAll(externalId, true);
			logger.info("pre-reduce {}", new GsonBuilder().setPrettyPrinting().create().toJson(res));
			res = MetadatiHelper.mapReduce(res, MetadatiDocumento.DOCNUM, MetadatiDocumento.DOCNAME, MetadatiDocumento.EXTERNAL_ID, MetadatiDocumento.CREATION_DATE);
			Assert.assertNotNull(res);
			logger.info("post-reduce {}", new GsonBuilder().setPrettyPrinting().create().toJson(res));
		} catch (Exception ex) {
			logger.error("test601", ex.getMessage());
		}
	}
	
	/*
	 * ricerca per valori multipli di metadato
	 */
	@Test
	public void test602() throws Exception {
		// String externalId = "test-171103*"; // protocollo_808775
		logger.info("test602 searchDocuments and reduce {}");
		init();
		try {
			Collection<Map<String, String>> res = helper.searchDocuments(MetadatiDocumento.DOCNUM, true, "886466", "886468");
			logger.info("pre-reduce {}", new GsonBuilder().setPrettyPrinting().create().toJson(res));
			res = MetadatiHelper.mapReduce(res, MetadatiDocumento.DOCNUM, MetadatiDocumento.DOCNAME, MetadatiDocumento.EXTERNAL_ID, MetadatiDocumento.CREATION_DATE);
			Assert.assertNotNull(res);
			logger.info("post-reduce {}", new GsonBuilder().setPrettyPrinting().create().toJson(res));
		} catch (Exception ex) {
			logger.error("test602", ex);
		}
	}
	
	@Test
	public void test604() throws Exception {
		logger.info("test604 searchDocuments and reduce {}");
		init();
		try {
			Collection<Map<String, String>> res = helper.searchDocuments(MetadatiDocumento.EXTERNAL_ID, false, "protocollo_808737", "protocollo_808738");
			logger.info("pre-reduce {}", new GsonBuilder().setPrettyPrinting().create().toJson(res));
			res = MetadatiHelper.mapReduce(res, MetadatiDocumento.DOCNUM, MetadatiDocumento.DOCNAME, MetadatiDocumento.EXTERNAL_ID, MetadatiDocumento.CREATION_DATE);
			Assert.assertNotNull(res);
			logger.info("post-reduce {}", new GsonBuilder().setPrettyPrinting().create().toJson(res));
		} catch (Exception ex) {
			logger.error("test604", ex);
		}
	}
	
	@Test
	public void test603() throws Exception {
		String externalId = "test-171103*"; // protocollo_808775
		logger.info("test603 searchDocumentsByExternalIdAll and reduce {}", externalId);
		init();
		try {
			Collection<Map<String, String>> res = helper.searchDocumentsByExternalIdAll(externalId, true);
			logger.info("pre-reduce {}", new GsonBuilder().setPrettyPrinting().create().toJson(res));
			res = MetadatiHelper.mapReduce(res, MetadatiDocumento.DOCNUM, MetadatiDocumento.DOCNAME, MetadatiDocumento.EXTERNAL_ID, MetadatiDocumento.CREATION_DATE);
			Assert.assertNotNull(res);
			logger.info("post-reduce {}", new GsonBuilder().setPrettyPrinting().create().toJson(res));
		} catch (Exception ex) {
			logger.error("test603", ex.getMessage());
		}
	}
	
	private String getTimeStamp() {
		return new SimpleDateFormat("yyMMddHHmmss").format(new Date());
	}
	
	/**
	 * ricerca per externalId con prefisso min e max uguali
	 */
	@Test
	public void test700() throws Exception {
		String externalIdMin = "test-171103190437";
		String externalIdMax = "test-171103190437";
//		Date data = new SimpleDateFormat("dd/MM/yyyy").parse("03/11/2017");
		Date data = null;
		logger.info("test700 searchDocumentsByExternalIdRangeAndDate {} {} {}", externalIdMin, externalIdMax, data);
		init();
		try {
			Collection<Map<String, String>> res = helper.searchDocumentsByExternalIdRangeAndDate(externalIdMin, externalIdMax, "test-", data, true);
			res = MetadatiHelper.mapReduce(res, MetadatiDocumento.DOCNUM, MetadatiDocumento.DOCNAME, MetadatiDocumento.DOC_HASH);
			Assert.assertNotNull(res);
			logger.info("{}", new GsonBuilder().setPrettyPrinting().create().toJson(res));
		} catch (Exception ex) {
			logger.error("test700", ex);
		}
	}
	
	/**
	 * ricerca per externalId con prefisso min e max diversi
	 */
	@Test
	public void test701() throws Exception {
		String externalIdMin = "test-171103190244";
		String externalIdMax = "test-171103190437";
//		Date data = new SimpleDateFormat("dd/MM/yyyy").parse("03/11/2017");
		Date data = null;
		logger.info("test701 searchDocumentsByExternalIdRangeAndDate {} {} {}", externalIdMin, externalIdMax, data);
		init();
		try {
			Collection<Map<String, String>> res = helper.searchDocumentsByExternalIdRangeAndDate(externalIdMin, externalIdMax, "test-171103190", data, true);
			res = MetadatiHelper.mapReduce(res, MetadatiDocumento.DOCNUM, MetadatiDocumento.DOCNAME, MetadatiDocumento.DOC_HASH);
			Assert.assertNotNull(res);
			logger.info("{}", new GsonBuilder().setPrettyPrinting().create().toJson(res));
		} catch (Exception ex) {
			logger.error("test701", ex);
		}
	}
	
	/**
	 * ricerca un file per externalId
	 */
	@Test
	public void test710() throws Exception {
		String externalIdMin = "protocollo_808737";
		String externalIdMax = "protocollo_808737";
		Date data = new SimpleDateFormat("dd/MM/yyyy").parse("22/06/2017");
		logger.info("test710 searchDocumentsByExternalIdRangeAndDate {} {} {}", externalIdMin, data);
		init();
		try {
			Collection<Map<String, String>> res = helper.searchDocumentsByExternalIdRangeAndDate(externalIdMin, externalIdMax, "protocollo_", data, true);
			res = MetadatiHelper.mapReduce(res, MetadatiDocumento.DOCNUM, MetadatiDocumento.DOCNAME, MetadatiDocumento.DOC_HASH, MetadatiDocumento.EXTERNAL_ID, MetadatiDocumento.CREATION_DATE);
			Assert.assertNotNull(res);
			logger.info("{}", new GsonBuilder().setPrettyPrinting().create().toJson(res));
		} catch (Exception ex) {
			logger.error("test710", ex);
		}
	}
	
	@Test
	public void test711() throws Exception {
		String externalIdMin = "protocollo_808737";
		String externalIdMax = "protocollo_808737";
		Date data = new SimpleDateFormat("dd/MM/yyyy").parse("07/06/2017");
		logger.info("test711 searchDocumentsByExternalIdRangeAndDate {} {} {}", externalIdMin, data);
		init();
		try {
			Collection<Map<String, String>> res = helper.searchDocumentsByExternalIdRangeAndDate(externalIdMin, externalIdMax, "protocollo_", data, true);
			res = MetadatiHelper.mapReduce(res, MetadatiDocumento.DOCNUM, MetadatiDocumento.DOCNAME, MetadatiDocumento.DOC_HASH, MetadatiDocumento.EXTERNAL_ID, MetadatiDocumento.CREATION_DATE);
			Assert.assertNotNull(res);
			logger.info("{}", new GsonBuilder().setPrettyPrinting().create().toJson(res));
		} catch (Exception ex) {
			logger.error("test711", ex);
		}
	}	
	
	
	@Test
	public void test712() throws Exception {
		String externalIdMin = "protocollo_808737";
		Date data = new SimpleDateFormat("dd/MM/yyyy").parse("22/06/2017");
		logger.info("test712 searchDocumentsByExternalIdRangeAndDate {} {} {}", externalIdMin, data);
		init();
		try {
			Collection<Map<String, String>> res = helper.searchDocumentsByExternalIdsAndDate(data, externalIdMin);
			res = MetadatiHelper.mapReduce(res, MetadatiDocumento.DOCNUM, MetadatiDocumento.DOCNAME, MetadatiDocumento.DOC_HASH);
			Assert.assertNotNull(res);
			logger.info("{}", new GsonBuilder().setPrettyPrinting().create().toJson(res));
		} catch (Exception ex) {
			logger.error("test712", ex);
		}
	}
	
	@Test
	public void test720() throws Exception {
		String externalIdMin = "protocollo_808735";
		Date data = new SimpleDateFormat("dd/MM/yyyy").parse("07/06/2017");
		logger.info("test701 searchDocumentsByExternalIdRangeAndDate {} {} {}", externalIdMin, data);
		init();
		try {
			Collection<Map<String, String>> res = helper.searchDocumentsByDateAndExternalIdLimit(externalIdMin, "protocollo_", data, false);
			res = MetadatiHelper.mapReduce(res, MetadatiDocumento.EXTERNAL_ID);
			Assert.assertNotNull(res);
			logger.info("{}", new GsonBuilder().setPrettyPrinting().create().toJson(res));
		} catch (Exception ex) {
			logger.error("test701", ex);
		}
	}
}
