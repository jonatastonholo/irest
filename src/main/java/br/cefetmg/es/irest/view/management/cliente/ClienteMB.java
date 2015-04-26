package br.cefetmg.es.irest.view.management.cliente;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import br.cefetmg.es.irest.controler.facade.ClienteFacade;
import br.cefetmg.es.irest.controler.util.CpfUtils;
import br.cefetmg.es.irest.model.entity.Cliente;
import br.cefetmg.es.irest.model.entity.Usuario;
import br.cefetmg.es.irest.view.util.BaseBean;
import br.cefetmg.es.irest.view.util.PageUtils;

@Scope(value = WebApplicationContext.SCOPE_SESSION)
@Named(value="clienteMB")
public class ClienteMB extends BaseBean{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(ClienteMB.class);

	@Inject
	private ClienteFacade clienteFacade;

	@Inject
	private FacesContext context;

	private List<Cliente> clientes;

	private List<Cliente> filteredClientes;

	private Cliente selectedCliente;

	private Integer id;

	private boolean editDisabled = false;

	private String nome;

	private String cpfFormatado;

	private String login;


	public ClienteMB() {
	}

	@PostConstruct
	public void onLoad() {
		this.clientes = this.clienteFacade.findAll();
	}

	public List<Cliente> getClientes() {
		return this.clientes;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void delete() {
		if (this.selectedCliente != null) {
			this.clienteFacade.delete(this.selectedCliente);
			unselectCliente();
		}
	}

	public void onNewClick() {
		redirect();
	}

	public void onUpdateClick() {

	}

	protected void redirect() {
			PageUtils.redirect(this.context, "/pages/cliente/addEdit.faces");
	}


	public void selectCliente(SelectEvent evt) {
		try {
			if (evt.getObject() != null) {
				this.selectedCliente = (Cliente) evt.getObject();
				this.editDisabled = true;
				this.nome = this.selectedCliente.getNome();
			} else {
				this.selectedCliente = null;
				this.editDisabled = false;
			}
		} catch (Exception e) {
			this.selectedCliente = null;
			this.editDisabled = false;
			logger.error(e.getMessage(), e);
		}
	}

	public void unselectCliente() {
		this.selectedCliente = null;
		this.editDisabled = false;
	}

	public Cliente getSelectedCliente() {
		return this.selectedCliente;
	}

	public void setSelectedCliente(Cliente selectedCliente) {
		this.selectedCliente = selectedCliente;
	}


	/**
	 * @return the filteredClientes
	 */
	public List<Cliente> getFilteredClientes() {
		return filteredClientes;
	}

	/**
	 * @param filteredClientes the filteredClientes to set
	 */
	public void setFilteredClientes(List<Cliente> filteredClientes) {
		this.filteredClientes = filteredClientes;
	}

	/**
	 * @return the editDisabled
	 */
	public boolean isEditDisabled() {
		return editDisabled;
	}

	/**
	 * @param editDisabled the editDisabled to set
	 */
	public void setEditDisabled(boolean editDisabled) {
		this.editDisabled = editDisabled;
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

	public String getCpfFormatado(String cpf) {
		this.cpfFormatado = CpfUtils.formatCPF(cpf);
		return cpfFormatado;
	}

	public void setCpfFormatado(String cpfFormatado) {
		this.cpfFormatado = cpfFormatado;
	}

	public Cliente getClienteByUsuario(Usuario usuario) {
		return clienteFacade.getClienteByUsuario(usuario);
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
	 * @return the cpfFormatado
	 */
	public String getCpfFormatado() {
		return cpfFormatado;
	}

	/**
	 * @param clientes the clientes to set
	 */
	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	/**
	 * @return the context
	 */
	public FacesContext getContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(FacesContext context) {
		this.context = context;
	}
}
