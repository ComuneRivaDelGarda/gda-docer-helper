package it.tn.rivadelgarda.comune.gda.docer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.kdm.docer.webservices.DocerServicesStub.KeyValuePair;
import it.kdm.docer.webservices.DocerServicesStub.SearchItem;
import it.tn.rivadelgarda.comune.gda.docer.keys.DocerKey;
import it.tn.rivadelgarda.comune.gda.docer.keys.DocerValue;
import it.tn.rivadelgarda.comune.gda.docer.keys.DocumentoMetadatiGenericiEnum;

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

	public static KeyValuePairFactory build(DocerKey key, String value) {
		KeyValuePairFactory res = new KeyValuePairFactory();
		res.add(key, value);
		return res;
	}

	public static KeyValuePairFactory build(String key, String value) {
		KeyValuePairFactory res = new KeyValuePairFactory();
		res.add(key, value);
		return res;
	}

	public KeyValuePairFactory add(DocerKey key, DocerValue value) {
		this.list.add(createKey(key.getValue(), value.getValue()));
		return this;
	}

	public KeyValuePairFactory add(DocerKey key, String value) {
		this.list.add(createKey(key.getValue(), value));
		return this;
	}

	public KeyValuePairFactory add(String key, String value) {
		this.list.add(createKey(key, value));
		return this;
	}

	public KeyValuePair[] get() {
		return this.list.toArray(new KeyValuePair[list.size()]);
	}

	/**
	 * 
	 * @param TYPE_ID
	 * @param DOCNAME
	 * @param COD_ENTE
	 * @param COD_AOO
	 * @return
	 */
	public static KeyValuePairFactory createDocumentKeys(String TYPE_ID, String DOCNAME, String COD_ENTE,
			String COD_AOO) {
		return build(DocumentoMetadatiGenericiEnum.TYPE_ID, TYPE_ID).add(DocumentoMetadatiGenericiEnum.DOCNAME, DOCNAME)
				.add(DocumentoMetadatiGenericiEnum.COD_ENTE, COD_ENTE)
				.add(DocumentoMetadatiGenericiEnum.COD_AOO, COD_AOO);
	}

	/**
	 * Converte in una mappa di String un array di KeyValuePair
	 * 
	 * @param data
	 *            KeyValuePair[]
	 * @return
	 */
	public static Map<String, String> asMap(KeyValuePair[] data) {
		Map<String, String> res = new HashMap<>();
		if (data != null)
			for (KeyValuePair kv : data) {
				res.put(kv.getKey(), kv.getValue());
			}
		return res;
	}

	/**
	 * Converte in una lista di mappe un array di SearchItem
	 * 
	 * @param data
	 *            SearchItem[]
	 * @return
	 */
	public static List<Map<String, String>> asListMap(SearchItem[] data) {
		List<Map<String, String>> result = new ArrayList<>();
		if (data != null) {
			for (SearchItem searchItem : data) {
				Map<String, String> res = new HashMap<>();
				KeyValuePair[] profilo = searchItem.getMetadata();
				for (KeyValuePair kv : profilo) {
					res.put(kv.getKey(), kv.getValue());
				}
				result.add(res);
			}
		}
		return result;
	}

	/**
	 * 
	 * @param metadataList
	 * @param key
	 * @return
	 */
	public static String searchMetadata(List<Map<String, String>> metadataList, DocerKey key) {
		String metadataValue = null;
		for (Map<String, String> metadata : metadataList) {
			metadataValue = searchMetadata(metadataList, key);
			if (metadataValue != null)
				break;
		}
		return metadataValue;
	}
	
	public static String searchMetadata(Map<String, String> metadata, DocerKey key) {
		String metadataValue = null;
		if (metadata.containsKey(key.getValue())) {
			metadataValue = metadata.get(key.getValue());
		}
		return metadataValue;
	}
}
