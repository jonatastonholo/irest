package br.cefetmg.es.irest.controler.facade;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import br.cefetmg.es.irest.controler.exception.AtendimentoException;
import br.cefetmg.es.irest.controler.exception.NegocioException;
import br.cefetmg.es.irest.controler.facade.util.IBaseFacede;
import br.cefetmg.es.irest.model.entity.Atendimento;
import br.cefetmg.es.irest.model.entity.Item;
import br.cefetmg.es.irest.model.entity.ItemPedido;
import br.cefetmg.es.irest.model.entity.Mesa;
import br.cefetmg.es.irest.model.enuns.EFormaPagamento;
import br.cefetmg.es.irest.model.enuns.EStatusPedido;
import br.cefetmg.es.irest.model.repositories.IAtendimentoRepository;
import br.cefetmg.es.irest.model.repositories.IItemPedidoRepository;
import br.cefetmg.es.irest.model.repositories.IItemRepository;
import br.cefetmg.es.irest.model.repositories.IMesaRepository;

/**
 * The Class AtendimentoFacade.
 */
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@Named(value = "atendimentoFacade")
public class AtendimentoFacade extends IBaseFacede<Atendimento> {

	private static final long serialVersionUID = 1L;

	@Inject
	private IAtendimentoRepository atendimentoRepository;

	@Inject
	private IItemRepository itemRepository;

	@Inject
	private IItemPedidoRepository itemPedidoRepository;

	@Inject
	private IMesaRepository mesaRepository;

	@Inject
	FacesContext context;

	private List<Atendimento> atendimentos;

	public AtendimentoFacade() {
	}

	@Override
	protected Atendimento update(Atendimento atendimento) throws NegocioException {
		return null;
	}

	@Override
	public List<Atendimento> findAll() {
		this.atendimentos = this.atendimentoRepository.findAll();
		return this.atendimentos;
	}

	@Override
	public Atendimento save(Atendimento atendimento) throws NegocioException {
		validate(atendimento);
		atendimento = atendimentoRepository.save(atendimento);
		try {
			validateItens(atendimento);
			return atendimentoRepository.save(atendimento);
		} catch (AtendimentoException e) {
			revert(atendimento);
			throw new NegocioException(e.getMensagem());
		} catch (Exception e) {
			revert(atendimento);
			e.printStackTrace();
		}
		return null;
	}

	private void revert(Atendimento atendimento) {
		List<ItemPedido> itens = itemPedidoRepository.findByAtendimento(atendimento);
		for(ItemPedido i : itens) {
			itemPedidoRepository.delete(i);
		}
		atendimento.setItensPedido(null);
		atendimentoRepository.delete(atendimento);
	}



	@Override
	protected void delete(Atendimento atendimento) {
	}

	@Override
	protected void validate(Atendimento atendimento) throws NegocioException {
		if(atendimento.getItensPedido() != null && !atendimento.getItensPedido().isEmpty()) {
		} else throw new NegocioException("msg_item_vazio");

		if(!EFormaPagamento.contains(atendimento.getFormaPagamento()) && atendimento.getId() != null) {
			throw new NegocioException("msg_forma_pagamento_invalida");
		}

		if(atendimento.getMesa() != null && atendimento.getMesa().getId() != null) {
		} else throw new NegocioException("msg_mesa_invalida");
	}

	private void validateItens(Atendimento atendimento) throws NegocioException, AtendimentoException {
		List<ItemPedido> itensPedido = atendimento.getItensPedido();
		List<ItemPedido> tmpList = new ArrayList<ItemPedido>();
		for(int i=0; i<itensPedido.size(); i++) {
			ItemPedido itemPedido = itensPedido.get(i);
			validateItemPedido(itemPedido);
			itemPedido.setAtendimento(atendimento);
			itemPedidoRepository.save(itemPedido);
			tmpList.add(itemPedido);
		}
		atendimento.setItensPedido(tmpList);
	}

	private void validateItemPedido(ItemPedido itemPedido)  throws AtendimentoException {
		if(itemPedido.getItem() != null && itemPedido.getItem().getId() != null) {
		} else throw new AtendimentoException("msg_item_vazio");

		if(itemPedido.getQuantidade() <=0) {
			throw new AtendimentoException("msg_quantidade_invalida");
		}

		if(itemPedido.getStatusPedido() != null && itemPedido.getStatusPedido().equals(EStatusPedido.SOLICITADO_CLIENTE.getKey())) {
		} else throw new AtendimentoException("msg_status_pedido_invalido");

		if(itemPedido.getTotal() != null && itemPedido.getTotal().doubleValue() > 0) {
		} else throw new AtendimentoException("msg_total_pedido_invalido");
	}

	public Atendimento findAtendimentoByMesaIpTerminal(String ipTerminal) {
		return this.atendimentoRepository.findAtendimentoByMesaIpTerminal(ipTerminal);
	}

	public List<Item> findItensByType(String type) {
		return this.itemRepository.findItemByTipoItem(type);
	}

	public Mesa findMesaByIpTerminal(String ipTerminal) {
		return this.mesaRepository.findByIpTerminal(ipTerminal);
	}
}
