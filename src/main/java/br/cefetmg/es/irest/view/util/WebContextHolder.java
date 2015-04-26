package br.cefetmg.es.irest.view.util;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.WebApplicationContext;

import br.cefetmg.es.irest.controler.facade.UsuarioFacade;
import br.cefetmg.es.irest.model.entity.Atendimento;
import br.cefetmg.es.irest.model.entity.Cliente;
import br.cefetmg.es.irest.model.entity.Funcionario;
import br.cefetmg.es.irest.model.entity.Item;
import br.cefetmg.es.irest.model.entity.ItemPedido;
import br.cefetmg.es.irest.model.entity.Usuario;
import br.cefetmg.es.irest.model.enuns.EUsuarioRole;

@Scope(value = WebApplicationContext.SCOPE_SESSION)
@Named(value = "webContextHolder")
public final class WebContextHolder {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(WebContextHolder.class);

	@Inject
	private FacesContext context;

	@Inject
	private UsuarioFacade usuarioFacade;

	private String username;

	private String userRole;

	private Integer userId;

	private Usuario usuarioLogado;

	private Object entidadeLogada;

	private boolean roleAdmin = false;

	private boolean roleCliente = false;

	private boolean roleFuncionario = false;

	private String ipLogado;

	// Sessão Item
	private String nomeImagemItem;

	private InputStream imagemItem;
	private InputStream imagemItemTmp;
	private Item item;
	private boolean editOp = false;
	private String tipoItemSelected;

	// Sessão Atendimento
	private Map<Integer,ItemPedido> mapItensPedido;
	private Atendimento atendimento;
	private BigDecimal totalAtendimento;
	private String formaPagamento;

	public WebContextHolder() {
	}

	@PostConstruct
	public void onLoad() {

	}

	public void flushItem() {
		this.formaPagamento = null;
		this.nomeImagemItem = null;
		this.imagemItem = null;
		this.imagemItemTmp = null;
		this.item = null;
		this.editOp = false;
		tipoItemSelected=null;
	}

	public void flushItemPedido() {
		mapItensPedido = null;
		totalAtendimento = null;
	}

	public String getUsername() {
		this.username = ((UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal()).getUsername();
		return this.username;
	}

	public String getUserRole() {
		this.userRole = ((UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal())
				.getAuthorities().toString();
		this.userRole = this.userRole.substring(1, this.userRole.length() - 1);
		return this.userRole;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param userRole
	 *            the userRole to set
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	/**
	 * @return the usuarioLogado
	 */
	public Usuario getUsuarioLogado() {
		if(userId == null) {
			this.usuarioLogado = usuarioFacade.findByLogin(getUsername());
		} else {
			this.usuarioLogado = usuarioFacade.findById(userId);
		}
		return usuarioLogado;
	}

	/**
	 * @param usuarioLogado
	 *            the usuarioLogado to set
	 */
	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	/**
	 * @return the entidadeLogada
	 */
	public Object getEntidadeLogada() {
		if (this.getUsuarioLogado() != null) {
			if (getUserRole().equals(EUsuarioRole.ROLE_ADMIN.getKey()) || getUserRole().equals(EUsuarioRole.ROLE_FUNCIONARIO.getKey())) {
				this.entidadeLogada = (Funcionario)this.usuarioFacade.buscarDependenciaFuncionario(this.usuarioLogado.getId());
			} else {
				this.entidadeLogada = (Cliente)this.usuarioFacade.buscarDependenciaCliente(this.usuarioLogado.getId());
			}
		}
		return entidadeLogada;
	}

	/**
	 * @param entidadeLogada the entidadeLogada to set
	 */
	public void setEntidadeLogada(Object entidadeLogada) {
		this.entidadeLogada = entidadeLogada;
	}

	public boolean isRoleAdmin() {
		this.roleAdmin = getUserRole().equals(EUsuarioRole.ROLE_ADMIN.getKey());
		return this.roleAdmin;
	}

	public boolean isRoleCliente() {
		this.roleCliente = getUserRole().equals(EUsuarioRole.ROLE_CLIENTE.getKey());
		return this.roleCliente;
	}

	public boolean isRoleFuncionario() {
		this.roleFuncionario = getUserRole().equals(EUsuarioRole.ROLE_FUNCIONARIO.getKey());
		return this.roleFuncionario;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the nomeItem
	 */
	public String getNomeImagemItem() {
		return nomeImagemItem;
	}

	/**
	 * @param nomeItem the nomeItem to set
	 */
	public void setNomeImagemItem(String nomeImagemItem) {
		this.nomeImagemItem = nomeImagemItem;
	}

	/**
	 * @return the imagemItem
	 */
	public InputStream getImagemItem() {
		return imagemItem;
	}

	/**
	 * @param imagemItem the imagemItem to set
	 */
	public void setImagemItem(InputStream imagemItem) {
		this.imagemItem = imagemItem;
	}

	/**
	 * @return the imagemItemTmp
	 */
	public InputStream getImagemItemTmp() {
		return imagemItemTmp;
	}

	/**
	 * @param imagemItemTmp the imagemItemTmp to set
	 */
	public void setImagemItemTmp(InputStream imagemItemTmp) {
		this.imagemItemTmp = imagemItemTmp;
	}

	/**
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	/**
	 * @param editOp the edit to set
	 */
	public void setEditOp(boolean editOp) {
		this.editOp = editOp;
	}

	/**
	 * @return the editOp
	 */
	public boolean isEditOp() {
		return editOp;
	}

	/**
	 * @return the tipoItemSelected
	 */
	public String getTipoItemSelected() {
		return tipoItemSelected;
	}

	/**
	 * @param tipoItemSelected the tipoItemSelected to set
	 */
	public void setTipoItemSelected(String tipoItemSelected) {
		this.tipoItemSelected = tipoItemSelected;
	}

	/**
	 * @return
	 */
	public String getIpLogado() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
		ipLogado = request.getRemoteAddr();
		return ipLogado;
	}

	/**
	 * @param ipLogado the ipLogado to set
	 */
	public void setIpLogado(String ipLogado) {
		this.ipLogado = ipLogado;
	}

	/**
	 * @return the mapItemPedidoQuant
	 */
	public Map<Integer, ItemPedido> getMapItensPedido() {
		if(mapItensPedido == null) {
			mapItensPedido = new HashMap<Integer, ItemPedido>();
		}
		return mapItensPedido;
	}

	/**
	 * @param mapItemPedidoQuant the mapItemPedidoQuant to set
	 */
	public void setMapItensPedido(Map<Integer, ItemPedido> mapItemPedidoQuant) {
		this.mapItensPedido = mapItemPedidoQuant;
	}

	/**
	 * @return the totalAtendimento
	 */
	public BigDecimal getTotalAtendimento() {
		if(totalAtendimento==null) {
			totalAtendimento = new BigDecimal(0);
		}
		return totalAtendimento;
	}

	/**
	 * @param totalAtendimento the totalAtendimento to set
	 */
	public void setTotalAtendimento(BigDecimal totalAtendimento) {
		this.totalAtendimento = totalAtendimento;
	}

	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}

	public Atendimento getAtendimento() {
		return this.atendimento;
	}

	/**
	 * @return the formaPagamento
	 */
	public String getFormaPagamento() {
		return formaPagamento;
	}

	/**
	 * @param formaPagamento the formaPagamento to set
	 */
	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
}