package br.cefetmg.es.test.irest.repositorios;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;

import br.cefetmg.es.irest.model.entity.Item;
import br.cefetmg.es.irest.model.repositories.IItemRepository;
import br.cefetmg.es.test.irest.AbstractDatabaseTest;

public class ItemRepositoryTest extends AbstractDatabaseTest {
	private static final Logger LOGGER = Logger
			.getLogger(ItemRepositoryTest.class);

	@Inject
	private IItemRepository itemRepository;

	@Test
	public void testClienteRepository() {
		List<Item> itens = this.itemRepository.findAll();
		LOGGER.setLevel(Level.INFO);
		LOGGER.info(itens);
	}
}
