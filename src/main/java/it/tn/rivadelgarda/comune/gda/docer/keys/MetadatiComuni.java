package it.tn.rivadelgarda.comune.gda.docer.keys;

/**
 * 
 * @author mirco
 *
 */
public enum MetadatiComuni implements MetadatoDocer {

	/**
	 * codice AOO
	 */
	COD_AOO(COD_AOO_KEY), 
	/**
	 * codice Ente
	 */
	COD_ENTE(COD_ENTE_KEY);

	private String key;

	private MetadatiComuni(final String key) {
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