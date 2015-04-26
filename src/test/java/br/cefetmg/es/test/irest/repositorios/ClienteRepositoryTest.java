package br.cefetmg.es.test.irest.repositorios;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;

import br.cefetmg.es.irest.model.dto.ClienteDto;
import br.cefetmg.es.irest.model.entity.Cliente;
import br.cefetmg.es.irest.model.entity.Usuario;
import br.cefetmg.es.irest.model.repositories.IClienteRepository;
import br.cefetmg.es.irest.model.repositories.IUsuarioRepository;
import br.cefetmg.es.test.irest.AbstractDatabaseTest;

public class ClienteRepositoryTest extends AbstractDatabaseTest {
	private static final Logger LOGGER = Logger
			.getLogger(ClienteRepositoryTest.class);

	@Inject
	private IClienteRepository clienteRepository;

	@Inject
	private IUsuarioRepository usuarioRepository;

	@Test
	public void testClienteRepository() {
		List<Cliente> clientes = this.clienteRepository.findAll();
		LOGGER.setLevel(Level.INFO);
		LOGGER.info(clientes);
	}

	@Test
	public void testClienteDto() {
		List<ClienteDto> clientes = this.clienteRepository.findClienteUsuario();
		LOGGER.setLevel(Level.INFO);
		LOGGER.info(clientes);
	}

	@Test
	public void testDependencia() {
		List<Usuario> usuarios = this.usuarioRepository.findByRole("ROLE_CLIENTE");
		Usuario u = null;
		if(usuarios != null) {
			if(usuarios.get(0) != null) {
				u = clienteRepository.buscarDependenciaUsuario(usuarios.get(0).getId());
			}
		}
		LOGGER.setLevel(Level.INFO);
		LOGGER.info(u);
	}
}
