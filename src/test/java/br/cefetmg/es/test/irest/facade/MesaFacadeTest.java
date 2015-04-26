package br.cefetmg.es.test.irest.facade;

import javax.inject.Inject;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import br.cefetmg.es.irest.controler.exception.NegocioException;
import br.cefetmg.es.irest.controler.facade.MesaFacade;
import br.cefetmg.es.irest.model.entity.Mesa;
import br.cefetmg.es.irest.model.enuns.EStatusMesa;
import br.cefetmg.es.test.irest.AbstractDatabaseTest;
/**
 * Testes CDU-004
 * @author Jônatas
 *
 */
public class MesaFacadeTest extends AbstractDatabaseTest {

	private static final Logger LOGGER = Logger
			.getLogger(MesaFacadeTest.class);

	@Inject
	private MesaFacade mesaFacade;


	/**
	 * RN-006 / RN-012
	 */
	@Test
	public void testMesaIpDuplicado() {
		LOGGER.setLevel(Level.INFO);
		Mesa mesa1 = new Mesa();
		mesa1.setCapacidade(8);
		mesa1.setEstadoMesa(EStatusMesa.LIVRE.getKey());
		mesa1.setIpTerminal("192.168.0.1");
		LOGGER.info(mesa1);

		Mesa mesa2 = new Mesa();
		mesa2.setCapacidade(4);
		mesa2.setEstadoMesa(EStatusMesa.LIVRE.getKey());
		mesa2.setIpTerminal("192.168.0.1");
		LOGGER.info(mesa2);
		try {
			mesaFacade.save(mesa1);
			mesaFacade.save(mesa2);
		} catch (NegocioException e) {
			String msg = e.getMensagem();
			switch (msg) {
			case "msg_duplicated_table_ip": Assert.assertTrue(true);LOGGER.info(msg);break;
			default:Assert.assertTrue(false);break;
			}
		}finally{
			mesaFacade.delete(mesa1);
			mesaFacade.delete(mesa2);
		}
	}

	/**
	 * RN-008
	 */
	@Test
	public void testMesaEmptyIp() {
		LOGGER.setLevel(Level.INFO);
		Mesa mesa1 = new Mesa();
		mesa1.setCapacidade(8);
		mesa1.setEstadoMesa(EStatusMesa.LIVRE.getKey());
		LOGGER.info(mesa1);
		try {
			mesaFacade.save(mesa1);
		} catch (NegocioException e) {
			String msg = e.getMensagem();
			switch (msg) {
			case "table_ip_required": Assert.assertTrue(true);LOGGER.info(msg);break;
			default:Assert.assertTrue(false);break;
			}
		}finally{
			mesaFacade.delete(mesa1);
		}
	}

	/**
	 * RN-010
	 */
	@Test
	public void testMesaEmptyStatus() {
		LOGGER.setLevel(Level.INFO);
		Mesa mesa1 = new Mesa();
		mesa1.setCapacidade(8);
		mesa1.setIpTerminal("192.168.0.1");
		LOGGER.info(mesa1);
		try {
			mesaFacade.save(mesa1);
		} catch (NegocioException e) {
			String msg = e.getMensagem();
			switch (msg) {
			case "table_status_required": Assert.assertTrue(true);LOGGER.info(msg);break;
			default:Assert.assertTrue(false);break;
			}
		}finally{
			mesaFacade.delete(mesa1);
		}
	}

	/**
	 * RN-028
	 */
	@Test
	public void testMesaCapacidade() {
		LOGGER.setLevel(Level.INFO);
		Mesa mesa1 = new Mesa();
		mesa1.setCapacidade(22);
		mesa1.setEstadoMesa(EStatusMesa.LIVRE.getKey());
		mesa1.setIpTerminal("192.168.0.1");
		LOGGER.info(mesa1);
		try {
			mesaFacade.save(mesa1);
		} catch (NegocioException e) {
			String msg = e.getMensagem();
			switch (msg) {
			case "msg_invalid_table_capacity": Assert.assertTrue(true);LOGGER.info(msg);break;
			default:Assert.assertTrue(false);break;
			}
		}finally{
			mesaFacade.delete(mesa1);
		}
	}
}
