package br.cefetmg.es.irest.model.entity;

import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

import br.cefetmg.es.irest.model.entity.util.BaseEntity;


@Entity
@Table(name = "item_pedido")
@AttributeOverride(name = "id", column = @Column(name = "id_item_pedido"))
public class ItemPedido extends BaseEntity<Integer> {

	private static final long serialVersionUID = 1L;

	@OneToOne
	@JoinColumn(name="id_item", nullable=false)
	private Item item;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_atendimento")
	private Atendimento atendimento;

	@Column(nullable=false)
	private Integer quantidade;

	@Column(name="status_pedido", nullable=false)
	String statusPedido;

	@Transient
	private BigDecimal total;

	public ItemPedido() {

	}

	/**
	 * @param item
	 * @param atendimento
	 * @param quantidade
	 * @param statusPedido
	 */
	public ItemPedido(Item item, Atendimento atendimento, Integer quantidade,
			String statusPedido) {
		super();
		this.item = item;
		this.atendimento = atendimento;
		this.quantidade = quantidade;
		this.statusPedido = statusPedido;
	}

	/**
	 * @return the quantidade
	 */
	public Integer getQuantidade() {
		if(quantidade == null){
			quantidade = 0;
		}

		return quantidade;
	}

	/**
	 * @param quantidade the quantidade to set
	 */
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	/**
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	/**
	 * @return the atendimento
	 */
	public Atendimento getAtendimento() {
		return atendimento;
	}

	/**
	 * @param atendimento the atendimento to set
	 */
	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}

	/**
	 * @return the total
	 */
	public BigDecimal getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	/**
	 * @return the statusPedido
	 */
	public String getStatusPedido() {
		return statusPedido;
	}

	/**
	 * @param statusPedido the statusPedido to set
	 */
	public void setStatusPedido(String statusPedido) {
		this.statusPedido = statusPedido;
	}
}
