package it.tn.rivadelgarda.comune.gda.docer.keys;

/**
 * 
 * @author mirco
 *
 */
public interface MetadatoDocer {

	public final static String TYPE_ID_KEY = "TYPE_ID";
	public final static String DOCNAME_KEY = "DOCNAME";
	public final static String ABSTRACT_KEY = "ABSTRACT";
	public final static String COD_ENTE_KEY = "COD_ENTE";
	public final static String COD_AOO_KEY = "COD_AOO";
	public final static String TIPO_COMPONENTE_KEY = "TIPO_COMPONENTE";
	public final static String DOCNUM_KEY = "DOCNUM";
	public final static String STATO_ARCHIVISTICO_KEY = "STATO_ARCHIVISTICO";
	public final static String STATO_BUSINESS_KEY = "STATO_BUSINESS";
	public final static String CREATION_DATE_KEY = "CREATION_DATE";
	public final static String ARCHIVE_TYPE_KEY = "ARCHIVE_TYPE";
	public final static String DOC_URL_KEY = "DOC_URL";
	public final static String APP_VERSANTE_KEY = "APP_VERSANTE";
	public final static String DOC_HASH_KEY = "DOC_HASH";
	public final static String DOCNUM_RECORD_KEY = "DOCNUM_RECORD";
	public final static String UD_VERSION_KEY = "UD_VERSION";
	public final static String EXTERNAL_ID_KEY = "EXTERNAL_ID";

	public final static String FOLDER_NAME_KEY = "FOLDER_NAME";
	public final static String FOLDER_OWNER_KEY = "FOLDER_OWNER";
	public final static String DES_FOLDER_KEY = "DES_FOLDER";
	public final static String ENABLED_KEY = "ENABLED";
	public final static String PARENT_FOLDER_NAME_KEY = "PARENT_FOLDER_NAME";
	public final static String FOLDER_ID_KEY = "FOLDER_ID";
	public final static String PARENT_FOLDER_ID_KEY = "PARENT_FOLDER_ID";

	public final static String TYPE_ID_DOCUMENTO = "DOCUMENTO";

	/**
	 * Documento che deve essere obbligatoriamente presente nell’Unità
	 * Documentaria
	 */
	public final static String TIPO_COMPONENTE_PRINCIPALE = "PRINCIPALE";
	/**
	 * Documento facoltativamente unito al documento principale per integrarne
	 * le informazioni registrato contestualmente o precedentemente al documento
	 * principale.
	 */
	public final static String TIPO_COMPONENTE_ALLEGATO = "ALLEGATO";
	/**
	 * Documento facoltativamente unito al documento principale per integrarne
	 * le informazioni. E’ registrato in un momento successivo a quello di
	 * redazione del documento principale.
	 */
	public final static String TIPO_COMPONENTE_ANNESSO = "ANNESSO";
	/**
	 * File detached riferiti all’intera unità documentaria (un tipico esempio
	 * di annotazione è rappresentato dalla segnatura di protocollo)
	 */
	public final static String TIPO_COMPONENTE_ANNOTAZIONE = "ANNOTAZIONE";

	/**
	 * Archive=Documento Digitale;
	 */
	public final static String ARCHIVE_TYPE_ARCHIVE = "ARCHIVE";
	/**
	 * Url=Documento di tipo link ad una Url Esterna)
	 */
	public final static String ARCHIVE_TYPE_URL = "URL";
	/**
	 * Paper=Documento copia elettronica dell'originale cartaceo quale ad esempio
	 * una scansione di un documento cartaceo;
	 */
	public final static String ARCHIVE_TYPE_PAPER = "PAPER";

	public final static String SORT_ASC = "ASC";
	public final static String SORT_DESC = "DESC";

	public final static String CLASSIFICA_KEY = "CLASSIFICA";
	public final static String PROGR_FASCICOLO_KEY = "PROGR_FASCICOLO";
	public final static String ANNO_FASCICOLO_KEY = "ANNO_FASCICOLO";
	public final static String FASC_SECONDARI_KEY = "FASC_SECONDARI";

