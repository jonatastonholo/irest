package br.cefetmg.es.irest.view.management.mesa;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import br.cefetmg.es.irest.controler.facade.MesaFacade;
import br.cefetmg.es.irest.model.entity.Mesa;
import br.cefetmg.es.irest.view.util.BaseBean;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@Named(value="mesaMB")
public class MesaMB extends BaseBean{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(MesaMB.class);

	@Inject
	private MesaFacade mesaFacade;

	private List<Mesa> mesas;

	private List<Mesa> filteredMesas;

	private Mesa selectedMesa;

	private Integer id;

	private boolean editDisabled = false;

	private String ipTerminal;

	public MesaMB() {
	}

	@PostConstruct
	public void onLoad() {
		this.mesas = this.mesaFacade.findAll();
	}

	public List<Mesa> getMesas() {
		return this.mesas;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void delete() {
		if (this.selectedMesa != null) {
			this.mesaFacade.delete(this.selectedMesa);
			unselectMesa();
		}
	}


	public void selectMesa(SelectEvent evt) {
		try {
			if (evt.getObject() != null) {
				this.selectedMesa = (Mesa) evt.getObject();
				this.editDisabled = true;
				this.ipTerminal = this.selectedMesa.getIpTerminal();
			} else {
				this.selectedMesa = null;
				this.editDisabled = false;
			}
		} catch (Exception e) {
			this.selectedMesa = null;
			this.editDisabled = false;
			logger.error(e.getMessage(), e);
		}
	}

	public void unselectMesa() {
		this.selectedMesa = null;
		this.editDisabled = false;
	}

	public Mesa getSelectedMesa() {
		return this.selectedMesa;
	}

	public void setSelectedMesa(Mesa selectedMesa) {
		this.selectedMesa = selectedMesa;
	}


	public List<Mesa> getFilteredMesas() {
		return filteredMesas;
	}

	public void setFilteredMesas(List<Mesa> filteredMesas) {
		this.filteredMesas = filteredMesas;
	}

	public boolean isEditDisabled() {
		return editDisabled;
	}

	public void setEditDisabled(boolean editDisabled) {
		this.editDisabled = editDisabled;
	}

	public String getIpTerminal() {
		return ipTerminal;
	}

	public void setIpTerminal(String ipTerminal) {
		this.ipTerminal = ipTerminal;
	}

	public void setMesas(List<Mesa> mesas) {
		this.mesas = mesas;
	}

}
