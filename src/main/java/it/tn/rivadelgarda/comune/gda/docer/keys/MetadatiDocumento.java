package it.tn.rivadelgarda.comune.gda.docer.keys;

import java.util.ArrayList;
import java.util.List;

/**
 * Profilo di un documento
 * 
 * Ogni documento versato nel documentale possiederà un insieme di proprietà i
 * cui nomi sono predefiniti e così conosciuti a tutti i livelli del modello
 * GeDoc. L’insieme delle proprietà può cambiare da tipo a tipo di documento.
 * 
 * Indipendentemente dal tipo, i metadati di base posseduti da ogni documento
 * sono:
 * 
 * @author mirco
 *
 */
public enum MetadatiDocumento implements MetadatoDocer {

	/**
	 * Metadati di base generici:
	 */

	/**
	 * codice dell’Ente assegnato al documento
	 */
	COD_ENTE(COD_ENTE_KEY),
	/**
	 * codice della AOO assegnata al documento
	 */
	COD_AOO(COD_AOO_KEY),
	/**
	 * obbligatorio, il document-type del documento. è il tipo di documento,
	 * individua l’insieme delle proprietà del profilo
	 */
	TYPE_ID(TYPE_ID_KEY),
	/**
	 * obbligatorio, il nome del documento comprensivo di estensione del file
	 * allegato
	 */
	DOCNAME(DOCNAME_KEY),
	/**
	 * non obblicatorio, descrizione del documetno
	 */
	ABSTRACT(ABSTRACT_KEY),
	/**
	 * non obbligatorio, definisce il tipo di componente dell’unità documentaria
	 * di protocollo
	 */
	TIPO_COMPONENTE(TIPO_COMPONENTE_KEY),

	/**
	 * identificativo univoco del documento assegnato automaticamente dal
	 * sistema
	 */
	DOCNUM(DOCNUM_KEY),

	/**
	 * indica lo stato “archivistico” di un documento, è un tipo enumerato i cui
	 * valori ammissibili sono:
	 * <li>0 (generico document)
	 * <li>1 (generico definitivo)
	 * <li>2 (registrato)
	 * <li>3 (protocollato)
	 * <li>4 (classificato)
	 * <li>5 (fascicolato)
	 * <li>6 (in archivio di deposito)
	 */
	STATO_ARCHIVISTICO(STATO_ARCHIVISTICO_KEY),
	/**
	 * indica lo stato avanzamento nel workflow di protocollazione/registrazione
	 * di un documento, è un tipo enumerato i cui valori ammissibili sono:
	 * <li>0 (non definito)
	 * <li>1 (da protocollare)
	 * <li>2 (da fascicolare)
	 */
	STATO_BUSINESS(STATO_BUSINESS_KEY),
	/**
	 * è la data di creazione del documento (se non specificata viene assegnata
	 * automaticamente),
	 */
	CREATION_DATE(CREATION_DATE_KEY),
	/**
	 * identifica il tipo di documento, è un tipo enumerato i cui valori
	 * ammissibili sono: ARCHIVE: documento fisico; URL: puntamento ad una URL
	 * esterna in cui è presente il documento;
	 */
	ARCHIVE_TYPE(ARCHIVE_TYPE_KEY),
	/**
	 * è la URL con cui è possibile recuperare il file del documento (il file
	 * non viene memorizzato nel documentale);
	 */
	DOC_URL(DOC_URL_KEY),
	/**
	 * Identificativo dell’applicazione versante nell’archivio corrente
	 */
	APP_VERSANTE(APP_VERSANTE_KEY),
	/**
	 * Impronta del documento
	 */
	DOC_HASH(DOC_HASH_KEY),
	/**
	 * nel caso di versioning avanzato è l’identificativo del documento che
	 * rappresenta la versione avanzata registrata/protocollata (in particolare
	 * è l’identificativo del documento principale dell’unità documentaria
	 * registrata/protocollata),
	 */
	DOCNUM_RECORD(DOCNUM_RECORD_KEY),
	/**
	 * Versione dell’unità documentaria (applicabile al solo versioning
	 * avanzato) per identificare il numero della versione in formato
	 * alfanumerico.
	 */
	UD_VERSION(UD_VERSION_KEY),
	/**
	 * METADATO aggiunto il 16/03/2017 per memorizzare ID VERSANTE e fare
	 * successive ricerche per documenti appartenenti alla stessa ENTITA' del
	 * VERSANTE
	 */
	EXTERNAL_ID(EXTERNAL_ID_KEY),

