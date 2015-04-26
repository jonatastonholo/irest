package br.cefetmg.es.irest.view.management;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import br.cefetmg.es.irest.controler.exception.NegocioException;
import br.cefetmg.es.irest.controler.facade.ChamadoFacade;
import br.cefetmg.es.irest.model.entity.Chamado;
import br.cefetmg.es.irest.view.management.util.MessagesUtil;
import br.cefetmg.es.irest.view.util.BaseBean;
import br.cefetmg.es.irest.view.util.WebContextHolder;

@Scope(value = WebApplicationContext.SCOPE_SESSION)
@Named(value="chamadoMB")
public class ChamadoMB extends BaseBean{
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(ChamadoMB.class);

	@Inject
	private ChamadoFacade chamadoFacade;

	@Inject
	WebContextHolder contextHolder;

	private Chamado chamado;

	private Chamado selectedChamado;

	private List<Chamado> chamados;

	private List<Chamado> filteredChamados;

	private boolean atendido;

	private String ipTerminal;

	private String message;

	public ChamadoMB() {
	}

	@PostConstruct
	public void onLoad() {
		clear();
		this.chamados = this.chamadoFacade.findAll();
	}

	private void clear() {
		chamado = new Chamado();
		atendido = false;
		ipTerminal = null;
		selectedChamado = null;
	}

	public void update() {
		if(selectedChamado != null) {
			try {
				chamadoFacade.update(selectedChamado);
				clear();
			} catch (NegocioException e) {
				MessagesUtil.addMessage(e.getMensagem(), FacesContext.getCurrentInstance());
			}
		}
	}

	public void save() {
		if(chamado != null) {
			chamado.setAtendido(false);
			if(ipTerminal != null && !ipTerminal.trim().equals("")) {
				chamado.setMesa(chamadoFacade.findMesaByIpTerminal(ipTerminal));
			}
			try {
				chamadoFacade.save(chamado);
				setMessage("msg_chamado_realizado");
				clear();
			} catch (NegocioException e) {
				setMessage(e.getMensagem());
			}
		}
	}

	public void setMessage(String msgError) {
		this.message = MessagesUtil.getResourceProperty("labels", msgError, FacesContext.getCurrentInstance());
		FacesMessage fMsg = new FacesMessage(this.message, "");
        FacesContext.getCurrentInstance().addMessage(null, fMsg);
	}

	public void selectChamado(SelectEvent evt) {
		try {
			if (evt.getObject() != null) {
				this.selectedChamado = (Chamado) evt.getObject();
			} else {
				this.selectedChamado = null;
			}
		} catch (Exception e) {
			this.selectedChamado = null;
			logger.error(e.getMessage(), e);
		}
	}

	public void unselectChamado() {
		this.selectedChamado = null;
	}

	/**
	 * @return the chamado
	 */
	public Chamado getChamado() {
		return chamado;
	}

	/**
	 * @param chamado the chamado to set
	 */
	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}

	/**
	 * @return the chamados
	 */
	public List<Chamado> getChamados() {
		return chamados;
	}

	/**
	 * @param chamados the chamados to set
	 */
	public void setChamados(List<Chamado> chamados) {
		this.chamados = chamados;
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
	 * @return the selectedChamado
	 */
	public Chamado getSelectedChamado() {
		return selectedChamado;
	}

	/**
	 * @param selectedChamado the selectedChamado to set
	 */
	public void setSelectedChamado(Chamado selectedChamado) {
		this.selectedChamado = selectedChamado;
	}

	/**
	 * @return the filteredChamados
	 */
	public List<Chamado> getFilteredChamados() {
		return filteredChamados;
	}

	/**
	 * @param filteredChamados the filteredChamados to set
	 */
	public void setFilteredChamados(List<Chamado> filteredChamados) {
		this.filteredChamados = filteredChamados;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}


}
