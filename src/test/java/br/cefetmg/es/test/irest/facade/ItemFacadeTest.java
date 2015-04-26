package br.cefetmg.es.test.irest.facade;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import br.cefetmg.es.irest.controler.exception.NegocioException;
import br.cefetmg.es.irest.controler.facade.ItemFacade;
import br.cefetmg.es.irest.model.entity.Item;
import br.cefetmg.es.test.irest.AbstractDatabaseTest;
/**
 * Testes CDU-006
 * @author Jônatas
 *
 */
public class ItemFacadeTest extends AbstractDatabaseTest {

	private static final Logger LOGGER = Logger
			.getLogger(ItemFacadeTest.class);

	@Inject
	private ItemFacade itemFacade;

	/**
	 * RN-032
	 */
	@Test
	public void testItemNome() {
		LOGGER.setLevel(Level.INFO);
		Item item = new Item("Teste", "", "teste", new BigDecimal(0), null);
		Item item2 = new Item("Teste", "dd", "2aaa", new BigDecimal(0), null);
		LOGGER.info(item);
		try {
			itemFacade.save(item);
			itemFacade.save(item2);
		} catch (NegocioException e) {
			String msg = e.getMensagem();
			switch (msg) {
			case "msg_duplicated_item_name": Assert.assertTrue(true);LOGGER.info(msg);break;
			default:Assert.assertTrue(false);break;
			}
		}finally{
			itemFacade.delete(item);
			itemFacade.delete(item2);
		}
	}
}
