package br.cefetmg.es.irest.model.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.cefetmg.es.irest.model.entity.util.BaseEntity;


@Entity
@Table(name = "chamado")
@AttributeOverride(name = "id", column = @Column(name = "id_chmado"))
public class Chamado extends BaseEntity<Integer>{
	private static final long serialVersionUID = 1L;

	public Chamado() {
	}

	/**
	 * @param mesa
	 * @param atendido
	 */
	public Chamado(Mesa mesa, boolean atendido) {
		super();
		this.mesa = mesa;
		this.atendido = atendido;
	}

	@OneToOne
	@JoinColumn(name="id_mesa")
	Mesa mesa;

	@Column(nullable=false)
	boolean atendido;

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
	 * @return the atendido
	 */
	public boolean isAtendido() {
		return atendido;
	}

	/**
	 * @param atendido the atendido to set
	 */
	public void setAtendido(boolean atendido) {
		this.atendido = atendido;
	}
}
