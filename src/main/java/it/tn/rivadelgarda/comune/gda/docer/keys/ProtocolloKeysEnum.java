package it.tn.rivadelgarda.comune.gda.docer.keys;

/**
 * 
 * @author mirco
 *
 */
public enum ProtocolloKeysEnum implements MetadatoDocer {

    /**
     * codice dell’Ente assegnato al documento
     */
    COD_ENTE("COD_ENTE"),
    /**
     * codice della AOO assegnata al documento
     */
    COD_AOO("COD_AOO"),
    /**
     * numero di protocollo assegnato al documento
     */
    NUM_PG("NUM_PG"),
    /**
     * anno di protocollo assegnato al documento, se assente viene ricavata da
     * DATA_PG; se specificato deve coincidere con l’anno presente in DATA_PG
     */
    ANNO_PG("ANNO_PG"),
    /**
     * oggetto del protocollo
     */
    OGGETTO_PG("OGGETTO_PG"),
    /**
     * registro del protocollo
     */
    REGISTRO_PG("REGISTRO_PG"),
    /**
     * data di protocollazione
     */
    DATA_PG("DATA_PG"),
    /**
     * tipo protocollazione
     * <p>
     * <ul>
     * <li>E (entrata)</li>
     * <li>I (interna)</li>
     * <li>U (uscita)</li>
     * <li>ND (non definita)</li>
     * </ul>
     * </p>
     */
    TIPO_PROTOCOLLAZIONE("TIPO_PROTOCOLLAZIONE"),
    /**
     * i mittenti (è anche metadato di Registrazione)
     */
    MITTENTI("MITTENTI"),
    /**
     * i destinatari (è anche metadato di Registrazione)
     */
    DESTINATARI("DESTINATARI"),
    /**
     * tipo firma (è anche metadato di Registrazione)
     */
    TIPO_FIRMA("TIPO_FIRMA"),
    /**
     * tfirmatario : firmatario (è anche metadato di Registrazione)
     */
    FIRMATARIO("FIRMATARIO");

    private String key;

    private ProtocolloKeysEnum(final String key) {
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

}