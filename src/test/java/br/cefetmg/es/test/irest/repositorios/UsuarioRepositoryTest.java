package br.cefetmg.es.test.irest.repositorios;

import javax.inject.Inject;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;

import br.cefetmg.es.irest.model.entity.Cliente;
import br.cefetmg.es.irest.model.entity.Funcionario;
import br.cefetmg.es.irest.model.repositories.IUsuarioRepository;
import br.cefetmg.es.test.irest.AbstractDatabaseTest;

public class UsuarioRepositoryTest extends AbstractDatabaseTest {

	private static final Logger LOGGER = Logger
			.getLogger(UsuarioRepositoryTest.class);

	@Inject
	private IUsuarioRepository userRepository;

	// @Test
	// public void testUsuarioRepository() {
	// List<Usuario> users = this.userRepository.findAll();
	// LOGGER.setLevel(Level.INFO);
	// LOGGER.info(users);
	// }

	@Test
	public void testDependenciasUsuario() {
		Cliente cli = this.userRepository.buscarDependenciaCliente(new Integer(11));
		Funcionario fun= this.userRepository.buscarDependenciaFuncionario(new Integer(11));
		LOGGER.setLevel(Level.INFO);
		LOGGER.info("\nDependencia com Cliente encontrada: " + cli);
		LOGGER.info("\nDependencia com Funcionario encontrada: " + fun);
	}

}
