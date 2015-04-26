package br.cefetmg.es.irest.model.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.cefetmg.es.irest.model.entity.util.BaseEntity;

@Entity
@Table(name = "mesa")
@AttributeOverride(name = "id", column = @Column(name = "id_mesa"))
public class Mesa extends BaseEntity<Integer>{

	private static final long serialVersionUID = 1L;

	@Column(name="ip_terminal", length = 15, nullable = false, unique = true)
	private String ipTerminal;

	@Column(nullable = false)
	private Integer capacidade;

	@Column(name="estado_mesa", length = 8, nullable = false)
	private String estadoMesa;


	/**
	 * @return the ipTerminal
	 */
	public String getIpTerminal() {
		return ipTerminal;
	}

	/**
	 * @param ipTerminal the ipTerminal to set
	 */
	public void setIpTerminal(String ipTerminal) {
		this.ipTerminal = ipTerminal;
	}

	/**
	 * @return the estadoMesa
	 */
	public String getEstadoMesa() {
		return estadoMesa;
	}

	/**
	 * @param estadoMesa the estadoMesa to set
	 */
	public void setEstadoMesa(String estadoMesa) {
		this.estadoMesa = estadoMesa;
	}

	/**
	 * @return the capacidade
	 */
	public Integer getCapacidade() {
		return capacidade;
	}

	/**
	 * @param capacidade the capacidade to set
	 */
	public void setCapacidade(Integer capacidade) {
		this.capacidade = capacidade;
	}

}
