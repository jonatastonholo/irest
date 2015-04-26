package br.cefetmg.es.irest.controler.exception;

public class AtendimentoException extends Exception{

	private static final long serialVersionUID = 1L;


	/** The mensagem. */
	private String mensagem;

	/**
	 * Utilizar "name" do labels_pt.properties
	 *
	 * @param message the message
	 */
	public AtendimentoException(String message) {
		super(message);
		this.mensagem = message;
	}

	/**
	 * Gets the mensagem.
	 *
	 * @return the mensagem
	 */
	public String getMensagem() {
		return mensagem;
	}

	/**
	 * Sets the mensagem.
	 *
	 * @param mensagem the mensagem to set
	 */
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
