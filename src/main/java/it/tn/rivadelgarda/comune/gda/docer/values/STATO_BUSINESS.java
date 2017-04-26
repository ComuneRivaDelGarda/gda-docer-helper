package it.tn.rivadelgarda.comune.gda.docer.values;

import it.tn.rivadelgarda.comune.gda.docer.keys.MetadatoDocer;

/**
 * indica lo stato avanzamento nel workflow di protocollazione/registrazione di
 * un documento, è un tipo enumerato i cui valori ammissibili sono
 * 
 * @author mirco
 *
 */
public enum STATO_BUSINESS implements MetadatoDocer {

	NON_DEFINITO(STATO_BUSINESS_NON_DEFINITO),
	DA_PROTOCOLLARE(STATO_BUSINESS_DA_PROTOCOLLARE),
	DA_FASCICOLARE(STATO_BUSINESS_DA_FASCICOLARE),
	DA_REGISTRARE(STATO_BUSINESS_DA_REGISTRARE),
	DA_FIRMARE(STATO_BUSINESS_DA_FIRMARE);

	private String value;

	private STATO_BUSINESS(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}

}