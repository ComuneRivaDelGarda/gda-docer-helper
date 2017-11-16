package it.tn.rivadelgarda.comune.gda.docer.values;

import it.tn.rivadelgarda.comune.gda.docer.keys.MetadatoDocer;

/**
 * <li>PRINCIPALE = Documento che deve essere obbligatoriamente presente nell’Unità Documentaria
 * <li>ALLEGATO = Documento facoltativamente unito al documento principale per integrarne le informazioni registrato contestualmente o precedentemente al documento principale.
 * <li>ANNESSO = Documento facoltativamente unito al documento principale per integrarne le informazioni. E’ registrato in un momento successivo a quello di redazione del documento principale.
 * <li>ANNOTAZIONE = File detached riferiti all’intera unità documentaria (un tipico esempio di annotazione è rappresentato dalla segnatura di protocollo)
 */
public enum TIPO_COMPONENTE implements MetadatoDocer {
	/**
	 * Documento che deve essere obbligatoriamente presente nell’Unità
	 * Documentaria
	 */
	PRINCIPALE(TIPO_COMPONENTE_PRINCIPALE),
	/**
	 * Documento facoltativamente unito al documento principale per
	 * integrarne le informazioni registrato contestualmente o
	 * precedentemente al documento principale.
	 */
	ALLEGATO(TIPO_COMPONENTE_ALLEGATO),
	/**
	 * Documento facoltativamente unito al documento principale per
	 * integrarne le informazioni. E’ registrato in un momento successivo a
	 * quello di redazione del documento principale.
	 */
	ANNESSO(TIPO_COMPONENTE_ANNESSO),
	/**
	 * File detached riferiti all’intera unità documentaria (un tipico
	 * esempio di annotazione è rappresentato dalla segnatura di protocollo)
	 */
	ANNOTAZIONE(TIPO_COMPONENTE_ANNOTAZIONE);
	private String value;

	private TIPO_COMPONENTE(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}