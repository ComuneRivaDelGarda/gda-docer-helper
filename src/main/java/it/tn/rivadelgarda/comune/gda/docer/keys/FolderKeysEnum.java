package it.tn.rivadelgarda.comune.gda.docer.keys;

/**
 * 
 * @author mirco
 *
 */
public enum FolderKeysEnum implements MetadatoDocer {

    /**
     * identificativo univoco del folder assegnato automaticamente dal sistema,
     */
    FOLDER_ID("FOLDER_ID"),
    /**
     * (Nome del folder; p.e. “cartella1”)
     */
    FOLDER_NAME("FOLDER_NAME"),
    /**
     * Utente proprietario del folder
     * 
     */
    FOLDER_OWNER("FOLDER_OWNER"), COD_AOO("COD_AOO"), COD_ENTE("COD_ENTE"),
    /**
     * (descrizione)
     */
    DES_FOLDER("DES_FOLDER"),
    /**
     * (flag abilitato/disabilitato)
     */
    ENABLED("ENABLED"), PARENT_FOLDER_NAME("PARENT_FOLDER_NAME"),
    /**
     * (id folder “padre” o di livello superiore)
     */
    PARENT_FOLDER_ID("PARENT_FOLDER_ID");

    private String key;

    private FolderKeysEnum(final String key) {
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