package br.cefetmg.es.irest.model.enuns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ECapacidadeMesa {
	QUATRO("QUATRO", "4"),
	SEIS("SEIS", "6"),
	OITO("OITO", "8"),
	DOZE("DOZE", "12");


	private final String key;
	private final String desc;
	/**
	 * @param key
	 * @param desc
	 */
	private ECapacidadeMesa(String key, String desc) {
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
		List<ECapacidadeMesa> funcoes  = Arrays.asList(ECapacidadeMesa.values());

		for (ECapacidadeMesa f : funcoes) {
			descs.add(f.desc);
		}
		return descs;
	}

	public static String getKey(String desc) {
		List<ECapacidadeMesa> funcoes  = Arrays.asList(ECapacidadeMesa.values());

		for (ECapacidadeMesa f : funcoes) {
			if(f.desc.equals(desc)) {
				return f.key;
			}
		}
		return "";
	}

	public static String getDesc(String key) {
		List<ECapacidadeMesa> funcoes  = Arrays.asList(ECapacidadeMesa.values());

		for (ECapacidadeMesa f : funcoes) {
			if(f.key.equals(key)) {
				return f.desc;
			}
		}
		return "";
	}

	public static boolean contains(String key) {
		List<ECapacidadeMesa> funcoes  = Arrays.asList(ECapacidadeMesa.values());

		for (ECapacidadeMesa f : funcoes) {
			if(f.key.equals(key)) {
				return true;
			}
		}
		return false;
	}
}
