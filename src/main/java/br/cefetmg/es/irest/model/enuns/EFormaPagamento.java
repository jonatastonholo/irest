package br.cefetmg.es.irest.model.enuns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum EFormaPagamento {
	VISA 	("VISA","enum_forma_pagamento_visa"),
	MASTER	("MASTER","enum_forma_pagamento_master"),
	CHEQUE	("CHEQUE","enum_forma_pagamento_cheque"),
	DINHEIRO("DINHEIRO","enum_forma_pagamento_dinheiro");

	private final String key;
	private final String desc;
	/**
	 * @param key
	 * @param desc
	 */
	private EFormaPagamento(String key, String desc) {
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
		List<EFormaPagamento> funcoes  = Arrays.asList(EFormaPagamento.values());

		for (EFormaPagamento f : funcoes) {
			descs.add(f.desc);
		}
		return descs;
	}

	public static String getKey(String desc) {
		List<EFormaPagamento> funcoes  = Arrays.asList(EFormaPagamento.values());

		for (EFormaPagamento f : funcoes) {
			if(f.desc.equals(desc)) {
				return f.key;
			}
		}
		return "";
	}

	public static String getDesc(String key) {
		List<EFormaPagamento> funcoes  = Arrays.asList(EFormaPagamento.values());

		for (EFormaPagamento f : funcoes) {
			if(f.key.equals(key)) {
				return f.desc;
			}
		}
		return "";
	}

	public static boolean contains(String key) {
		List<EFormaPagamento> funcoes  = Arrays.asList(EFormaPagamento.values());

		for (EFormaPagamento f : funcoes) {
			if(f.key.equals(key)) {
				return true;
			}
		}
		return false;
	}


}