	/*
	 * Metadati di classificazione e fascicolazione:
	 */

	/**
	 * è la classifica del Titolario assegnato al documento
	 */
	CLASSIFICA(CLASSIFICA_KEY),
	/**
	 * è il numero progressivo del Fascicolo primario assegnato al documento
	 */
	PROGR_FASCICOLO(PROGR_FASCICOLO_KEY),
	/**
	 * l’anno del Fascicolo primario assegnato al documento
	 */
	ANNO_FASCICOLO(ANNO_FASCICOLO_KEY),
	/**
	 * fascicoli secondari del documento (lista dei fascicoli secondari
	 * separarti dal carattere ‘;’. Ogni fascicolo secondario viene indicato
	 * concatenando classifica, anno fascicolo secondario e progressivo
	 * fascicolo secondario con il carattere ‘/’ come nel seguente esempio:
	 * “1.3.5/2012/1/2/1/3;1.3.6/2012/3/1”).
	 */
	FASC_SECONDARI(FASC_SECONDARI_KEY),

	/*
	 * Metadati di protocollazione:
	 */

	/**
	 * numero di protocollo assegnato al documento
	 */
	NUM_PG(NUM_PG_KEY),
	/**
	 * anno di protocollo assegnato al documento, se assente viene ricavata da
	 * DATA_PG; se specificato deve coincidere con l’anno presente in DATA_PG
	 */
	ANNO_PG(ANNO_PG_KEY),
	/**
	 * oggetto del protocollo
	 */
	OGGETTO_PG(OGGETTO_PG_KEY),
	/**
	 * registro del protocollo
	 */
	REGISTRO_PG(REGISTRO_PG_KEY),
	/**
	 * data di protocollazione
	 */
	DATA_PG(DATA_PG_KEY),
	/**
	 * tipo protocollazione
	 * <p>
	 * <li>E (entrata)</li>
	 * <li>I (interna)</li>
	 * <li>U (uscita)</li>
	 * <li>ND (non definita)</li>
	 * </p>
	 */
	TIPO_PROTOCOLLAZIONE(TIPO_PROTOCOLLAZIONE_KEY),
	/**
	 * i mittenti (è anche metadato di Registrazione)
	 */
	MITTENTI(MITTENTI_KEY),
	/**
	 * i destinatari (è anche metadato di Registrazione)
	 */
	DESTINATARI(DESTINATARI_KEY),
	/**
	 * tipo firma (è anche metadato di Registrazione)
	 */
	TIPO_FIRMA(TIPO_FIRMA_KEY),
	/**
	 * tfirmatario : firmatario (è anche metadato di Registrazione)
	 */
	FIRMATARIO(FIRMATARIO_KEY);

	/**
	 * Metadati per la conservazione sostitutiva:
	 */

	/**
	 * Metadati di Registrazione
	 */

	/**
	 * Metadati di Pubblicazione:
	 */

	private String key;

	private MetadatiDocumento(final String key) {
		this.key = key;
	}

	@Override
	public String getValue() {
		return key;
	}

	@Override
	public String toString() {
		return this.getValue();
	}

	public static List<String> getKeyList() {
		ArrayList<String> res = new ArrayList<>();
		for (MetadatiDocumento k : MetadatiDocumento.values()) {
			res.add(k.getValue());
		}
		return res;
	}

	public enum TYPE_ID_VALUES implements MetadatoDocer {
		DOCUMENTO(TYPE_ID_DOCUMENTO);
		private String value;

		private TYPE_ID_VALUES(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 
	 * @author mirco
	 *
	 */
	public enum TIPO_COMPONENTE_VALUES implements MetadatoDocer {
		PRINCIPALE(TIPO_COMPONENTE_PRINCIPALE), ALLEGATO(TIPO_COMPONENTE_ALLEGATO), ANNESSO(
				TIPO_COMPONENTE_ANNESSO), ANNOTAZIONE(TIPO_COMPONENTE_ANNOTAZIONE);
		private String value;

		private TIPO_COMPONENTE_VALUES(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 
	 * @author mirco
	 *
	 */
	public enum ARCHIVE_TYPE_VALUES implements MetadatoDocer {
		ARCHIVE(ARCHIVE_TYPE_ARCHIVE), URL(ARCHIVE_TYPE_URL);
		private String value;

		private ARCHIVE_TYPE_VALUES(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

}