package it.tn.rivadelgarda.comune.gda.docer.keys;

/**
 * 
 * @author mirco
 *
 */
public enum MetadatiFolder implements MetadatoDocer {

    /**
     * identificativo univoco del folder assegnato automaticamente dal sistema,
     */
    FOLDER_ID(FOLDER_ID_KEY),
    /**
     * (Nome del folder; p.e. “cartella1”)
     */
    FOLDER_NAME(FOLDER_NAME_KEY),
    /**
     * Utente proprietario del folder
     * 
     */
    FOLDER_OWNER(FOLDER_OWNER_KEY), COD_AOO(COD_AOO_KEY), COD_ENTE(COD_ENTE_KEY),
    /**
     * (descrizione)
     */
    DES_FOLDER(DES_FOLDER_KEY),
    /**
     * (flag abilitato/disabilitato)
     */
    ENABLED(ENABLED_KEY), PARENT_FOLDER_NAME(PARENT_FOLDER_NAME_KEY),
    /**
     * (id folder “padre” o di livello superiore)
     */
    PARENT_FOLDER_ID(PARENT_FOLDER_ID_KEY);

    private String key;

    private MetadatiFolder(final String key) {
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