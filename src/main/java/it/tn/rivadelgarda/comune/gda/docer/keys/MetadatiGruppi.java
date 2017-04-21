package it.tn.rivadelgarda.comune.gda.docer.keys;

/**
 * 
 * @author mirco
 *
 */
public enum MetadatiGruppi implements MetadatoDocer {

	/**
	 * (id del gruppo)
	 */
	GROUP_ID(GROUP_ID_KEY),
	/**
	 * (nome del gruppo)
	 */
	GROUP_NAME(GROUP_NAME_KEY),
	/**
	 * id del gruppo padre)
	 */
	PARENT_GROUP_ID(PARENT_GROUP_ID_KEY);

	private String key;

	private MetadatiGruppi(final String key) {
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