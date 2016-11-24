package it.tn.rivadelgarda.comune.gda.docer.keys;

public enum ACLValuesEnum implements KeyValuePairEnum {
	READ_ONLY_ACCESS(0), NORMAL_ACCESS(1), FULL_ACCESS(2);

	private int value;

	private ACLValuesEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String getKey() {
		return String.valueOf(value);
	}

}