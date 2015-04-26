package br.cefetmg.es.irest.controler.facade;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import br.cefetmg.es.irest.controler.exception.NegocioException;
import br.cefetmg.es.irest.controler.facade.util.IBaseFacede;
import br.cefetmg.es.irest.controler.util.CpfUtils;
import br.cefetmg.es.irest.model.entity.Cliente;
import br.cefetmg.es.irest.model.entity.Funcionario;
import br.cefetmg.es.irest.model.entity.Usuario;
import br.cefetmg.es.irest.model.enuns.EFuncionarioFuncao;
import br.cefetmg.es.irest.model.enuns.EUsuarioRole;
import br.cefetmg.es.irest.model.repositories.IFuncionarioRepository;
import br.cefetmg.es.irest.model.repositories.IUsuarioRepository;

@Scope(value = WebApplicationContext.SCOPE_SESSION)
@Named(value = "funcionarioFacade")
public class FuncionarioFacade extends IBaseFacede<Funcionario>{

	private static final long serialVersionUID = 1L;

	@Inject
	IFuncionarioRepository funcionarioRepository;

	@Inject
	IUsuarioRepository usuarioRepository;

	@Override
	public List<Funcionario> findAll() {
		return this.funcionarioRepository.findAll();
	}

	@Override
	public Funcionario save(Funcionario entity) throws NegocioException {
		validate(entity);
		return funcionarioRepository.save(entity);
	}

	@Override
	protected Funcionario update(Funcionario entity) throws NegocioException {
		return null;
	}

	@Override
	public void delete(Funcionario entity) {
		if (entity != null) {
			Usuario u = buscarDependenciaUsuario(entity);
			this.funcionarioRepository.delete(entity.getId());
			if(u != null) {
				usuarioRepository.delete(u);
			}
		}
	}

	@Override
	protected void validate(Funcionario entity) throws NegocioException {
		if(entity == null) {
			throw new NegocioException("msg_invalid_costumer");
		}

		if (entity.getNome() != null && !entity.getNome().isEmpty()
				&& !entity.getNome().trim().equals("")) {
		} else {
			throw new NegocioException("msg_required_employee_name");
		}

		if (entity.getFuncao() != null && EFuncionarioFuncao.contains(entity.getFuncao())) {
		} else {
			throw new NegocioException("msg_required_employee_function");
		}

		if(entity.getUsuario() == null) {
			throw new NegocioException("msg_invalid_employee_user");
		} else {
			// verificar se usuário já está vinculado a outro cliente

			if(hasDependencia(entity.getUsuario(),entity)) {
				throw new NegocioException("msg_duplicated_employee_user");
			} else if(entity.getUsuario().getRole().equals(EUsuarioRole.ROLE_CLIENTE.getKey())) {
				throw new NegocioException("msg_cpf_belongs_costumer_user");
			}
		}

	}

	public Funcionario getFuncionarioByUsuario(Usuario usuario) {
		return funcionarioRepository.getFuncionarioByUsuario(usuario);
	}

	private Usuario buscarDependenciaUsuario(Funcionario funcionario) {
		if (funcionario.getUsuario() != null) {
			return funcionarioRepository.buscarDependenciaUsuario(funcionario.getUsuario().getId());
		}
		return null;
	}

	public Usuario findByCpf(String cpf) {
		return this.usuarioRepository.findByCpf(CpfUtils.apenasNumeros(cpf));
	}

	public boolean hasDependencia(Usuario u, Funcionario funcionario) {
		Cliente c = this.usuarioRepository.buscarDependenciaCliente(u.getId());
		Funcionario f = this.usuarioRepository.buscarDependenciaFuncionario(u.getId());
		return (c!=null || (f!=null && f.getId() != funcionario.getId()));
	}

}