	public static final String NUM_PG_KEY = "NUM_PG";
	public static final String ANNO_PG_KEY = "ANNO_PG";
	public static final String OGGETTO_PG_KEY = "OGGETTO_PG";
	public static final String REGISTRO_PG_KEY = "REGISTRO_PG";
	public static final String DATA_PG_KEY = "DATA_PG";
	public static final String TIPO_PROTOCOLLAZIONE_KEY = "TIPO_PROTOCOLLAZIONE";
	public static final String MITTENTI_KEY = "MITTENTI";
	public static final String DESTINATARI_KEY = "DESTINATARI";
	public static final String TIPO_FIRMA_KEY = "TIPO_FIRMA";
	public static final String FIRMATARIO_KEY = "FIRMATARIO";
	
	/*
	 * Metadati di Protocollazione
	 */
	public static final String ID_REGISTRO_KEY="ID_REGISTRO";
	public static final String N_REGISTRAZ_KEY="N_REGISTRAZ";
	public static final String D_REGISTRAZ_KEY="D_REGISTRAZ";
	public static final String ANNULL_REGISTRAZ_KEY="ANNULL_REGISTRAZ";
	public static final String D_ANNULL_REGISTRAZ_KEY="D_ANNULL_REGISTRAZ";
	public static final String M_ANNULL_REGISTRAZ_KEY="M_ANNULL_REGISTRAZ";
	public static final String P_ANNULL_REGISTRAZ_KEY="P_ANNULL_REGISTRAZ";
	public static final String A_REGISTRAZ_KEY="A_REGISTRAZ";
	public static final String O_REGISTRAZ_KEY= "O_REGISTRAZ";	
	/*
	 * Metadati di Pubblicazione
	 */
	public static final String REGISTRO_PUB_KEY="REGISTRO_PUB";
	public static final String NUMERO_PUB_KEY="NUMERO_PUB";
	public static final String DATA_INIZIO_PUB_KEY="DATA_INIZIO_PUB";
	public static final String DATA_FINE_PUB_KEY="DATA_FINE_PUB";
	public static final String PUBBLICATO_KEY="PUBBLICATO";
	public static final String OGGETTO_PUB_KEY="OGGETTO_PUB";
	public static final String ANNO_PUB_KEY="ANNO_PUB";
	
	public final static String ACL_FULL_ACCESS = "0";
	public final static String ACL_NORMAL_ACCESS = "1";
	public final static String ACL_READ_ONLY_ACCESS = "2";

	public final static String STATO_ARCHIVISTICO_GENERICO_DOCUMENT = "0";
	public final static String STATO_ARCHIVISTICO_GENERICO_DEFINITIVO = "2";
	public final static String STATO_ARCHIVISTICO_REGISTRATO = "2";
	public final static String STATO_ARCHIVISTICO_PROTOCOLLATO = "3";
	public final static String STATO_ARCHIVISTICO_CLASSIFICATO = "4";
	public final static String STATO_ARCHIVISTICO_FASCICOLATO = "5";
	public final static String STATO_ARCHIVISTICO_IN_ARCHIVIO_DI_DEPOSITO = "6";

	public final static String STATO_BUSINESS_NON_DEFINITO = "0";
	public final static String STATO_BUSINESS_DA_PROTOCOLLARE = "2";
	public final static String STATO_BUSINESS_DA_FASCICOLARE = "2";
	public final static String STATO_BUSINESS_DA_REGISTRARE = "3";
	public final static String STATO_BUSINESS_DA_FIRMARE = "4";

	/*
	 * UTENTE
	 */
	public final static String USER_ID_KEY = "USER_ID";
	public final static String FULL_NAME_KEY = "FULL_NAME";
	public final static String USER_PASSWORD_KEY = "USER_PASSWORD";
	public final static String FIRST_NAME_KEY = "FIRST_NAME";
	public final static String LAST_NAME_KEY = "LAST_NAME";
	public final static String EMAIL_ADDRESS_KEY = "EMAIL_ADDRESS";
	public final static String USER_ENABLED_KEY = "ENABLED";
	/*
	 * GRUPPO
	 */
	public final static String GROUP_ID_KEY = "GROUP_ID";
	public final static String GROUP_NAME_KEY = "GROUP_NAME";
	public final static String PARENT_GROUP_ID_KEY = "PARENT_GROUP_ID";

	String getValue();
}