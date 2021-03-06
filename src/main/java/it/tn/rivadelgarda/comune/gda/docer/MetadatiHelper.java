package it.tn.rivadelgarda.comune.gda.docer;

import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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

//	public static KeyValuePair createKey(String key, String min, String max) {
//		KeyValuePair res = new KeyValuePair();
//		res.setKey(key);
//		if (StringUtils.isNotBlank(min) && StringUtils.isNotBlank(max)) {
//			res.setValue("[" + min + " TO " + max + "]");
////			res.setValue("" + min + " TO " + max + "");
//		} else if (StringUtils.isNotBlank(min)) {
//			res.setValue("[" + min + " TO *]");
//		} else {
//			res.setValue("[* TO " + max + "]");
//		}
//		return res;
//	}
	
	/**
	 * 
	 * @param key nome del metadato
	 * @param min limite inferiore, 0 se non si vuole considerare il limite inferiore
	 * @param max limite superiore, 0 se non si vuole considerare il limite superiore
	 * @return
	 */
	public static KeyValuePair createKey(String key, long min, long max) {
		KeyValuePair res = new KeyValuePair();
		res.setKey(key);
		if (min < max) {
			res.setValue("[" + min + " TO " + max + "]");
		} else if (min > 0) {
			res.setValue("[" + min + " TO *]");
		} else {
			res.setValue("[* TO " + max + "]");
		}
		return res;
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static KeyValuePair createKey(String key, Date value) {
		KeyValuePair res = new KeyValuePair();
		res.setKey(key);
		String isoPattern = new SimpleDateFormat("yyyy-MM-dd").format(value) + "T*";
		res.setValue(isoPattern);
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
	public static <F extends MetadatoDocer> MetadatiHelper<F> build(F key, String... values) {
		MetadatiHelper<F> res = new MetadatiHelper<F>();
		res.add(key, values);
		return res;
	}
	
	/**
	 * 
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public static <F extends MetadatoDocer> MetadatiHelper<F> build(F key, long min, long max) {
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
	public MetadatiHelper add(T key, String... values) {
		for (String value : values) {
			this.list.add(createKey(key.getValue(), value));
		}
		return this;
	}
	
	public MetadatiHelper add(T key, long min, long max) {
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
	 * @param TYPE_ID obbligatorio, il document-type del documento. è il tipo di documento individua l’insieme delle proprietà del profilo
	 * @param DOCNAME obbligatorio, il nome del documento comprensivo di estensione del file allegato
	 * @param COD_ENTE codice dell’Ente assegnato al documento
	 * @param COD_AOO codice della AOO assegnata al documento
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

	public static <F extends MetadatoDocer> List<String> listMetadataValues(List<Map<String, String>> metadataList, F key) {
		List<String> metadataValues = new ArrayList<>();
		for (Map<String, String> metadata : metadataList) {
			String metadataValue = getMetadata(metadata, key);
			metadataValues.add(metadataValue);
		}
		return metadataValues;
	}
	
	public static <F extends MetadatoDocer> List<String> listMetadataValues(SearchItem[] data, F key) {
		return listMetadataValues(asListMap(data), key);
	}
	
	public static <F extends MetadatoDocer> Set<String> setOfMetadataValues(Collection<Map<String, String>> metadataList, F key) {
		Set<String> metadataValues = new HashSet<>();
		for (Map<String, String> metadata : metadataList) {
			String metadataValue = getMetadata(metadata, key);
			metadataValues.add(metadataValue);
		}
		return metadataValues;
	}
	
	public static <F extends MetadatoDocer> Set<String> setOfMetadataValues(SearchItem[] data, F key) {
		return setOfMetadataValues(asListMap(data), key);
	}
	
	/**
	 * crea una lista di valori di metadati con chiave key dalla lista di metadati
	 * @param metadataList lista di metadati documenti
	 * @param key chiave del metadato da estrarre
	 * @return array di valori del metadato 
	 */
	public static <F extends MetadatoDocer> String[] joinMetadata(List<Map<String, String>> metadataList, F key) {
		List<String> metadataValues = listMetadataValues(metadataList, key);
		// return metadataValues.toArray(new String[metadataValues.size()]);
		return asArray(metadataValues);
	}	

	public static String[] asArray(List<String> metadataValues) {
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
	public static <T extends MetadatoDocer> Collection<Map<String, String>> mapReduce(Collection<Map<String, String>> listaDiMedatadi, T... keys) {
		Collection<Map<String, String>> res = new ArrayList<>();
		if (listaDiMedatadi != null && !listaDiMedatadi.isEmpty() && keys != null && keys.length > 0) {
			for (Map<String, String> medatadi : listaDiMedatadi) {
				res.add(mapReduce(medatadi, keys));
			}
		}
		return res;
	}
	
//	public static <T extends MetadatoDocer> Set<Map<String, String>> mapReduce(Set<Map<String, String>> listaDiMedatadi, T... keys) {
//		Set<Map<String, String>> res = new HashSet<>();
//		if (listaDiMedatadi != null && !listaDiMedatadi.isEmpty() && keys != null && keys.length > 0) {
//			for (Map<String, String> medatadi : listaDiMedatadi) {
//				res.add(mapReduce(medatadi, keys));
//			}
//		}
//		return res;
//	}
	
	public static <T extends MetadatoDocer> Collection<Map<String, String>> mapReduce(Collection<Map<String, String>> listaDiMedatadi, List<T> keys) {
		return mapReduce(listaDiMedatadi, keys.toArray(new MetadatoDocer[keys.size()]));
	}
	
	
	/**
	 * riduce la mappa di metadati ai soli metadati specificati
	 * @param metadati i metadati da ridurre
	 * @param keys le chiavi di metadati che si desidera conservare
	 * @return
	 */
	public static <T extends MetadatoDocer> Map<String, String> mapReduce(Map<String, String> metadati, T... keys) {
		Map<String, String> res = new HashMap<>();
		if (metadati != null && !metadati.isEmpty() && keys != null && keys.length > 0) {
			for (MetadatoDocer metadatoMatch : keys) {
				final String keyMatch = metadatoMatch.getValue();
				if (metadati.containsKey(keyMatch)) {
					res.put(keyMatch, metadati.get(keyMatch));
				}				
			}
		}
		return res;
	}
}
