package it.tn.rivadelgarda.comune.gda.docer.values;

import it.tn.rivadelgarda.comune.gda.docer.keys.MetadatoDocer;

/**
 * <li>DOCUMENTO
 */
public enum TYPE_ID implements MetadatoDocer {
	DOCUMENTO(TYPE_ID_DOCUMENTO);
	private String value;

	private TYPE_ID(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}