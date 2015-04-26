package br.cefetmg.es.irest.view.management.mesa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import br.cefetmg.es.irest.controler.exception.NegocioException;
import br.cefetmg.es.irest.controler.facade.MesaFacade;
import br.cefetmg.es.irest.model.entity.Mesa;
import br.cefetmg.es.irest.model.enuns.ECapacidadeMesa;
import br.cefetmg.es.irest.model.enuns.EStatusMesa;
import br.cefetmg.es.irest.view.management.util.MessagesUtil;
import br.cefetmg.es.irest.view.util.BaseAddEditBean;
import br.cefetmg.es.irest.view.util.PageUtils;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@Named(value = "mesaAddEditMB")
public class MesaAddEditMB extends BaseAddEditBean{

	private static final long serialVersionUID = 1L;

	@Inject
	private MesaFacade mesaFacade;

	@Autowired
	private MesaMB mesaMB;

	private Mesa mesa;

	private String title;

	private String messageError;

	private Integer id;

	private String ipTerminal;

	private Integer capacidade;

	private String estadoMesa;

	private boolean editOp = false;

	@SuppressWarnings("unused")
	private List<String> capacidades;

	private Map<String,String> capacidadeConverter;

	private String capacidadeDesc;

	@SuppressWarnings("unused")
	private List<String> estados;

	private Map<String,String> estadoConverter;

	private String estadoDesc;

	public MesaAddEditMB() {
		this.mesa = new Mesa();
	}

	@Override
	public void add() {
		this.title = MessagesUtil.getResourceProperty("labels", "table_add", this.getFacesConext());
	}

	@Override
	public void update() {
		this.title = MessagesUtil.getResourceProperty("labels", "table_update", this.getFacesConext());
		this.mesa = this.mesaMB.getSelectedMesa();
		editOp=true;
		forgeCampos();
	}

	@Override
	protected void forgeCampos() {
		this.id = this.mesa.getId();
		this.ipTerminal = this.mesa.getIpTerminal();
		this.capacidade = this.mesa.getCapacidade();
		this.estadoMesa = this.mesa.getEstadoMesa();
		this.capacidadeDesc = mesa.getCapacidade().toString();
		this.estadoMesa = MessagesUtil.getResourceProperty("labels", EStatusMesa.getDesc(mesa.getEstadoMesa()),getFacesConext());

	}

	@Override
	protected void limpaCampos() {
		this.id = null;
		this.ipTerminal = "";
		this.capacidade = null;
		this.estadoMesa = "";
	}

	@Override
	protected void forgeEntity () {
		this.mesa.setId(id);
		this.mesa.setIpTerminal(ipTerminal);
	}

	@Override
	public void cancel() {
		redirect();
	}

	@Override
	public void save() {
		forgeEntity();
		if (this.mesa != null) {
			updateCapacidade();
			if (this.mesa.getId() == null) {
				// Add
				this.mesa.setEstadoMesa(EStatusMesa.LIVRE.getKey());
					try {
						this.mesaFacade.save(this.mesa);
						redirect();
					} catch (NegocioException e) {
						setMessageError(e.getMensagem());
					}
			} else {
				// Update
				try {
					updateEstadoMesa();
					this.mesaFacade.save(this.mesa);
					redirect();
				} catch (NegocioException e) {
					setMessageError(e.getMensagem());
				}
			}
		}
	}

	private void updateCapacidade () {
		if(capacidadeDesc != null && !capacidadeDesc.equals("")) {
			if(!capacidadeDesc.equals("")) {
				this.mesa.setCapacidade(Integer.parseInt(capacidadeDesc));
			}
		} else if (capacidade != null){
			this.mesa.setCapacidade(capacidade);
		} else if(this.mesa.getCapacidade() == null && capacidadeDesc != null) {
			if(!capacidadeDesc.equals("")) {
				this.mesa.setCapacidade(Integer.parseInt(capacidadeDesc));
			}
		} else {
			this.mesa.setCapacidade(capacidade);
		}
	}

