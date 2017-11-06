package it.tn.rivadelgarda.comune.gda.docer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import it.kdm.docer.webservices.DocerServicesStub.KeyValuePair;
import it.kdm.docer.webservices.DocerServicesStub.SearchItem;
import it.tn.rivadelgarda.comune.gda.docer.keys.MetadatiDocumento;
import it.tn.rivadelgarda.comune.gda.docer.keys.MetadatoDocer;

/**
 * Classe per costruzione delle chiavi-valori per docer e metodi di utilità per
 * elaborazione in lettura o creazione delle liste di metadati
 * 
 * @author mirco
 *
 * @param <T>
 *            MetadatoDocer
 */
public class MetadatiHelper<T extends MetadatoDocer> {

	public static KeyValuePair createKey(String key, String value) {
		KeyValuePair res = new KeyValuePair();
		res.setKey(key);
		res.setValue(value);
		return res;
	}

	public static KeyValuePair createKey(String key, String min, String max) {
		KeyValuePair res = new KeyValuePair();
		res.setKey(key);
		if (StringUtils.isNotBlank(min) && StringUtils.isNotBlank(max)) {
			res.setValue("[" + min + " TO " + max + "]");
//			res.setValue("" + min + " TO " + max + "");
		} else if (StringUtils.isNotBlank(min)) {
			res.setValue("[" + min + " TO *]");
		} else {
			res.setValue("[* TO " + max + "]");
		}
		return res;
	}
	
	public static KeyValuePair createKey(String key, Date value) {
		KeyValuePair res = new KeyValuePair();
		res.setKey(key);
		res.setValue(new SimpleDateFormat("yyyy-MM-dd").format(value));
		return res;
	}
	
	public static KeyValuePair createKeyOrderByAsc(String key) {
		KeyValuePair res = new KeyValuePair();
		res.setKey(key);
		res.setValue(MetadatoDocer.SORT_ASC);
		return res;
	}

	public static KeyValuePair createKeyOrderByDesc(String key) {
		KeyValuePair res = new KeyValuePair();
		res.setKey(key);
		res.setValue(MetadatoDocer.SORT_DESC);
		return res;
	}

	private List<KeyValuePair> list = new ArrayList<>();

	/**
	 * crea un oggetto {@link MetadatiHelper} con un metadato
	 * 
	 * @param key
	 *            consulta {@link MetadatiDocumento} per vedere metadati dei
	 *            documenti conosciuti
	 * @param value
	 *            valore da assegnare al metadato
	 * @return
	 */
	public static <F extends MetadatoDocer> MetadatiHelper<F> build(F key, String value) {
		MetadatiHelper<F> res = new MetadatiHelper<F>();
		res.add(key, value);
		return res;
	}
	
	/**
	 * 
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public static <F extends MetadatoDocer> MetadatiHelper<F> build(F key, String min, String max) {
		MetadatiHelper<F> res = new MetadatiHelper<F>();
		res.add(key, min, max);
		return res;
	}
	
	public static <F extends MetadatoDocer> MetadatiHelper<F> build(F key, Date value) {
		MetadatiHelper<F> res = new MetadatiHelper<F>();
		res.add(key, value);
		return res;
	}
	
	public static <F extends MetadatoDocer> MetadatiHelper<F> build(String key, String value) {
		MetadatiHelper<F> res = new MetadatiHelper<F>();
		res.add(key, value);
		return res;
	}
	
	/**
	 * aggiunge un metadato alla catena {@link MetadatiHelper} corrente
	 * 
	 * @param key
	 *            consulta {@link MetadatiDocumento} per vedere metadati dei
	 *            documenti conosciuti
	 * @param value
	 *            valore da assegnare al metadato
	 * @return
	 */
	public MetadatiHelper add(T key, T value) {
		this.list.add(createKey(key.getValue(), value.getValue()));
		return this;
	}

	public MetadatiHelper add(String key, T value) {
		this.list.add(createKey(key, value.getValue()));
		return this;
	}

	/**
	 * aggiunge un metadato alla catena {@link MetadatiHelper} corrente
	 * 
	 * @param key
	 *            consulta {@link MetadatiDocumento} per vedere metadati dei
	 *            documenti conosciuti
	 * @param value
	 *            valore da assegnare al metadato
	 * @return
	 */
	public MetadatiHelper add(T key, String value) {
		this.list.add(createKey(key.getValue(), value));
		return this;
	}
	
	public MetadatiHelper add(T key, String min, String max) {
		this.list.add(createKey(key.getValue(), min, max));
		return this;
	}
	
	public MetadatiHelper add(T key, Date value) {
		this.list.add(createKey(key.getValue(), value));
		return this;
	}

