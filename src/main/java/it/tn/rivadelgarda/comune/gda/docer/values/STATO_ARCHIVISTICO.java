package it.tn.rivadelgarda.comune.gda.docer.values;

import it.tn.rivadelgarda.comune.gda.docer.keys.MetadatoDocer;

/**
 * indica lo stato “archivistico” di un documento, è un tipo enumerato i cui
 * valori ammissibili sono
 * 
 * @author mirco
 *
 */
public enum STATO_ARCHIVISTICO implements MetadatoDocer {

	GENERICO_DOCUMENT(STATO_ARCHIVISTICO_GENERICO_DOCUMENT), GENERICO_DEFINITIVO(
			STATO_ARCHIVISTICO_GENERICO_DEFINITIVO), REGISTRATO(STATO_ARCHIVISTICO_REGISTRATO), PROTOCOLLATO(
					STATO_ARCHIVISTICO_PROTOCOLLATO), CLASSIFICATO(STATO_ARCHIVISTICO_CLASSIFICATO), FASCICOLATO(
							STATO_ARCHIVISTICO_FASCICOLATO), IN_ARCHIVIO_DI_DEPOSITO(
									STATO_ARCHIVISTICO_IN_ARCHIVIO_DI_DEPOSITO);

	private String value;

	private STATO_ARCHIVISTICO(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}

}