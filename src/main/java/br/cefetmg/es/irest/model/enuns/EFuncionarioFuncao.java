package br.cefetmg.es.irest.model.enuns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum EFuncionarioFuncao {
	COPA("COPA","enum_funcao_desc_copa"),
	COZINHA("COZINHA","enum_funcao_desc_cozinha");

	private final String key;
	private final String desc;
	/**
	 * @param key
	 * @param desc
	 */
	private EFuncionarioFuncao(String key, String desc) {
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
		List<EFuncionarioFuncao> funcoes  = Arrays.asList(EFuncionarioFuncao.values());

		for (EFuncionarioFuncao f : funcoes) {
			descs.add(f.desc);
		}
		return descs;
	}

	public static String getKey(String desc) {
		List<EFuncionarioFuncao> funcoes  = Arrays.asList(EFuncionarioFuncao.values());

		for (EFuncionarioFuncao f : funcoes) {
			if(f.desc.equals(desc)) {
				return f.key;
			}
		}
		return "";
	}

	public static String getDesc(String key) {
		List<EFuncionarioFuncao> funcoes  = Arrays.asList(EFuncionarioFuncao.values());

		for (EFuncionarioFuncao f : funcoes) {
			if(f.key.equals(key)) {
				return f.desc;
			}
		}
		return "";
	}

	public static boolean contains(String key) {
		List<EFuncionarioFuncao> funcoes  = Arrays.asList(EFuncionarioFuncao.values());

		for (EFuncionarioFuncao f : funcoes) {
			if(f.key.equals(key)) {
				return true;
			}
		}
		return false;
	}
}
