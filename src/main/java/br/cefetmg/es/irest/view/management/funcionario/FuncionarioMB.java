package br.cefetmg.es.irest.view.management.funcionario;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import br.cefetmg.es.irest.controler.facade.FuncionarioFacade;
import br.cefetmg.es.irest.controler.util.CpfUtils;
import br.cefetmg.es.irest.model.entity.Funcionario;
import br.cefetmg.es.irest.view.util.BaseBean;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@Named(value="funcionarioMB")
public class FuncionarioMB extends BaseBean{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(FuncionarioMB.class);

	@Inject
	private FuncionarioFacade funcionarioFacade;

	private List<Funcionario> funcionario;

	private List<Funcionario> filteredFuncionarios;

	private Funcionario selectedFuncionario;

	private Integer id;

	private boolean editDisabled = false;

	private String nome;

	private String cpfFormatado;

	public FuncionarioMB() {
	}

	@PostConstruct
	public void onLoad() {
		this.funcionario = this.funcionarioFacade.findAll();
	}

	public List<Funcionario> getFuncionarios() {
		return this.funcionario;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void delete() {
		if (this.selectedFuncionario != null) {
			this.funcionarioFacade.delete(this.selectedFuncionario);
			unselectFuncionario();
		}
	}


	public void selectFuncionario(SelectEvent evt) {
		try {
			if (evt.getObject() != null) {
				this.selectedFuncionario = (Funcionario) evt.getObject();
				this.editDisabled = true;
				this.nome = this.selectedFuncionario.getNome();
			} else {
				this.selectedFuncionario = null;
				this.editDisabled = false;
			}
		} catch (Exception e) {
			this.selectedFuncionario = null;
			this.editDisabled = false;
			logger.error(e.getMessage(), e);
		}
	}

	public void unselectFuncionario() {
		this.selectedFuncionario = null;
		this.editDisabled = false;
	}

	public Funcionario getSelectedFuncionario() {
		return this.selectedFuncionario;
	}

	public void setSelectedFuncionario(Funcionario selectedFuncionario) {
		this.selectedFuncionario = selectedFuncionario;
	}


	public List<Funcionario> getFilteredFuncionarios() {
		return filteredFuncionarios;
	}

	public void setFilteredFuncionarios(List<Funcionario> filteredFuncionarios) {
		this.filteredFuncionarios = filteredFuncionarios;
	}

	public boolean isEditDisabled() {
		return editDisabled;
	}

	public void setEditDisabled(boolean editDisabled) {
		this.editDisabled = editDisabled;
	}

	public String getNome() {
		return nome;
	}

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

	public String getCpfFormatado() {
		return cpfFormatado;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionario = funcionarios;
	}

}
