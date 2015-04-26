package br.cefetmg.es.irest.model.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.cefetmg.es.irest.model.entity.util.BaseEntity;

@Entity
@Table(name = "funcionario")
@AttributeOverride(name = "id", column = @Column(name = "id_funcionario"))
public class Funcionario extends BaseEntity<Integer>{

	private static final long serialVersionUID = 1L;

	@OneToOne
	@JoinColumn(name="id_usuario")
	Usuario usuario;

	@Column(length=50, nullable=false)
	private String nome;

	@Column(length=50, nullable=false)
	private String funcao;

	public Funcionario () {}

	/**
	 * @param usuario
	 * @param nome
	 * @param funcao
	 */
	public Funcionario(Usuario usuario, String nome, String funcao) {
		super();
		this.usuario = usuario;
		this.nome = nome;
		this.funcao = funcao;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the funcao
	 */
	public String getFuncao() {
		return funcao;
	}

	/**
	 * @param funcao the funcao to set
	 */
	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

}
