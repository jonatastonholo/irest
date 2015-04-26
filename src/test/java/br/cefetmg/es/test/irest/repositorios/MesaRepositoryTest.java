package br.cefetmg.es.test.irest.repositorios;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;

import br.cefetmg.es.irest.model.entity.Mesa;
import br.cefetmg.es.irest.model.repositories.IMesaRepository;
import br.cefetmg.es.test.irest.AbstractDatabaseTest;

public class MesaRepositoryTest extends AbstractDatabaseTest{

	private static final Logger LOGGER = Logger
			.getLogger(MesaRepositoryTest.class);

	@Inject
	private IMesaRepository mesaRepository;

	@Test
	public void mesaRepositoryTest() {
		List<Mesa> mesa = this.mesaRepository.findAll();
		LOGGER.setLevel(Level.INFO);
		LOGGER.info(mesa);
	}
}
