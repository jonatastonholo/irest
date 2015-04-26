package br.cefetmg.es.irest.model.entity;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.cefetmg.es.irest.model.entity.util.BaseEntity;
@Entity
@Table(name = "atendimento")
@AttributeOverride(name = "id", column = @Column(name = "id_atendimento"))
public class Atendimento extends BaseEntity<Integer>{

	private static final long serialVersionUID = 1L;

	@OneToOne
	@JoinColumn(name="id_mesa", nullable=false)
	private Mesa mesa;

	@OneToMany(mappedBy="atendimento", fetch=FetchType.EAGER)
	private List<ItemPedido> itensPedido;

	@Column(nullable=false)
	private boolean finalizado;

	@Column(name="forma_pagamento")
	private String formaPagamento;

	public Atendimento() {
	}

	/**
	 * @param mesa
	 * @param itemPedido
	 * @param finalizado
	 * @param formaPagamento
	 */
	public Atendimento(Mesa mesa, List<ItemPedido> itensPedido,
			boolean finalizado, String formaPagamento) {
		super();
		this.mesa = mesa;
		this.itensPedido = itensPedido;
		this.finalizado = finalizado;
		this.formaPagamento = formaPagamento;
	}

	/**
	 * @return the mesa
	 */
	public Mesa getMesa() {
		return mesa;
	}

	/**
	 * @param mesa the mesa to set
	 */
	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}

	/**
	 * @return the itemPedido
	 */
	public List<ItemPedido> getItensPedido() {
		return itensPedido;
	}

	/**
	 * @param itensPedido the itemPedido to set
	 */
	public void setItensPedido(List<ItemPedido> itensPedido) {
		this.itensPedido = itensPedido;
	}

	/**
	 * @return the finalizado
	 */
	public boolean isFinalizado() {
		return finalizado;
	}

	/**
	 * @param finalizado the finalizado to set
	 */
	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}

	/**
	 * @return the formaPagamento
	 */
	public String getFormaPagamento() {
		return formaPagamento;
	}

	/**
	 * @param formaPagamento the formaPagamento to set
	 */
	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
}