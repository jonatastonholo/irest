package br.cefetmg.es.irest.view.management.funcionario;

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
import br.cefetmg.es.irest.controler.facade.FuncionarioFacade;
import br.cefetmg.es.irest.model.entity.Funcionario;
import br.cefetmg.es.irest.model.entity.Usuario;
import br.cefetmg.es.irest.model.enuns.EFuncionarioFuncao;
import br.cefetmg.es.irest.model.enuns.EUsuarioRole;
import br.cefetmg.es.irest.view.management.util.MessagesUtil;
import br.cefetmg.es.irest.view.util.BaseAddEditBean;
import br.cefetmg.es.irest.view.util.PageUtils;

@Scope(value = WebApplicationContext.SCOPE_SESSION)
@Named(value = "funcionarioEditMB")
public class FuncionarioEditMB extends BaseAddEditBean{

	private static final long serialVersionUID = 1L;

	@Inject
	private FuncionarioFacade funcionarioFacade;

	@Autowired
	private FuncionarioMB funcionarioMB;

	private Funcionario funcionario;

	private String title;

	private String messageError;

	private Integer id;

	private String nome;

	private String funcao;

	private String cpf;

	private String tmpCpf;

	private boolean addOperation = false;

	private boolean disableCpfEdit = false;

	@SuppressWarnings("unused")
	private List<String> funcoes;

	private Map<String,String> funcaoConverter;

	private String funcaoDesc;

	private boolean disableFuncaoEdit = false;

	public FuncionarioEditMB() {
		this.funcionario = new Funcionario();
		this.addOperation = true;
	}

	@Override
	public void add() {
		this.title = MessagesUtil.getResourceProperty("labels", "employee_add", this.getFacesConext());
		this.addOperation = true;
	}

	@Override
	public void update() {
		this.title = MessagesUtil.getResourceProperty("labels", "employee_update", this.getFacesConext());
		this.addOperation = false;
		if(!isRoleAdmin()) {
			this.disableCpfEdit = true;
			this.disableFuncaoEdit = true;
		}
		if(!isRoleAdmin()) {
			this.funcionario = getFuncionarioLogado();
		} else {
			this.funcionario = this.funcionarioMB.getSelectedFuncionario();
		}
		forgeCampos();
	}

	@Override
	protected void forgeCampos() {
		if(this.funcionario == null) {
			this.funcionario = getFuncionarioLogado();
		}
		this.id = this.funcionario.getId();
		this.nome = this.funcionario.getNome();
		this.funcao = this.funcionario.getFuncao();
		if(this.funcionario.getUsuario() != null) {
			this.tmpCpf = this.funcionario.getUsuario().getCpf();
			this.cpf = this.funcionario.getUsuario().getCpf();
		}
		this.funcaoDesc = MessagesUtil.getResourceProperty("labels", EFuncionarioFuncao.getDesc(funcionario.getFuncao()),getFacesConext());
	}

	@Override
	protected void limpaCampos() {
		this.id = null;
		this.nome = "";
		this.funcao = null;
		this.tmpCpf = "";
		this.cpf = "";
		funcionario.setUsuario(null);
	}

	@Override
	protected void forgeEntity () {
		this.funcionario.setId(id);
		this.funcionario.setNome(nome);
		this.funcionario.setFuncao(funcao);
		buscarUsuarioCpf();
	}

	@Override
	public void cancel() {
		redirect();
	}