	private void updateEstadoMesa () {
		if(estadoDesc != null && !estadoDesc.equals("")) {
			this.mesa.setEstadoMesa(EStatusMesa.getKey(estadoConverter.get(estadoDesc)));
		} else if (estadoMesa != null && !estadoMesa.equals("")){
			this.mesa.setEstadoMesa(EStatusMesa.getKey(estadoConverter.get(estadoMesa)));
		} else if(this.mesa.getEstadoMesa() == null && estadoDesc != null) {
			this.mesa.setEstadoMesa(EStatusMesa.getKey(capacidadeConverter.get(estadoDesc)));
		} else {
			this.mesa.setEstadoMesa(EStatusMesa.LIVRE.getKey());
		}
	}

	public void selectCapacidade(AjaxBehaviorEvent evt) {
		try {
			if (!evt.getComponent().getAttributes().get("value").toString().trim().equalsIgnoreCase("")) {
				this.capacidade = (Integer) Integer.parseInt(evt.getComponent().getAttributes().get("value").toString());
			} else {
				this.capacidade = null;
			}
		} catch (Exception e) {
			this.capacidade = null;
			this.capacidade = null;
		}
	}

	public void selectEstadoMesa(AjaxBehaviorEvent evt) {
		try {
			if (!evt.getComponent().getAttributes().get("value").toString().trim().equalsIgnoreCase("")) {
				this.estadoMesa = (String) evt.getComponent().getAttributes().get("value").toString();
			} else {
				this.estadoMesa = null;
			}
		} catch (Exception e) {
			this.estadoMesa = null;
			this.estadoMesa = null;
		}
	}

	@Override
	protected void redirect() {
		PageUtils.redirect(getFacesConext(), "/pages/mesa/list.faces");
		if (this.mesaMB != null) {
			this.mesaMB.unselectMesa();
		}
	}

	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}

	public void setMessageError(String msgError) {
		this.messageError = MessagesUtil.addMessage(msgError, getFacesConext());
	}

	public String getMessageError() {
		return this.messageError;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEstadoMesa() {
		return estadoMesa;
	}

	public void setEstadoMesa(String estadoMesa) {
		this.estadoMesa = estadoMesa;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIpTerminal() {
		return ipTerminal;
	}

	public void setIpTerminal(String ipTerminal) {
		this.ipTerminal = ipTerminal;
	}

	public Integer getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(Integer capacidade) {
		this.capacidade = capacidade;
	}

	public List<String> getCapacidades() {
		List<String> descs = new ArrayList<String>();
		capacidadeConverter = new HashMap<String, String>();

		for(String s : ECapacidadeMesa.descs()) {
			String key = s;
			descs.add(key);
			capacidadeConverter.put(key, s);
		}
		return descs;
	}

	public String getCapacidadeDesc() {
		return this.capacidadeDesc;
	}

	public void setCapacidadeDesc(String capacidadeDesc) {
		this.capacidadeDesc = capacidadeDesc;
	}

	/**
	 * @return the estados
	 */
	public List<String> getEstados() {
		List<String> descs = new ArrayList<String>();
		estadoConverter = new HashMap<String, String>();

		for(String s : EStatusMesa.descs()) {
			String key = MessagesUtil.getResourceProperty("labels", s, this.getFacesConext());;
			descs.add(key);
			estadoConverter.put(key, s);
		}
		return descs;
	}

	/**
	 * @param estados the estados to set
	 */
	public void setEstados(List<String> estados) {
		this.estados = estados;
	}

	/**
	 * @return the estadoDesc
	 */
	public String getEstadoDesc() {
		if(isEditOp()) {
			this.estadoDesc = MessagesUtil.getResourceProperty("labels", EStatusMesa.getDesc(mesa.getEstadoMesa()), this.getFacesConext());
		}
		return estadoDesc;
	}

	/**
	 * @param estadoDesc the estadoDesc to set
	 */
	public void setEstadoDesc(String estadoDesc) {
		this.estadoDesc = estadoDesc;
	}

	/**
	 * @param capacidades the capacidades to set
	 */
	public void setCapacidades(List<String> capacidades) {
		this.capacidades = capacidades;
	}

	/**
	 * @return the editOp
	 */
	public boolean isEditOp() {
		editOp = mesa.getId() != null || getId() != null;
		return editOp;
	}

	/**
	 * @param editOp the editOp to set
	 */
	public void setEditOp(boolean editOp) {
		this.editOp = editOp;
	}
}
