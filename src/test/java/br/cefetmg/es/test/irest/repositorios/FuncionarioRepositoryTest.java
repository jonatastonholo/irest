package br.cefetmg.es.test.irest.repositorios;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;

import br.cefetmg.es.irest.model.entity.Funcionario;
import br.cefetmg.es.irest.model.repositories.IFuncionarioRepository;
import br.cefetmg.es.test.irest.AbstractDatabaseTest;

public class FuncionarioRepositoryTest extends AbstractDatabaseTest {
	private static final Logger LOGGER = Logger
			.getLogger(UsuarioRepositoryTest.class);

	@Inject
	private IFuncionarioRepository funcionarioRepository;

	@Test
	public void testUsuarioRepository() {
		List<Funcionario> users = this.funcionarioRepository.findAll();
		LOGGER.setLevel(Level.INFO);
		LOGGER.info(users);
	}
}
