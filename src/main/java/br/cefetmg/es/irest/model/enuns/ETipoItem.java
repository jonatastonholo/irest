package br.cefetmg.es.irest.model.enuns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ETipoItem {
	BEBIDAS ("BEBIDAS", "enum_tipo_item_bebidas"),
	APERITIVOS ("APERITIVOS","enum_tipo_item_aperitivos"),
	SANDUICHES ("SANDUICHES","enum_tipo_item_sanduiches"),
	PROMOCOES ("PROMOCOES","enum_tipo_item_promocoes"),
	ESPECIAIS ("ESPECIAIS","enum_tipo_item_especiais"),
	OUTROS ("OUTROS","enum_tipo_item_outros");

	private final String key;
	private final String desc;

	/**
	 * @param num
	 * @param desc
	 */
	private ETipoItem(String key, String desc) {
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
		List<ETipoItem> roles  = Arrays.asList(ETipoItem.values());

		for (ETipoItem r : roles) {
			descs.add(r.desc);
		}
		return descs;
	}

	public static String getKey(String desc) {
		List<ETipoItem> roles  = Arrays.asList(ETipoItem.values());

		for (ETipoItem r : roles) {
			if(r.desc.equals(desc)) {
				return r.key;
			}
		}
		return "";
	}

	public static String getDesc(String key) {
		List<ETipoItem> roles  = Arrays.asList(ETipoItem.values());

		for (ETipoItem r : roles) {
			if(r.key.equals(key)) {
				return r.desc;
			}
		}
		return "";
	}

}
