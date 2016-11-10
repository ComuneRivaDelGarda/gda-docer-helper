package it.tn.rivadelgarda.comune.gda.docer;

import it.kdm.docer.webservices.DocerServicesStub.KeyValuePair;

public class KeyValuePairFactory {

	public final static String FOLDER_NAME = "FOLDER_NAME";
	public final static String FOLDER_OWNER = "FOLDER_OWNER";
	public final static String COD_AOO = "COD_AOO";
	public final static String COD_ENTE = "COD_ENTE";
	public final static String DES_FOLDER = "DES_FOLDER";
	public final static String ENABLED = "ENABLED";
	public final static String PARENT_FOLDER_NAME = "PARENT_FOLDER_NAME";
	public final static String FOLDER_ID = "FOLDER_ID";
	public final static String PARENT_FOLDER_ID = "PARENT_FOLDER_ID";
	
	public final static String ASC = "ASC";
	public final static String DESC = "DESC";

	public static KeyValuePair create(String key, String value) {
		KeyValuePair res = new KeyValuePair();
		res.setKey(key);
		res.setValue(value);
		return res;
	}
}
