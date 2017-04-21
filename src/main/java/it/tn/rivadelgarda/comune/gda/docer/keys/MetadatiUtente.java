package it.tn.rivadelgarda.comune.gda.docer.keys;

/**
 * 
 * @author mirco
 *
 */
public enum MetadatiUtente implements MetadatoDocer {

	/**
	 * (la AOO primaria di appartenenza)
	 */
	COD_AOO(COD_AOO_KEY), 
	/**
	 * (l’Ente primario di appartenenza)
	 */
	COD_ENTE(COD_ENTE_KEY),
	/**
	 * id dell’utente,
	 */
	USER_ID(USER_ID_KEY),
	/**
	 * (nome completo dell’utente)
	 */
	FULL_NAME(FULL_NAME_KEY),
	/**
	 * è possibile assegnare un default in fase di creazione ma non fa parte del
	 * profilo
	 * 
	 */
	USER_PASSWORD(USER_PASSWORD_KEY),
	/**
	 * (nome dell’utente)
	 */
	FIRST_NAME(FIRST_NAME_KEY),
	/**
	 * (cognome dell’utente)
	 */
	LAST_NAME(LAST_NAME_KEY),
	/**
	 * (indirizzo email dell’utente)
	 */
	EMAIL_ADDRESS(EMAIL_ADDRESS_KEY);

	private String key;

	private MetadatiUtente(final String key) {
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