package it.tn.rivadelgarda.comune.gda.docer.values;

import it.tn.rivadelgarda.comune.gda.docer.keys.MetadatoDocer;

/**
 * Nel modello GeDoc la visibilità dei documenti e delle voci di anagrafica
 * (titolari e soprattutto fascicoli) è regolata dai permessi o livelli di
 * accesso degli utenti: le Access Control List (ACL). 62Tali diritti possono
 * essere definiti su base utenti e/o gruppi e permettono di stabilire con
 * granularità i livelli di accesso su ogni documento e voce di anagrafica. Ogni
 * utente possiede diritti globali su un singolo documento o voce di anagrafica
 * dati dall’unione dei diritti espliciti dell’utente su quel determinato
 * oggetto e di quelli dei gruppi cui l’utente appartiene che possiedono diritti
 * sull’oggetto stesso.
 * <p>
 * In generale l’assegnazione delle ACL ad un documento o una voce di anagrafica
 * non solo regola l’accesso agli stessi, ma in assenza di diritti espliciti, un
 * utente, oltre che non avere alcun diritto ad operare su quel determinato
 * oggetto, non avrà neanche la possibilità di conoscerne l’esistenza, in quanto
 * il documento o la voce di anagrafica risulterà semplicemente inesistente.
 * <p>
 * Nel modello GeDoc sono stati definiti 3 livelli di permessi o livelli di
 * accesso ai documenti o voci di anagrafiche:
 * <ul>
 * <li>Read Only Access {@link #READ_ONLY_ACCESS}
 * <li>Normal Access {@link #NORMAL_ACCESS}
 * <li>Full Access {@link #FULL_ACCESS}
 * </ul>
 * 
 * @author mirco
 *
 */
public enum ACL_VALUES implements MetadatoDocer {

	/**
	 * Read Only Access: corrisponde alla lettura del profilo del
	 * documento/anagrafica e nel caso dei documenti anche la visualizzazione
	 * del file associato al documento.
	 */
	READ_ONLY_ACCESS(ACL_READ_ONLY_ACCESS),
	/**
	 * Normal Access: corrisponde ai permessi di scrittura (modifica del profilo
	 * dei documenti/anagrafiche, gestione delle versioni e delle correlazioni
	 * dei documenti). Tale diritto non abilita l’utente alla cancellazione del
	 * documento/anagrafica, né la gestione dei diritti del
	 * documento/anagrafica. A livello di voce di Titolario e Fascicolo permette
	 * la creazione di voci di Titolario figlio e sotto-fascicoli ovvero
	 * l’inserimento di Documenti nel fascicolo/sotto-fascicolo.
	 */
	NORMAL_ACCESS(ACL_NORMAL_ACCESS),
	/**
	 * Full Access: è il permesso di livello massimo ovvero diritti di scrittura
	 * con cancellazione del documento/anagrafica e gestione dei diritti del
	 * documento/anagrafica.
	 */
	FULL_ACCESS(ACL_FULL_ACCESS);

	private String value;

	private ACL_VALUES(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}

}