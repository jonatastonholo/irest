package br.cefetmg.es.irest.view.management.usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import br.cefetmg.es.irest.controler.exception.NegocioException;
import br.cefetmg.es.irest.controler.facade.UsuarioFacade;
import br.cefetmg.es.irest.model.entity.Usuario;
import br.cefetmg.es.irest.model.enuns.EUsuarioRole;
import br.cefetmg.es.irest.view.management.util.MessagesUtil;
import br.cefetmg.es.irest.view.util.BaseBean;
import br.cefetmg.es.irest.view.util.PageUtils;
import br.cefetmg.es.irest.view.util.WebContextHolder;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@Named(value = "usuarioAddEditMB")
public class UsuarioAddEditMB extends BaseBean{

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioFacade usuarioFacade;

	@Inject
	private UsuarioMB mbUserBean;

	@Inject
	private FacesContext context;

	@Inject
	private WebContextHolder webContextHolder;


	private Usuario usuario;

	private String title;

	private String messageError;

	private String userRoleDesc;

	private Map<String,String> roleConverter;

	private boolean disableRoleEdit = false;

	private boolean addOperation = false;

	@SuppressWarnings("unused")
	private List<String> roles;

	public UsuarioAddEditMB() {
		this.usuario = new Usuario();
		this.addOperation = true;
	}

	public void add() {
		this.title = MessagesUtil.getResourceProperty("labels", "user_add", this.context);
		this.addOperation = true;
	}

	public void update() {
		addOperation = false;
		if(!webContextHolder.getUserRole().equals(EUsuarioRole.ROLE_ADMIN.getKey())) {
			this.disableRoleEdit = true;
		}
		this.usuario = this.mbUserBean.getSelectedUser();
		this.title = MessagesUtil.getResourceProperty("labels", "user_update", this.context);
		this.userRoleDesc = MessagesUtil.getResourceProperty("labels", EUsuarioRole.getDesc(usuario.getRole()),this.context);
	}

	public void cancel() {
		if(!webContextHolder.getUserRole().equals(EUsuarioRole.ROLE_ADMIN.getKey())) {
			PageUtils.redirect(this.context, "/pages/main.faces");
		}
		else {
			this.mbUserBean.unselectUsuario();
		}
	}

	public void save() {
		if (this.usuario != null) {
			if(this.usuario.getRole() == null && (userRoleDesc == null)) {
				this.userRoleDesc = EUsuarioRole.getDesc(webContextHolder.getUserRole());
				this.usuario.setRole(EUsuarioRole.getKey(roleConverter.get(userRoleDesc)));
			} else if(this.usuario.getRole() == null && userRoleDesc != null) {
				this.usuario.setRole(EUsuarioRole.getKey(roleConverter.get(userRoleDesc)));
			} else if(EUsuarioRole.getDesc(usuario.getRole()).equals("")){ //A role é uma desc
				this.usuario.setRole(EUsuarioRole.getKey(EUsuarioRole.getKey(usuario.getRole())));
			}

			if (this.usuario.getId() == null) {
				// Add
				try {
					this.usuarioFacade.save(this.usuario);
				} catch (NegocioException e) {
					setMessageError(e.getMensagem());
				}
			} else {
				// Update
				try {
					Usuario novo = this.usuarioFacade.save(this.usuario);
					if(!webContextHolder.getUserRole().equals(EUsuarioRole.ROLE_ADMIN.getKey())) {
						webContextHolder.setUserId(novo.getId());
						PageUtils.redirect(this.context, "/pages/main.faces");
					}
				} catch (NegocioException e) {
					setMessageError(e.getMensagem());
				}
			}
		}
	}

	public void setMessageError(String msgError) {
		this.messageError = MessagesUtil.getResourceProperty("labels", msgError, this.context);
		FacesMessage fMsg = new FacesMessage(this.messageError, "");
        FacesContext.getCurrentInstance().addMessage(null, fMsg);
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
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		if(!isAddOperation() && (usuario == null || usuario.getLogin() == null)) {
			this.usuario = webContextHolder.getUsuarioLogado();
		}
		return usuario;
	}


	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * Gets the roles.
	 *
	 * @return the roles
	 */
	public List<String> getRoles() {
		List<String> descs = new ArrayList<String>();
		roleConverter = new HashMap<String, String>();

		for(String s : EUsuarioRole.descs()) {
			String key = MessagesUtil.getResourceProperty("labels", s, this.context);
			descs.add(key);
			roleConverter.put(key, s);
		}
		return descs;
	}

	/**
	 * @return the userRoleDesc
	 */
	public String getUserRoleDesc() {
		if(!isAddOperation() && usuario != null && this.userRoleDesc == null) {
			this.userRoleDesc =  MessagesUtil.getResourceProperty("labels", EUsuarioRole.getDesc(usuario.getRole()), this.context);
		}
		return this.userRoleDesc;
	}

	/**
	 * @param userRoleDesc the userRoleDesc to set
	 */
	public void setUserRoleDesc(String userRoleDesc) {
		this.userRoleDesc = userRoleDesc;
	}

	/**
	 * @return the disableRoleEdit
	 */
	public boolean isDisableRoleEdit() {
		if(!webContextHolder.getUserRole().equals(EUsuarioRole.ROLE_ADMIN.getKey())) {
			this.disableRoleEdit = true;
		}
		return this.disableRoleEdit;
	}

	/**
	 * @param disableRoleEdit the disableRoleEdit to set
	 */
	public void setDisableRoleEdit(boolean disableRoleEdit) {
		this.disableRoleEdit = disableRoleEdit;
	}

	private boolean isAddOperation() {
		addOperation =
				webContextHolder.getUserRole().equals(EUsuarioRole.ROLE_ADMIN.getKey()) && this.mbUserBean.getSelectedUser() == null ;

		return addOperation;
	}

}
