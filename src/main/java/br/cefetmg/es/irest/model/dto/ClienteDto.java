package br.cefetmg.es.irest.model.dto;

import java.util.Date;


public class ClienteDto {

	private String nome;

	private String endereco;

	private String telefone;

	private Date nascimento;

	private String email;

	private String login;

	private String cpf;

	private String role;

	/**
	 * @param nome
	 * @param endereco
	 * @param telefone
	 * @param nascimento
	 * @param email
	 * @param login
	 * @param cpf
	 * @param role
	 */
	public ClienteDto(String nome, String endereco, String telefone,
			Date nascimento, String email, String login, String cpf, String role) {
		super();
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
		this.nascimento = nascimento;
		this.email = email;
		this.login = login;
		this.cpf = cpf;
		this.role = role;
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
	 * @return the endereco
	 */
	public String getEndereco() {
		return endereco;
	}

	/**
	 * @param endereco the endereco to set
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
	 * @param telefone the telefone to set
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
	 * @param nascimento the nascimento to set
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
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
