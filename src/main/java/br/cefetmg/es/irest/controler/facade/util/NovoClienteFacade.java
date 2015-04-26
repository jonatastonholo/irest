package br.cefetmg.es.irest.controler.facade.util;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import br.cefetmg.es.irest.controler.exception.NegocioException;
import br.cefetmg.es.irest.model.entity.Cliente;
import br.cefetmg.es.irest.model.enuns.EUsuarioRole;
import br.cefetmg.es.irest.model.repositories.IClienteRepository;

@Scope(value = WebApplicationContext.SCOPE_SESSION)
@Named(value = "novoClienteFacade")
public class NovoClienteFacade extends IBaseFacede<Cliente> {

	private static final long serialVersionUID = 1L;

	@Inject
	private IClienteRepository clienteRepository;

	private List<Cliente> clientes;

	private Cliente cliente;

	@Override
	public List<Cliente> findAll() {
		this.clientes = this.clienteRepository.findAll();
		return this.clientes;
	}

	@Override
	public Cliente save(Cliente cliente) throws NegocioException {
		validate(cliente);
		return this.clienteRepository.save(cliente);
	}

	@Override
	protected Cliente update(Cliente cliente) throws NegocioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Cliente cliente) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validate(Cliente cliente) throws NegocioException {
		if (cliente == null) {
			throw new NegocioException("msg_invalid_costumer");
		}
		// Validação do usuário vinculado ao cliente inserido.
		if (cliente.getUsuario() == null) {
			throw new NegocioException("msg_invalid_costumer_user");
		} else if (cliente.getUsuario().getId() == null) {
			throw new NegocioException("msg_invalid_costumer_user");
		} else if (cliente.getUsuario().getRole() != null
				&& !cliente.getUsuario().getRole().isEmpty()
				&& !cliente.getUsuario().getRole().trim().equals("")) {
			if (!(EUsuarioRole.ROLE_CLIENTE.getKey().equals(cliente
					.getUsuario().getRole()))) {
				throw new NegocioException("msg_invalid_costumer_role");
			}

			if (cliente.getNome() != null && !cliente.getNome().isEmpty() && !cliente.getNome().trim().equals("")) {
			} else throw new NegocioException("msg_required_costumer_name");

			if (cliente.getEndereco() != null && !cliente.getEndereco().isEmpty() && !cliente.getEndereco().trim().equals("")) {
			} else throw new NegocioException("msg_required_costumer_address");

			if (cliente.getTelefone() != null && !cliente.getTelefone().isEmpty() && !cliente.getTelefone().trim().equals("")) {
			} else throw new NegocioException("msg_required_costumer_phone");
		}
	}

	/**
	 * @return the clientes
	 */
	public List<Cliente> getClientes() {
		return clientes;
	}

	/**
	 * @param clientes the clientes to set
	 */
	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
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
