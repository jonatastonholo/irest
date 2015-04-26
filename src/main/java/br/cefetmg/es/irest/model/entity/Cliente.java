package br.cefetmg.es.irest.model.entity;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.cefetmg.es.irest.model.entity.util.BaseEntity;

@Entity
@Table(name = "cliente")
@AttributeOverride(name = "id", column = @Column(name = "id_cliente"))
public class Cliente extends BaseEntity<Integer> {

	private static final long serialVersionUID = 1L;

	@OneToOne
	@JoinColumn(name = "id_usuario")
	Usuario usuario;

	@Column(length = 50, nullable = false)
	private String nome;

	@Column(length = 100, nullable = false)
	private String endereco;

	@Column(length = 20, nullable = false)
	private String telefone;

	@Column
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date nascimento;

	@Column(length = 50)
	private String email;

	public Cliente() {
	}

	/**
	 * @param usuario
	 * @param nome
	 * @param endereco
	 * @param telefone
	 * @param nascimento
	 * @param email
	 */
	public Cliente(Usuario usuario, String nome, String endereco,
			String telefone, Date nascimento, String email) {
		super();
		this.usuario = usuario;
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
		this.nascimento = nascimento;
		this.email = email;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
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
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the endereco
	 */
	public String getEndereco() {
		return endereco;
	}

	/**
	 * @param endereco
	 *            the endereco to set
	 */
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	/**
	 * @return the telefone
	 */
	public String getTelefone() {
		return telefone;
	}

	/**
	 * @param telefone
	 *            the telefone to set
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * @return the nascimento
	 */
	public Date getNascimento() {
		return nascimento;
	}

	/**
	 * @param nascimento
	 *            the nascimento to set
	 */
	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
