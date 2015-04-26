package br.cefetmg.es.irest.view.management.main;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import br.cefetmg.es.irest.model.entity.Usuario;
import br.cefetmg.es.irest.model.enuns.EUsuarioRole;
import br.cefetmg.es.irest.view.management.ChamadoMB;
import br.cefetmg.es.irest.view.management.atendimento.AtendimentoMB;
import br.cefetmg.es.irest.view.management.cliente.ClienteEditMB;
import br.cefetmg.es.irest.view.management.cliente.ClienteMB;
import br.cefetmg.es.irest.view.management.funcionario.FuncionarioEditMB;
import br.cefetmg.es.irest.view.management.funcionario.FuncionarioMB;
import br.cefetmg.es.irest.view.management.item.ItemMB;
import br.cefetmg.es.irest.view.management.usuario.UsuarioAddEditMB;
import br.cefetmg.es.irest.view.management.usuario.UsuarioMB;
import br.cefetmg.es.irest.view.util.BaseBean;
import br.cefetmg.es.irest.view.util.PageUtils;
import br.cefetmg.es.irest.view.util.WebContextHolder;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@Named(value = "menuMB")
public class MenuMB extends BaseBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioMB usuarioMB;

	@Inject
	private UsuarioAddEditMB usuarioAddEditMB;

	@Inject
	private FuncionarioMB fucionarioMB;

	@Inject
	private FuncionarioEditMB fucionarioEditMB;

	@Inject
	private ClienteMB clienteMB;

	@Inject
	private ClienteEditMB clienteEditMB;

	@Inject
	private ItemMB itemMB;

	@Inject
	private ChamadoMB chamadoMB;

	@Inject
	private AtendimentoMB atendimentoMB;

	@Inject
	private FacesContext context;

	@Inject
	private WebContextHolder webContextHolder;

	private Usuario usuarioLogado;

	public void home() {
		PageUtils.redirect(this.context, "/pages/main.faces");
	}

	public void manterUsuario() {
		if (this.webContextHolder.getUserRole().equals(EUsuarioRole.ROLE_ADMIN.getKey())) {
			PageUtils.redirect(this.context, "/pages/usuario/list.faces");
		} else {
			this.usuarioLogado = this.webContextHolder.getUsuarioLogado();
			usuarioMB.setSelectedUser(this.usuarioLogado);
			usuarioAddEditMB.update();
			PageUtils.redirect(this.context, "/pages/usuario/addEdit.faces");
		}
	}

	public void manterFuncionario() {
		if (this.webContextHolder.getUserRole().equals(EUsuarioRole.ROLE_ADMIN.getKey())) {
			PageUtils.redirect(this.context, "/pages/funcionario/list.faces");
		} else {
			fucionarioEditMB.update();
			PageUtils.redirect(this.context, "/pages/funcionario/edit.faces");
		}
	}

	public void manterCliente() {
		if (this.webContextHolder.getUserRole().equals(EUsuarioRole.ROLE_ADMIN.getKey())) {
			PageUtils.redirect(this.context, "/pages/cliente/list.faces");
		} else {
			clienteEditMB.update();
			PageUtils.redirect(this.context, "/pages/cliente/edit.faces");
		}
	}

	public void manterMesas() {
		if (this.webContextHolder.getUserRole().equals(EUsuarioRole.ROLE_ADMIN.getKey()) || this.webContextHolder.getUserRole().equals(EUsuarioRole.ROLE_FUNCIONARIO.getKey())) {
			PageUtils.redirect(this.context, "/pages/mesa/list.faces");
		}
	}

	public void manterItens() {
		if (this.webContextHolder.getUserRole().equals(EUsuarioRole.ROLE_ADMIN.getKey()) || this.webContextHolder.getUserRole().equals(EUsuarioRole.ROLE_FUNCIONARIO.getKey())) {
			PageUtils.redirect(this.context, "/pages/item/list.faces");
		}
	}

	public void manterPedido() {
		if(this.webContextHolder.getUserRole().equals(EUsuarioRole.ROLE_CLIENTE.getKey())) {
			if(atendimentoMB.existeItensAtendimento()) {
				PageUtils.redirect(this.context, "/pages/pedidos/order.faces");
			} else {
				PageUtils.redirect(this.context, "/pages/pedidos/menu.faces");
			}
		} else {
			PageUtils.redirect(this.context, "/pages/pedidos/list.faces");
		}
	}

	public void chamado() {
		if (this.webContextHolder.getUserRole().equals(EUsuarioRole.ROLE_ADMIN.getKey()) || this.webContextHolder.getUserRole().equals(EUsuarioRole.ROLE_FUNCIONARIO.getKey())) {
			PageUtils.redirect(this.context, "/pages/chamado/list.faces");
		}
	}

	public void idioma() {
		PageUtils.redirect(this.context, "/pages/settings/language.faces");
	}

	public void chamarAtendente() {
		String ip = this.webContextHolder.getIpLogado();
		this.chamadoMB.setIpTerminal(ip);
		this.chamadoMB.save();
	}



}
