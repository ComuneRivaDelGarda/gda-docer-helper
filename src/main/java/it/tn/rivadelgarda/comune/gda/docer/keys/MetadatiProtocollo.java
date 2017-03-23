package it.tn.rivadelgarda.comune.gda.docer.keys;

/**
 * 
 * @author mirco
 *
 */
public enum MetadatiProtocollo implements MetadatoDocer {

    /**
     * codice dell’Ente assegnato al documento
     */
    COD_ENTE(COD_ENTE_KEY),
    /**
     * codice della AOO assegnata al documento
     */
    COD_AOO(COD_AOO_KEY),
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
     * <ul>
     * <li>E (entrata)</li>
     * <li>I (interna)</li>
     * <li>U (uscita)</li>
     * <li>ND (non definita)</li>
     * </ul>
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

    private String key;

    private MetadatiProtocollo(final String key) {
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