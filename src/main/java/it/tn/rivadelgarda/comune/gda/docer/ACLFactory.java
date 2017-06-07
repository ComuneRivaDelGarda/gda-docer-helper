package it.tn.rivadelgarda.comune.gda.docer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import it.tn.rivadelgarda.comune.gda.docer.values.ACL_VALUES;

public class ACLFactory {

	protected ACLFactory() {
		super();
	}
	//
	// private class ACL {
	// String subject;
	// ACL_VALUES access;
	//
	// public ACL(String subject, ACL_VALUES access) {
	// super();
	// this.subject = subject;
	// this.access = access;
	// }
	// }

	private Map<String, ACL_VALUES> acls = new HashMap<>();

	// public static ACLsFactory create(String subject, ACL_VALUES access) {
	// ACLsFactory factory = new ACLsFactory();
	// ACL acl = new ACL(subject, access);
	// factory.lista.add(acl);
	// return factory;
	// }

	public static ACLFactory create(String subject, ACL_VALUES access) {
		ACLFactory factory = new ACLFactory();
		factory.acls.put(subject, access);
		return factory;
	}

	public ACLFactory add(String subject, ACL_VALUES access) {
		this.acls.put(subject, access);
		return this;
	}

	public Map<String, ACL_VALUES> get() {
		return acls;
	}
}
