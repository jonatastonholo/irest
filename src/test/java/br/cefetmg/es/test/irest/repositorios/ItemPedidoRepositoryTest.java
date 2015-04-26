package br.cefetmg.es.test.irest.repositorios;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;

import br.cefetmg.es.irest.model.entity.Atendimento;
import br.cefetmg.es.irest.model.entity.Item;
import br.cefetmg.es.irest.model.entity.ItemPedido;
import br.cefetmg.es.irest.model.entity.Mesa;
import br.cefetmg.es.irest.model.enuns.EStatusPedido;
import br.cefetmg.es.irest.model.repositories.IAtendimentoRepository;
import br.cefetmg.es.irest.model.repositories.IItemPedidoRepository;
import br.cefetmg.es.irest.model.repositories.IItemRepository;
import br.cefetmg.es.irest.model.repositories.IMesaRepository;
import br.cefetmg.es.test.irest.AbstractDatabaseTest;

public class ItemPedidoRepositoryTest extends AbstractDatabaseTest {
	private static final Logger LOGGER = Logger
			.getLogger(ItemPedidoRepositoryTest.class);

	@Inject
	private IItemPedidoRepository itemPedidoRepository;

	@Inject
	private IItemRepository itemRepository;

	@Inject
	private IMesaRepository mesaRepository;

	@Inject
	private IAtendimentoRepository atendimentoRepository;

	@Test
	public void testSelect() {
		List<ItemPedido> itens = this.itemPedidoRepository.findAll();
		LOGGER.setLevel(Level.INFO);
		LOGGER.info(itens);
	}

	@Test
	public void testInsertDelete() {
		List<Item> itens = itemRepository.findAll();
		Mesa m = mesaRepository.findByIpTerminal("127.0.0.1");
		Atendimento a = new Atendimento(m, new ArrayList<ItemPedido>(), false, "");

		a = atendimentoRepository.save(a);

		for(Item i : itens) {
			ItemPedido itemPedido = new ItemPedido(i, a, 1, EStatusPedido.SOLICITADO_CLIENTE.getKey());
			itemPedido = itemPedidoRepository.save(itemPedido);
			a.getItensPedido().add(itemPedido);
			atendimentoRepository.save(a);
		}

		for(ItemPedido i : itemPedidoRepository.findByAtendimento(a)) {
			itemPedidoRepository.delete(i.getId());
		}

		a.setItensPedido(null);
		atendimentoRepository.delete(a);

		LOGGER.setLevel(Level.INFO);
		LOGGER.info(itens);
	}
}
