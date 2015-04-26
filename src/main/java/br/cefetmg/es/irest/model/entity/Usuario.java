package br.cefetmg.es.irest.model.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.cefetmg.es.irest.model.entity.util.BaseEntity;

@Entity
@Table(name = "usuario")
@AttributeOverride(name = "id", column = @Column(name = "id_usuario"))
public class Usuario extends BaseEntity<Integer>{
	private static final long serialVersionUID = 1L;

	@Column(length=50, nullable=false, unique = true)
	private String login;

	@Column(length=128, nullable=false)
	private String senha;

	@Column(length=11, nullable=false, unique = true)
	private String cpf;

	@Column(length=16, nullable=false)
	private String role;


	/**
	 * Instantiates a new usuario.
	 */
	public Usuario() {
	}

	/**
	 * Instantiates a new usuario.
	 *
	 * @param login the login
	 * @param senha the senha
	 * @param cpf the cpf
	 */
	public Usuario(String login, String senha, String cpf) {
		super();
		this.login = login;
		this.senha = senha;
		this.cpf = cpf;
	}


	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}


	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}


	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}


	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}


	/**
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}


	/**
	 * @param cpf the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
}