	@Override
	public void save() {
		forgeEntity();
		if (this.funcionario != null) {
			updateFuncao();
			if (this.funcionario.getId() == null) {
				if(funcionario.getUsuario() != null) {
					// Add
					try {
						this.funcionarioFacade.save(this.funcionario);
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
					this.funcionarioFacade.save(this.funcionario);
					redirect();
				} catch (NegocioException e) {
					setMessageError(e.getMensagem());
				}
			}
		}
	}

	private void updateFuncao () {
		if(funcaoDesc != null && !funcaoDesc.equals("")) {
			this.funcionario.setFuncao(EFuncionarioFuncao.getKey(funcaoConverter.get(funcaoDesc)));
		} else if (funcao != null && !funcao.equals("")){
			this.funcionario.setFuncao(EFuncionarioFuncao.getKey(funcaoConverter.get(funcao)));
		}else if(this.funcionario.getFuncao() == null && (funcaoDesc == null)) {
			//this.funcaoDesc = "COPAZZZ";
			//this.funcionario.setFuncao(EFuncionarioFuncao.getKey(funcaoConverter.get(funcaoDesc)));
		} else if(this.funcionario.getFuncao() == null && funcaoDesc != null) {
			this.funcionario.setFuncao(EFuncionarioFuncao.getKey(funcaoConverter.get(funcaoDesc)));
		} else if(EFuncionarioFuncao.getDesc(funcionario.getFuncao()).equals("")){ //A role é uma desc
			this.funcionario.setFuncao(EFuncionarioFuncao.getKey(EUsuarioRole.getKey(funcionario.getFuncao())));
		} else {
			this.funcionario.setFuncao(funcao);
		}
	}

	public void selectFuncao(AjaxBehaviorEvent evt) {
		try {
			if (!evt.getComponent().getAttributes().get("value").toString().trim().equalsIgnoreCase("")) {
				this.funcao = (String) evt.getComponent().getAttributes().get("value").toString();
			} else {
				this.funcao = null;
			}
		} catch (Exception e) {
			this.funcao = null;
			this.funcao = null;
		}
	}

	@Override
	protected void redirect() {
		if(isRoleAdmin()) {
			PageUtils.redirect(getFacesConext(), "/pages/funcionario/list.faces");
			if(this.funcionarioMB != null) {
				this.funcionarioMB.unselectFuncionario();
			}
		} else {
			PageUtils.redirect(getFacesConext(), "/pages/main.faces");
		}
	}

	public Funcionario getFuncionario() {
		if((!isAddOperation())&& (funcionario == null || funcionario.getNome() == null)) {
			this.funcionario = getFuncionarioLogado();
		}

		if(isAdminEditOperation()) {
			this.funcionario = this.funcionarioMB.getSelectedFuncionario();
		}

		if(funcionario!=null && funcionario.getNome() != null) {
			this.cpf = funcionario.getUsuario().getCpf();
		}

		return funcionario;
	}

	private Funcionario getFuncionarioLogado() {
		Usuario u = webContextHolder.getUsuarioLogado();
		return funcionarioFacade.getFuncionarioByUsuario(u);
	}


	public void setUsuario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	private boolean isAdminEditOperation() {
		boolean editOperation =
					isRoleAdmin()
					&& this.funcionarioMB.getSelectedFuncionario() != null;

		return editOperation;
	}

	private boolean isAddOperation() {
		addOperation =
				isRoleAdmin()
					&& this.funcionarioMB.getSelectedFuncionario() == null;

		return addOperation;
	}

	public void buscarUsuarioCpf() {
		Usuario u = funcionarioFacade.findByCpf(cpf);
		if(u!=null) {

			if(tmpCpf != null && u.getCpf() != null) {
				if(tmpCpf.equals(u.getCpf())) {
					funcionario.setUsuario(u);
				} else {
					// verifica se o novo cpf possui vinculo
					if(!this.funcionarioFacade.hasDependencia(u,this.funcionario)) {
						funcionario.setUsuario(u);
					} else {
						setMessageError("msg_duplicated_employee_user");
					}
				}
			}
		}
	}

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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setDisableCpfEdit(boolean disableCpfEdit) {
		this.disableCpfEdit = disableCpfEdit;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public String getTmpCpf() {
		return tmpCpf;
	}

	public void setTmpCpf(String tmpCpf) {
		this.tmpCpf = tmpCpf;
	}

	public List<String> getFuncoes() {
		List<String> descs = new ArrayList<String>();
		funcaoConverter = new HashMap<String, String>();

		for(String s : EFuncionarioFuncao.descs()) {
			String key = MessagesUtil.getResourceProperty("labels", s, this.getFacesConext());
			descs.add(key);
			funcaoConverter.put(key, s);
		}
		return descs;
	}

	public boolean isDisableFuncaoEdit() {
		if(!isRoleAdmin()) {
			this.disableFuncaoEdit = true;
		}
		return this.disableFuncaoEdit;
	}

	public String getFuncaoDesc() {
		if(!isAddOperation() && funcionario != null && this.funcaoDesc == null) {
			this.funcaoDesc =  MessagesUtil.getResourceProperty("labels", EFuncionarioFuncao.getDesc(funcionario.getFuncao()), getFacesConext());
		}
		return this.funcaoDesc;
	}

	public void setFuncaoDesc(String funcaoDesc) {
		this.funcaoDesc = funcaoDesc;
	}
}
