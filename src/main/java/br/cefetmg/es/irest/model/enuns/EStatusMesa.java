package br.cefetmg.es.irest.model.enuns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum EStatusMesa {
	LIVRE("LIVRE", "enum_status_mesa_desc_livre"),
	OCUPADA("OCUPADA", "enum_status_mesa_desc_ocupada");


	private final String key;
	private final String desc;
	/**
	 * @param key
	 * @param desc
	 */
	private EStatusMesa(String key, String desc) {
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
		List<EStatusMesa> funcoes  = Arrays.asList(EStatusMesa.values());

		for (EStatusMesa f : funcoes) {
			descs.add(f.desc);
		}
		return descs;
	}

	public static String getKey(String desc) {
		List<EStatusMesa> funcoes  = Arrays.asList(EStatusMesa.values());

		for (EStatusMesa f : funcoes) {
			if(f.desc.equals(desc)) {
				return f.key;
			}
		}
		return "";
	}

	public static String getDesc(String key) {
		List<EStatusMesa> funcoes  = Arrays.asList(EStatusMesa.values());

		for (EStatusMesa f : funcoes) {
			if(f.key.equals(key)) {
				return f.desc;
			}
		}
		return "";
	}

	public static boolean contains(String key) {
		List<EStatusMesa> funcoes  = Arrays.asList(EStatusMesa.values());

		for (EStatusMesa f : funcoes) {
			if(f.key.equals(key)) {
				return true;
			}
		}
		return false;
	}
}