	public MetadatiHelper add(String key, String value) {
		this.list.add(createKey(key, value));
		return this;
	}

	public KeyValuePair[] get() {
		return this.list.toArray(new KeyValuePair[list.size()]);
	}

	public Map<String, String> getAsMap() {
		return asMap(this.get());
	}
	
	/**
	 * 
	 * @param TYPE_ID
	 * @param DOCNAME
	 * @param COD_ENTE
	 * @param COD_AOO
	 * @return
	 */
	public static MetadatiHelper createDocumentKeys(String TYPE_ID, String DOCNAME, String COD_ENTE,
			String COD_AOO) {
		return build(MetadatiDocumento.TYPE_ID, TYPE_ID).add(MetadatiDocumento.DOCNAME, DOCNAME)
				.add(MetadatiDocumento.COD_ENTE, COD_ENTE).add(MetadatiDocumento.COD_AOO, COD_AOO);
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
	public static <F extends MetadatoDocer> String searchMetadata(List<Map<String, String>> metadataList, F key) {
		String metadataValue = null;
		for (Map<String, String> metadata : metadataList) {
			metadataValue = getMetadata(metadata, key);
			if (metadataValue != null)
				break;
		}
		return metadataValue;
	}

	/**
	 * ritorna il valore del metadato con chiave key
	 * @param metadata metadati del documento
	 * @param key nome del metadato
	 * @return valore del metadato o null se non presente key
	 */
	public static <F extends MetadatoDocer> String getMetadata(Map<String, String> metadata, F key) {
		String metadataValue = null;
		if (metadata.containsKey(key.getValue())) {
			metadataValue = metadata.get(key.getValue());
		}
		return metadataValue;
	}

	/**
	 * crea una lista di valori di metadati con chiave key dalla lista di metadati
	 * @param metadataList lista di metadati documenti
	 * @param key chiave del metadato da estrarre
	 * @return array di valori del metadato 
	 */
	public static <F extends MetadatoDocer> String[] joinMetadata(List<Map<String, String>> metadataList, F key) {
		List<String> metadataValues = new ArrayList<>();
		for (Map<String, String> metadata : metadataList) {
			String metadataValue = getMetadata(metadata, key);
			metadataValues.add(metadataValue);
		}
		return metadataValues.toArray(new String[metadataValues.size()]);
	}

	public static <T extends MetadatoDocer> KeyValuePair[] toArray(List<Map<T, String>> listMetadati) {
		List<KeyValuePair> res = new ArrayList<>();
		if (listMetadati != null && !listMetadati.isEmpty()) {
			for (Map<T, String> metadati : listMetadati) {
				for (Entry<T, String> entry : metadati.entrySet()) {
					KeyValuePair kvp = new KeyValuePair();
					kvp.setKey(entry.getKey().getValue());
					kvp.setValue(entry.getValue());
					res.add(kvp);
				}
			}
		}
		return res.toArray(new KeyValuePair[res.size()]);
	}
	
	public static String[] toArrayString(List<String> aList) {
		String[] res = null;
		if (aList != null && !aList.isEmpty()) {
			res  = aList.toArray(new String[aList.size()]);
		}
		return res;
	}

	/**
	 * riduce la lista di mappe di metadati ai soli metadati specificati
	 * @param listaDiMedatadi la lista di mappe di metadati che si desidera ridurre
	 * @param keys le chiavi di metadati che si desidera conservare
	 * @return
	 */
	public static List<Map<String, String>> mapReduce(List<Map<String, String>> listaDiMedatadi, MetadatiDocumento... keys) {
		List<Map<String, String>> res = new ArrayList<>();
		if (listaDiMedatadi != null && !listaDiMedatadi.isEmpty() && keys != null && keys.length > 0) {
			for (Map<String, String> medatadi : listaDiMedatadi) {
				res.add(mapReduce(medatadi, keys));
			}
		}
		return res;
	}
	
	/**
	 * riduce la mappa di metadati ai soli metadati specificati
	 * @param metadati i metadati da ridurre
	 * @param keys le chiavi di metadati che si desidera conservare
	 * @return
	 */
	public static Map<String, String> mapReduce(Map<String, String> metadati, MetadatiDocumento... keys) {
		Map<String, String> res = new HashMap<>();
		if (metadati != null && !metadati.isEmpty() && keys != null && keys.length > 0) {
			for (MetadatiDocumento metadatoMatch : keys) {
				final String keyMatch = metadatoMatch.getValue();
				if (metadati.containsKey(keyMatch)) {
					res.put(keyMatch, metadati.get(keyMatch));
				}				
			}
		}
		return res;
	}
}
