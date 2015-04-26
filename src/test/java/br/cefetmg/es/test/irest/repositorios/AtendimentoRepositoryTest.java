package br.cefetmg.es.test.irest.repositorios;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;

import br.cefetmg.es.irest.model.entity.Atendimento;
import br.cefetmg.es.irest.model.repositories.IAtendimentoRepository;
import br.cefetmg.es.test.irest.AbstractDatabaseTest;

public class AtendimentoRepositoryTest extends AbstractDatabaseTest {
	private static final Logger LOGGER = Logger
			.getLogger(AtendimentoRepositoryTest.class);

	@Inject
	private IAtendimentoRepository atendimentoRepository;

	@Test
	public void testClienteRepository() {
		List<Atendimento> atendimentos = this.atendimentoRepository.findAll();
		LOGGER.setLevel(Level.INFO);
		LOGGER.info(atendimentos);
	}
}
