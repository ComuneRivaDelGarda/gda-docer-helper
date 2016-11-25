package it.tn.rivadelgarda.comune.gda.docer.values;

import it.tn.rivadelgarda.comune.gda.docer.keys.DocerKey;

public enum ACLValuesEnum implements DocerKey {
	READ_ONLY_ACCESS(0), NORMAL_ACCESS(1), FULL_ACCESS(2);

	private int value;

	private ACLValuesEnum(int value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return String.valueOf(value);
	}

}