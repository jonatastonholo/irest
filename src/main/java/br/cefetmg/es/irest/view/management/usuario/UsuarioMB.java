package br.cefetmg.es.irest.view.management.usuario;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import br.cefetmg.es.irest.controler.facade.UsuarioFacade;
import br.cefetmg.es.irest.controler.util.CpfUtils;
import br.cefetmg.es.irest.model.entity.Usuario;
import br.cefetmg.es.irest.model.enuns.EUsuarioRole;
import br.cefetmg.es.irest.view.management.util.MessagesUtil;
import br.cefetmg.es.irest.view.util.BaseBean;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@Named(value="usuarioMB")
public class UsuarioMB extends BaseBean{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(UsuarioMB.class);

	@Inject
	private UsuarioFacade usuarioFacade;

	@Inject
	private FacesContext context;

	private List<Usuario> usuarios;

	private List<Usuario> filteredUsuarios;

	private Usuario selectedUser;

	private Integer id;

	private String userRoleDesc;

	private String cpfFormatado;

	private boolean editDisabled = false;

	private String login;

	public UsuarioMB() {
	}

	@PostConstruct
	public void onLoad() {
		this.usuarios = this.usuarioFacade.findAll();
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void delete() {
		if (this.selectedUser != null) {
			this.usuarioFacade.delete(this.selectedUser);
			unselectUsuario();
		}
	}

	public void selectUsuario(SelectEvent evt) {
		try {
			if (evt.getObject() != null) {
				this.selectedUser = (Usuario) evt.getObject();
				this.editDisabled = true;
				this.login = this.selectedUser.getLogin();
			} else {
				this.selectedUser = null;
				this.editDisabled = false;
			}
		} catch (Exception e) {
			this.selectedUser = null;
			this.editDisabled = false;
			logger.error(e.getMessage(), e);
		}
	}

	public void unselectUsuario() {
		this.selectedUser = null;
		this.editDisabled = false;
	}

	public Usuario getSelectedUser() {
		return this.selectedUser;
	}

	public void setSelectedUser(Usuario selectedUser) {
		this.selectedUser = selectedUser;
	}

	/**
	 * @return the userRoleDesc
	 */
	public String getUserRoleDesc() {
		return userRoleDesc;
	}

	/**
	 * Gets the user role desc.
	 *
	 * @param key the role
	 * @return the user role desc
	 */
	public String getUserRoleDesc(String key) {
		return MessagesUtil.getResourceProperty("labels", EUsuarioRole.getDesc(key), this.context);
	}

	/**
	 * @param userRoleDesc the userRoleDesc to set
	 */
	public void setUserRoleDesc(String userRoleDesc) {
		this.userRoleDesc = userRoleDesc;
	}

	/**
	 * @return the cpfFormatado
	 */
	public String getCpfFormatado(String cpf) {
		this.cpfFormatado = CpfUtils.formatCPF(cpf);
		return cpfFormatado;
	}

	/**
	 * @param cpfFormatado the cpfFormatado to set
	 */
	public void setCpfFormatado(String cpfFormatado) {
		this.cpfFormatado = cpfFormatado;
	}

	/**
	 * @return the filteredUsuarios
	 */
	public List<Usuario> getFilteredUsuarios() {
		return filteredUsuarios;
	}

	/**
	 * @param filteredUsuarios the filteredUsuarios to set
	 */
	public void setFilteredUsuarios(List<Usuario> filteredUsuarios) {
		this.filteredUsuarios = filteredUsuarios;
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
}
