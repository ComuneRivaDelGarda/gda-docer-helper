package it.tn.rivadelgarda.comune.gda.docer.keys;

import java.util.ArrayList;
import java.util.List;

import it.kdm.docer.webservices.DocerServicesStub.KeyValuePair;

public class KeyValuePairFactory {

	public final static String ASC = "ASC";
	public final static String DESC = "DESC";

	public static KeyValuePair createKey(String key, String value) {
		KeyValuePair res = new KeyValuePair();
		res.setKey(key);
		res.setValue(value);
		return res;
	}

	public static KeyValuePair createKeyOrderByAsc(String key) {
		KeyValuePair res = new KeyValuePair();
		res.setKey(key);
		res.setValue(KeyValuePairFactory.ASC);
		return res;
	}

	public static KeyValuePair createKeyOrderByDesc(String key) {
		KeyValuePair res = new KeyValuePair();
		res.setKey(key);
		res.setValue(KeyValuePairFactory.DESC);
		return res;
	}

	private List<KeyValuePair> list = new ArrayList<>();

	public static KeyValuePairFactory build(KeyValuePairEnum key, String value) {
		KeyValuePairFactory res = new KeyValuePairFactory();
		res.add(key, value);
		return res;
	}

	public static KeyValuePairFactory build(String key, String value) {
		KeyValuePairFactory res = new KeyValuePairFactory();
		res.add(key, value);
		return res;
	}
	
	public KeyValuePairFactory add(KeyValuePairEnum key, String value) {
		this.list.add(createKey(key.getKey(), value));
		return this;
	}

	public KeyValuePairFactory add(String key, String value) {
		this.list.add(createKey(key, value));
		return this;
	}
	
	public KeyValuePair[] get() {
		return this.list.toArray(new KeyValuePair[list.size()]);
	}

	public static KeyValuePairFactory createDocumentKeys(String typeId, String docName, String codEnte, String codAoo) {
		return build(DocumentKeyValuePairEnum.TYPE_ID, typeId).add(DocumentKeyValuePairEnum.DOCNAME, docName)
				.add(DocumentKeyValuePairEnum.COD_ENTE, codEnte).add(DocumentKeyValuePairEnum.COD_AOO, codAoo);
	}

}
