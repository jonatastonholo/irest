package br.cefetmg.es.irest.model.enuns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum EUsuarioRole {
	ROLE_ADMIN("ROLE_ADMIN","enum_role_desc_admin"),
	ROLE_CLIENTE("ROLE_CLIENTE","enum_role_desc_customer"),
	ROLE_FUNCIONARIO("ROLE_FUNCIONARIO", "enum_role_desc_employee");

	private final String key;
	private final String desc;
	/**
	 * @param key
	 * @param desc
	 */
	private EUsuarioRole(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	public static List<String> descs() {
		List<String> descs = new ArrayList<String>();
		List<EUsuarioRole> roles  = Arrays.asList(EUsuarioRole.values());

		for (EUsuarioRole r : roles) {
			descs.add(r.desc);
		}
		return descs;
	}

	public static String getKey(String desc) {
		List<EUsuarioRole> roles  = Arrays.asList(EUsuarioRole.values());

		for (EUsuarioRole r : roles) {
			if(r.desc.equals(desc)) {
				return r.key;
			}
		}
		return "";
	}

	public static String getDesc(String key) {
		List<EUsuarioRole> roles  = Arrays.asList(EUsuarioRole.values());

		for (EUsuarioRole r : roles) {
			if(r.key.equals(key)) {
				return r.desc;
			}
		}
		return "";
	}

}
