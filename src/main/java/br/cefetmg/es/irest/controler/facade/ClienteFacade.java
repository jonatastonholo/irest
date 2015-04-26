package br.cefetmg.es.irest.controler.facade;

import java.util.List;

import javax.faces.context.FacesContext;
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
import br.cefetmg.es.irest.model.repositories.IUsuarioRepository;

/**
 * The Class ClienteFacade.
 */
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@Named(value = "clienteFacade")
public class ClienteFacade extends IBaseFacede<Cliente> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cliente repository. */
	@Inject
	private IClienteRepository clienteRepository;

	/** The usuario repository. */
	@Inject
	private IUsuarioRepository usuarioRepository;

	/** The context. */
	@Inject
	FacesContext context;

	/** The clientes. */
	private List<Cliente> clientes;

	/** The cliente. */
	private Cliente cliente;

	/**
	 * Instantiates a new cliente facade.
	 */
	public ClienteFacade() {
	}

	/* (non-Javadoc)
	 * @see br.cefetmg.es.irest.controler.facade.util.IBaseFacede#update(java.lang.Object)
	 */
	@Override
	protected Cliente update(Cliente cliente) throws NegocioException {
		return null;
	}

	/* (non-Javadoc)
	 * @see br.cefetmg.es.irest.controler.facade.util.IBaseFacede#findAll()
	 */
	@Override
	public List<Cliente> findAll() {
		this.clientes = this.clienteRepository.findAll();
		return this.clientes;
	}

	/* (non-Javadoc)
	 * @see br.cefetmg.es.irest.controler.facade.util.IBaseFacede#save(java.lang.Object)
	 */
	@Override
	public Cliente save(Cliente cliente) throws NegocioException {
		validate(cliente);
		return clienteRepository.save(cliente);
	}

	/* (non-Javadoc)
	 * @see br.cefetmg.es.irest.controler.facade.util.IBaseFacede#delete(java.lang.Object)
	 */
	@Override
	public void delete(Cliente cliente) {
		if (cliente != null) {
			Usuario u = buscarDependenciaUsuario(cliente);
			this.clienteRepository.delete(cliente.getId());
			if(u != null) {
				usuarioRepository.delete(u);
			}
		}
	}

	/* (non-Javadoc)
	 * @see br.cefetmg.es.irest.controler.facade.util.IBaseFacede#validate(java.lang.Object)
	 */
	@Override
	protected void validate(Cliente cliente) throws NegocioException {
		if(cliente == null) {
			throw new NegocioException("msg_invalid_costumer");
		}

		if (cliente.getNome() != null && !cliente.getNome().isEmpty()
				&& !cliente.getNome().trim().equals("")) {
		} else {
			throw new NegocioException("msg_required_costumer_name");
		}

		if (cliente.getEndereco() != null && !cliente.getEndereco().isEmpty()
				&& !cliente.getEndereco().trim().equals("")) {
		} else {
			throw new NegocioException("msg_required_costumer_address");
		}

		if (cliente.getTelefone() != null && !cliente.getTelefone().isEmpty()
				&& !cliente.getTelefone().trim().equals("")) {
		} else {
			throw new NegocioException("msg_required_costumer_phone");
		}

		if(cliente.getUsuario() == null) {
			throw new NegocioException("msg_invalid_costumer_user");
		} else {
			// verificar se usuário já está vinculado a outro cliente

			if(hasDependencia(cliente.getUsuario(),cliente)) {
				throw new NegocioException("msg_duplicated_costumer_user");
			} else if(cliente.getUsuario().getRole().equals(EUsuarioRole.ROLE_FUNCIONARIO.getKey())) {
				throw new NegocioException("msg_cpf_belongs_employee_user");
			}
		}
	}

	/**
	 * Buscar dependencia usuario.
	 *
	 * @param cliente the cliente
	 * @return the usuario
	 */
	private Usuario buscarDependenciaUsuario(Cliente cliente) {
		if (cliente.getUsuario() != null) {
			return clienteRepository.buscarDependenciaUsuario(cliente.getUsuario().getId());
		}
		return null;
	}

	public boolean hasDependencia(Usuario usuario, Cliente cliente) {
		Cliente c = this.usuarioRepository.buscarDependenciaCliente(usuario.getId());
		Funcionario f = this.usuarioRepository.buscarDependenciaFuncionario(usuario.getId());
		return ((c!=null && c.getId() != cliente.getId()) || f!=null);
	}

	/**
	 * Find by login.
	 *
	 * @param login the login
	 * @return the usuario
	 */
	public Usuario findByCpf(String cpf) {
		return this.usuarioRepository.findByCpf(CpfUtils.apenasNumeros(cpf));
	}

	/**
	 * Gets the clientes.
	 *
	 * @return the clientes
	 */
	public List<Cliente> getClientes() {
		return clientes;
	}

	/**
	 * Sets the usuarios.
	 *
	 * @param clientes the new usuarios
	 */
	public void setUsuarios(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	/**
	 * Gets the usuario.
	 *
	 * @return the usuario
	 */
	public Cliente getUsuario() {
		return cliente;
	}

	/**
	 * Sets the usuario.
	 *
	 * @param cliente the new usuario
	 */
	public void setUsuario(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cliente getClienteByUsuario(Usuario usuario) {
		return clienteRepository.getClienteByUsuario(usuario);
	}

}
