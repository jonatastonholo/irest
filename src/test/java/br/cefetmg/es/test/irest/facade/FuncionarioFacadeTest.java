package br.cefetmg.es.test.irest.facade;

import javax.inject.Inject;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import br.cefetmg.es.irest.controler.exception.NegocioException;
import br.cefetmg.es.irest.controler.facade.FuncionarioFacade;
import br.cefetmg.es.irest.controler.facade.UsuarioFacade;
import br.cefetmg.es.irest.model.entity.Funcionario;
import br.cefetmg.es.irest.model.entity.Usuario;
import br.cefetmg.es.irest.model.enuns.EFuncionarioFuncao;
import br.cefetmg.es.irest.model.enuns.EUsuarioRole;
import br.cefetmg.es.test.irest.AbstractDatabaseTest;
/**
 * Testes CDU-002
 * @author Jônatas
 *
 */
public class FuncionarioFacadeTest extends AbstractDatabaseTest {

	private static final Logger LOGGER = Logger
			.getLogger(FuncionarioFacadeTest.class);

	@Inject
	private UsuarioFacade usuarioFacade;

	@Inject
	private FuncionarioFacade funcionarioFacade;

	/**
	 * RN-018
	 */
	@Test
	public void testFuncionarioEmptyName() {
		LOGGER.setLevel(Level.INFO);
		Usuario user1 = new Usuario("teste", "123456", "65467689258");
		Funcionario funcionario = null;
		user1.setRole(EUsuarioRole.ROLE_ADMIN.getKey());
		LOGGER.info(user1);
		try {
			usuarioFacade.save(user1);
			funcionario = new Funcionario(user1, "", EFuncionarioFuncao.COPA.getKey());
		} catch (NegocioException e) {
			String msg = e.getMensagem();
			switch (msg) {
			case "msg_required_employee_name": Assert.assertTrue(true);LOGGER.info(msg);break;
			default:Assert.assertTrue(false);break;
			}
		}finally{
			usuarioFacade.delete(user1);
		}
	}
	/**
	 * RN-018
	 */
	@Test
	public void testFuncionarioEmptyFuncao() {
		LOGGER.setLevel(Level.INFO);
		Usuario user1 = new Usuario("teste", "123456", "65467689258");
		Funcionario funcionario = null;
		user1.setRole(EUsuarioRole.ROLE_ADMIN.getKey());
		LOGGER.info(user1);
		try {
			usuarioFacade.save(user1);
			funcionario = new Funcionario(user1, "teste", "");
		} catch (NegocioException e) {
			String msg = e.getMensagem();
			switch (msg) {
			case "msg_required_employee_function": Assert.assertTrue(true);LOGGER.info(msg);break;
			default:Assert.assertTrue(false);break;
			}
		}finally{
			usuarioFacade.delete(user1);
		}
	}
}
