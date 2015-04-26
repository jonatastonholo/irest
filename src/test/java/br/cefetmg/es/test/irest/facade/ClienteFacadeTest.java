package br.cefetmg.es.test.irest.facade;

import javax.inject.Inject;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import br.cefetmg.es.irest.controler.exception.NegocioException;
import br.cefetmg.es.irest.controler.facade.ClienteFacade;
import br.cefetmg.es.irest.controler.facade.UsuarioFacade;
import br.cefetmg.es.irest.model.entity.Cliente;
import br.cefetmg.es.irest.model.entity.Usuario;
import br.cefetmg.es.irest.model.enuns.EUsuarioRole;
import br.cefetmg.es.test.irest.AbstractDatabaseTest;
/**
 * Testes CDU-003
 * @author Jônatas
 *
 */
public class ClienteFacadeTest extends AbstractDatabaseTest {

	private static final Logger LOGGER = Logger
			.getLogger(ClienteFacadeTest.class);

	@Inject
	private UsuarioFacade usuarioFacade;

	@Inject
	private ClienteFacade clienteFacade;

	/**
	 * RN-020
	 */
	@Test
	public void testClienteEmptyName() {
		LOGGER.setLevel(Level.INFO);
		Usuario user = new Usuario("teste", "123456", "65467689258");
		Cliente cliente = null;
		user.setRole(EUsuarioRole.ROLE_ADMIN.getKey());
		LOGGER.info(user);
		try {
			usuarioFacade.save(user);
			cliente = new Cliente(user, "", "", "", null, "");
		} catch (NegocioException e) {
			String msg = e.getMensagem();
			switch (msg) {
			case "msg_required_costumer_name": Assert.assertTrue(true);LOGGER.info(msg);break;
			default:Assert.assertTrue(false);break;
			}
		}finally{
			usuarioFacade.delete(user);
		}
	}
}
