package it.tn.rivadelgarda.comune.gda.docer.values;

import it.tn.rivadelgarda.comune.gda.docer.keys.MetadatoDocer;

/**
 * <li>Archive = Documento Digitale
 * <li>Url = Documento di tipo link ad una Url Esterna)
 * <li>Paper = Documento copia elettronica dell'originale cartaceo quale ad esempio una scansione di un documento cartaceo;
 */
public enum ARCHIVE_TYPE implements MetadatoDocer {
	ARCHIVE(ARCHIVE_TYPE_ARCHIVE), URL(ARCHIVE_TYPE_URL), PAPER(ARCHIVE_TYPE_PAPER);
	private String value;

	private ARCHIVE_TYPE(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}