package br.cefetmg.es.irest.view.management.usuario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import br.cefetmg.es.irest.controler.exception.NegocioException;
import br.cefetmg.es.irest.controler.facade.UsuarioFacade;
import br.cefetmg.es.irest.controler.facade.util.NovoClienteFacade;
import br.cefetmg.es.irest.model.entity.Cliente;
import br.cefetmg.es.irest.model.entity.Usuario;
import br.cefetmg.es.irest.model.enuns.EUsuarioRole;
import br.cefetmg.es.irest.view.management.util.MessagesUtil;
import br.cefetmg.es.irest.view.util.BaseBean;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@Named(value = "novoUsuarioAddMB")
public class NovoUsuarioAddMB extends BaseBean{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(UsuarioMB.class);

	@Inject
	private UsuarioFacade usuarioFacade;

	@Inject
	private NovoClienteFacade clienteFacade;

	@Inject
	private FacesContext context;

	private Usuario usuario;

	private Cliente cliente;

	private String title;

	private String messageError;

	private String userRoleDesc;

	private Map<String,String> roleConverter;


	@SuppressWarnings("unused")
	private List<String> roles;

	public NovoUsuarioAddMB() {
		this.usuario = new Usuario();
		this.cliente = new Cliente();
	}

	public void cancel() {
		try {
			String path = this.context.getExternalContext().getApplicationContextPath();
			this.context.getExternalContext().redirect(path + "/login.faces");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
	}

	public void save() {
		if (this.usuario != null) {
			this.usuario.setRole(EUsuarioRole.ROLE_CLIENTE.getKey());
			if (this.usuario.getId() == null) {
				Usuario u = null;
				try {
					u = this.usuarioFacade.save(this.usuario);

					if(u != null) {
						this.cliente.setUsuario(u);
						this.clienteFacade.save(this.cliente);
						redirect();
					}

				} catch (NegocioException e) {
					if(u!=null) {
						this.usuarioFacade.delete(u);
					}
					logger.error(e.getMessage(), e);
					setMessageError(e.getMensagem());
				}
			}
		}
	}

	public void redirect() {
		try {
			String path = this.context.getExternalContext().getApplicationContextPath();
			this.context.getExternalContext().redirect(path + "/login.faces");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
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

		for(EUsuarioRole r : EUsuarioRole.values()) {
				if(r.equals(EUsuarioRole.ROLE_CLIENTE)) {
				String key = MessagesUtil.getResourceProperty("labels", r.getDesc(), this.context);
				descs.add(key);
				roleConverter.put(key, r.getDesc());
				this.userRoleDesc = r.getKey();
			}
		}
		return descs;
	}

	/**
	 * @return the userRoleDesc
	 */
	public String getUserRoleDesc() {
		return this.userRoleDesc;
	}

	/**
	 * @param userRoleDesc the userRoleDesc to set
	 */
	public void setUserRoleDesc(String userRoleDesc) {
		this.userRoleDesc = userRoleDesc;
	}

	/**
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


}
