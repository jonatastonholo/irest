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
import br.cefetmg.es.irest.model.enuns.EUsuarioRole;
import br.cefetmg.es.irest.model.repositories.IClienteRepository;
import br.cefetmg.es.irest.model.repositories.IFuncionarioRepository;
import br.cefetmg.es.irest.model.repositories.IUsuarioRepository;

@Scope(value = WebApplicationContext.SCOPE_SESSION)
@Named(value = "usuarioFacade")
public class UsuarioFacade extends IBaseFacede<Usuario> {

	private static final long serialVersionUID = 1L;

	@Inject
	private IUsuarioRepository usuarioRepository;
	@Inject
	private IClienteRepository clienteRepository;
	@Inject
	private IFuncionarioRepository funcionarioRepository;

	private List<Usuario> usuarios;

	private Usuario usuario;

	public UsuarioFacade() {
	}

	@Override
	protected Usuario update(Usuario usuario) throws NegocioException {
		return null;
	}

	@Override
	public List<Usuario> findAll() {
		this.usuarios = this.usuarioRepository.findAll();
		return this.usuarios;
	}

	@Override
	public Usuario save(Usuario usuario) throws NegocioException {
		validate(usuario);
		return usuarioRepository.save(usuario);
	}

	@Override
	public void delete(Usuario usuario) {
		if (usuario != null) {
			removeDependency(usuario);
			this.usuarioRepository.delete(usuario.getId());
		}
	}

	@Override
	protected void validate(Usuario usuario) throws NegocioException {
		Usuario tmp = new Usuario();
		if(usuario == null) {
			throw new NegocioException("msg_invalid_user");
		}
		if (usuario.getRole() != null && !usuario.getRole().isEmpty()
				&& !usuario.getRole().trim().equals("")) {
			String role;
			if((role = EUsuarioRole.getDesc(usuario.getRole())).equals("")) {
				// Chegou a descrição da Role
				if((role = EUsuarioRole.getKey(usuario.getRole())).equals("")) {
					throw new NegocioException("msg_invalid_role");
				} else {
					usuario.setRole(role);
				}

			} else if(EUsuarioRole.getKey(usuario.getRole()).equals("")) {
				// Chegou a Key da role
				usuario.setRole(usuario.getRole());
			}

		} else {
			throw new NegocioException("msg_empty_role");
		}

		if (usuario.getCpf() != null && !usuario.getCpf().isEmpty()
				&& !usuario.getCpf().trim().equals("")) {
			usuario.setCpf(CpfUtils.apenasNumeros(usuario.getCpf()));
			if (!CpfUtils.validaCPF(usuario.getCpf())) {
				throw new NegocioException("msg_invalid_cpf");
			} else if((tmp = this.usuarioRepository.findByCpf(usuario.getCpf())) != null && (tmp.getId() != usuario.getId())) {
				throw new NegocioException("msg_duplicated_cpf");
			}
		} else {
			throw new NegocioException("msg_empty_cpf");
		}

		if (usuario.getLogin() != null && !usuario.getLogin().isEmpty()
				&& !usuario.getLogin().trim().equals("")) {
			if((tmp = this.usuarioRepository.findByLogin(usuario.getLogin())) != null && (tmp.getId() != usuario.getId())) {
				throw new NegocioException("msg_duplicated_login");
			}
		} else {
			throw new NegocioException("msg_empty_login");
		}

		if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()
				&& !usuario.getSenha().trim().equals("")) {
			if (usuario.getSenha().length() < 6) {
				throw new NegocioException("msg_small_pass");
			}
		} else {
			throw new NegocioException("msg_empty_pass");
		}
	}

	private void removeDependency(Usuario usuario) {
		Cliente c = usuarioRepository.buscarDependenciaCliente(usuario.getId());
		Funcionario f = usuarioRepository.buscarDependenciaFuncionario(usuario.getId());

		if(c!=null) {
			clienteRepository.delete(c);
		}

		if(f!=null) {
			funcionarioRepository.delete(f);
		}
	}

	public Cliente buscarDependenciaCliente(Integer usuarioId) {
		return usuarioRepository.buscarDependenciaCliente(usuarioId);
	}

	public Funcionario buscarDependenciaFuncionario(Integer usuarioId) {
		return usuarioRepository.buscarDependenciaFuncionario(usuarioId);
	}

	public Usuario findByLogin(String login) {
		return this.usuarioRepository.findByLogin(login);
	}

	/**
	 * @return the usuarios
	 */
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	/**
	 * @param usuarios
	 *            the usuarios to set
	 */
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario findById(Integer userId) {
		return this.usuarioRepository.findOne(userId);
	}

}
