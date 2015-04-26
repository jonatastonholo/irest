package br.cefetmg.es.irest.model.entity;

import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

import br.cefetmg.es.irest.model.entity.util.BaseEntity;

@Entity
@Table(name = "item")
@AttributeOverride(name = "id", column = @Column(name = "id_item"))
public class Item extends BaseEntity<Integer>{

	private static final long serialVersionUID = 1L;

	@Column(length = 50, nullable=false, unique=true)
	private String nome;

	@Column(length = 100)
	private String descricao;

	@Column(name="tipo_item", nullable=false)
	private String tipoItem;

	@Column(nullable=false)
	private BigDecimal preco;

	@Column(name="dir_imagem")
	private String dirImagem;

	@Transient
	private Integer quantidade;

	public Item() {
	}

	/**
	 * @param nome
	 * @param descricao
	 * @param tipoItem
	 * @param preco
	 * @param dirImagem
	 */
	public Item(String nome, String descricao, String tipoItem,
			BigDecimal preco, String dirImagem) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.tipoItem = tipoItem;
		this.preco = preco;
		this.dirImagem = dirImagem;
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
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the tipoItem
	 */
	public String getTipoItem() {
		return tipoItem;
	}

	/**
	 * @param tipoItem the tipoItem to set
	 */
	public void setTipoItem(String tipoItem) {
		this.tipoItem = tipoItem;
	}

	/**
	 * @return the preco
	 */
	public BigDecimal getPreco() {
		return preco;
	}

	/**
	 * @param preco the preco to set
	 */
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	/**
	 * @return the dirImagem
	 */
	public String getDirImagem() {
		return dirImagem;
	}

	/**
	 * @param dirImagem the dirImagem to set
	 */
	public void setDirImagem(String dirImagem) {
		this.dirImagem = dirImagem;
	}

	/**
	 * @return the quantidade
	 */
	public Integer getQuantidade() {
		if(quantidade == null) {
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
}
