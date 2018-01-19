package it.tn.rivadelgarda.comune.gda.docer;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import it.tn.rivadelgarda.comune.gda.docer.values.ACL;

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

	private Map<String, ACL> acls = new HashMap<>();

	// public static ACLsFactory create(String subject, ACL_VALUES access) {
	// ACLsFactory factory = new ACLsFactory();
	// ACL acl = new ACL(subject, access);
	// factory.lista.add(acl);
	// return factory;
	// }

	/**
	 * crea ACLFactory
	 * @param GROUP_USER_ID groupId or userId
	 * @param acl {@link ACL}
	 * @return
	 */
	public static ACLFactory create(String GROUP_USER_ID, ACL acl) {
		ACLFactory factory = new ACLFactory();
		factory.acls.put(GROUP_USER_ID, acl);
		return factory;
	}
	
	public static ACLFactory create(Map<String, Integer> acl) {
		ACLFactory factory = new ACLFactory();
		MetadatiHelper<ACL> keyBuilder = new MetadatiHelper<>();
		for (Entry<String, Integer> entry : acl.entrySet()) {
			factory.acls.put(entry.getKey(), ACL.values()[entry.getValue()]);
		}		
		return factory;
	}

	/**
	 * aggiungi ACL 
	 * @param GROUP_USER_ID groupId or userId
	 * @param access {@link ACL}
	 * @return
	 */
	public ACLFactory add(String GROUP_USER_ID, ACL access) {
		this.acls.put(GROUP_USER_ID, access);
		return this;
	}

	public Map<String, ACL> get() {
		return acls;
	}
}
