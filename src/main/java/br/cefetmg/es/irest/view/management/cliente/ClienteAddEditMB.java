package br.cefetmg.es.irest.view.management.cliente;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import br.cefetmg.es.irest.controler.exception.NegocioException;
import br.cefetmg.es.irest.controler.facade.ClienteFacade;
import br.cefetmg.es.irest.model.entity.Cliente;
import br.cefetmg.es.irest.model.entity.Usuario;
import br.cefetmg.es.irest.view.management.util.MessagesUtil;
import br.cefetmg.es.irest.view.util.BaseAddEditBean;
import br.cefetmg.es.irest.view.util.PageUtils;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@Named(value = "clienteAddEditMB")
public class ClienteAddEditMB extends BaseAddEditBean{

	private static final long serialVersionUID = 1L;

	@Inject
	private ClienteFacade clienteFacade;

	@Autowired
	private ClienteMB clienteMB;

	private Cliente cliente;

	private String title;

	private String messageError;

	private Integer id;

	private String nome;

	private String endereco;

	private Date nascimento;

	private String telefone;

	private String email;

	private String cpf;

	private String tmpCpf;

	private boolean addOperation = false;

	private boolean disableCpfEdit = false;

	public ClienteAddEditMB() {
		this.cliente = new Cliente();
		this.addOperation = true;
	}

	@Override
	public void add() {
		this.title = MessagesUtil.getResourceProperty("labels", "costumer_add", this.getFacesConext());
		this.addOperation = true;
		limpaCampos();
	}

	@Override
	public void update() {
		this.title = MessagesUtil.getResourceProperty("labels", "costumer_update", this.getFacesConext());
		this.addOperation = false;
		if(!isRoleAdmin()) {
			this.disableCpfEdit = true;
		}
		if(!isRoleAdmin()) {
			this.cliente = getClienteLogado();
		} else {
			this.cliente = this.clienteMB.getSelectedCliente();
		}
		forgeCampos();
	}

	@Override
	protected void forgeCampos() {
		if(this.cliente == null) {
			this.cliente = getClienteLogado();
		}
		this.id = this.cliente.getId();
		this.nome = this.cliente.getNome();
		this.endereco = this.cliente.getEndereco();
		this.telefone = this.cliente.getTelefone();
		this.nascimento = this.cliente.getNascimento();
		this.email = this.cliente.getEmail();
		if(this.cliente.getUsuario() != null) {
			this.tmpCpf = this.cliente.getUsuario().getCpf();
			this.cpf = this.cliente.getUsuario().getCpf();
		}
	}

	@Override
	protected void limpaCampos() {
		this.id = null;
		this.nome = "";
		this.endereco = "";
		this.telefone = "";
		this.nascimento = null;
		this.email = "";
		this.tmpCpf = "";
		this.cpf = "";
		cliente.setUsuario(null);
	}

	@Override
	protected void forgeEntity () {
		this.cliente.setId(id);
		this.cliente.setNome(nome);
		this.cliente.setEndereco(endereco);
		this.cliente.setTelefone(telefone);
		this.cliente.setNascimento(nascimento);
		this.cliente.setEmail(email);
		buscarUsuarioCpf();
	}

	@Override
	public void cancel() {
		redirect();
	}

	@Override
	public void save() {
		forgeEntity();
		if (this.cliente != null) {
			if (this.cliente.getId() == null) {
				if(cliente.getUsuario() != null) {
					// Add
					try {
						this.clienteFacade.save(this.cliente);
						redirect();
					} catch (NegocioException e) {
						setMessageError(e.getMensagem());
					}
				} else {
					setMessageError("msg_user_not_found");
				}
			} else {
				// Update
				try {
					this.clienteFacade.save(this.cliente);
					redirect();
				} catch (NegocioException e) {
					setMessageError(e.getMensagem());
				}
			}
		}
	}

	@Override
	protected void redirect() {
		if(isRoleAdmin()) {
			PageUtils.redirect(getFacesConext(), "/pages/cliente/list.faces");
			if(this.clienteMB != null) {
				this.clienteMB.unselectCliente();
			}
		} else {
			PageUtils.redirect(getFacesConext(), "/pages/main.faces");
		}
	}

	/**
	 * @return the cliente
	 */
	public Cliente getCliente() {
		if((!isAddOperation())&& (cliente == null || cliente.getNome() == null)) {
			this.cliente = getClienteLogado();
		}

		if(isAdminEditOperation()) {
			this.cliente = this.clienteMB.getSelectedCliente();
		}

		if(cliente!=null && cliente.getNome() != null) {
			this.cpf = cliente.getUsuario().getCpf();
		}

		return cliente;
	}

	private Cliente getClienteLogado() {
		Usuario u = webContextHolder.getUsuarioLogado();
		return clienteFacade.getClienteByUsuario(u);
	}


	/**
	 * @param cliente the cliente to set
	 */
	public void setUsuario(Cliente cliente) {
		this.cliente = cliente;
	}

	private boolean isAdminEditOperation() {
		boolean editOperation =
					isRoleAdmin()
					&& this.clienteMB.getSelectedCliente() != null;

		return editOperation;
	}

	private boolean isAddOperation() {
		addOperation =
				isRoleAdmin()
					&& this.clienteMB.getSelectedCliente() == null;

		return addOperation;
	}

	public void buscarUsuarioCpf() {
		Usuario u = clienteFacade.findByCpf(cpf);
		if(u!=null) {

			if(tmpCpf != null && u.getCpf() != null) {
				if(tmpCpf.equals(u.getCpf())) {
					cliente.setUsuario(u);
				} else {
					// verifica se o novo cpf possui vinculo
					if(!this.clienteFacade.hasDependencia(u,this.cliente)) {
						cliente.setUsuario(u);
					} else {
						setMessageError("msg_duplicated_costumer_user");
					}
				}
			}
		}
	}

	/**
	 * @return the disableCpfEdit
	 */
	public boolean isDisableCpfEdit() {
		if(!isRoleAdmin()) {
			this.disableCpfEdit = true;
		}
		return this.disableCpfEdit;
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
	 * @param disableCpfEdit the disableCpfEdit to set
	 */
	public void setDisableCpfEdit(boolean disableCpfEdit) {
		this.disableCpfEdit = disableCpfEdit;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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
	 * @return the tmpCpf
	 */
	public String getTmpCpf() {
		return tmpCpf;
	}

	/**
	 * @param tmpCpf the tmpCpf to set
	 */
	public void setTmpCpf(String tmpCpf) {
		this.tmpCpf = tmpCpf;
	}
}
