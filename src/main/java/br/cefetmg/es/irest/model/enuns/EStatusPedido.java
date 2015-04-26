package br.cefetmg.es.irest.model.enuns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum EStatusPedido {
	SOLICITADO_CLIENTE("SOLICITADO_CLIENTE","enum_status_pedido_solicitado_cliente"),
	CANCELADO("CANCELADO","enum_status_pedido_solicitado_cliente"),
	EM_PREPARO("EM_PREPARO","enum_status_pedido_em_preparo"),
	AGUARDANDO_ATENDENTE("AGUARDANDO_ATENDENTE","enum_status_pedido_aguardando_atendente"),
	ENTREGUE_CLIENTE("ENTREGUE_CLIENTE","enum_status_pedido_entregue_cliente");

	private final String key;
	private final String desc;
	/**
	 * @param key
	 * @param desc
	 */
	private EStatusPedido(String key, String desc) {
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
		List<EStatusPedido> funcoes  = Arrays.asList(EStatusPedido.values());

		for (EStatusPedido f : funcoes) {
			descs.add(f.desc);
		}
		return descs;
	}

	public static String getKey(String desc) {
		List<EStatusPedido> funcoes  = Arrays.asList(EStatusPedido.values());

		for (EStatusPedido f : funcoes) {
			if(f.desc.equals(desc)) {
				return f.key;
			}
		}
		return "";
	}

	public static String getDesc(String key) {
		List<EStatusPedido> funcoes  = Arrays.asList(EStatusPedido.values());

		for (EStatusPedido f : funcoes) {
			if(f.key.equals(key)) {
				return f.desc;
			}
		}
		return "";
	}

	public static boolean contains(String key) {
		List<EStatusPedido> funcoes  = Arrays.asList(EStatusPedido.values());

		for (EStatusPedido f : funcoes) {
			if(f.key.equals(key)) {
				return true;
			}
		}
		return false;
	}

}
