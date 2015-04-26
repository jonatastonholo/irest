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
 * Testes CDU-005
 * @author Jônatas
 *
 */
public class UsuarioFacadeTest extends AbstractDatabaseTest {

	private static final Logger LOGGER = Logger
			.getLogger(UsuarioFacadeTest.class);

	@Inject
	private UsuarioFacade usuarioFacade;

	@Inject
	private FuncionarioFacade funcionarioFacade;

	/**
	 * RN-001
	 */
	@Test
	public void testUsuarioCpfDuplicado() {
		LOGGER.setLevel(Level.INFO);
		Usuario user1 = new Usuario("teste", "123456", "65467689258");
		user1.setRole(EUsuarioRole.ROLE_ADMIN.getKey());
		LOGGER.info(user1);

		Usuario user2 = new Usuario("Teste 2", "123456", "65467689258");
		user1.setRole(EUsuarioRole.ROLE_ADMIN.getKey());
		LOGGER.info(user1);
		try {
			usuarioFacade.save(user1);
			usuarioFacade.save(user2);
		} catch (NegocioException e) {
			String msg = e.getMensagem();
			switch (msg) {
			case "msg_duplicated_cpf": Assert.assertTrue(true);LOGGER.info(msg);break;
			default:Assert.assertTrue(false);break;
			}
		}finally{
			usuarioFacade.delete(user1);
			usuarioFacade.delete(user2);
		}


	}

	/**
	 * RN-002
	 */
	@Test
	public void testUsuarioPass() {
		LOGGER.setLevel(Level.INFO);
		Usuario user1 = new Usuario("teste", "12345", "65467689258");
		user1.setRole(EUsuarioRole.ROLE_ADMIN.getKey());
		LOGGER.info(user1);

		try {
			usuarioFacade.save(user1);
		} catch (NegocioException e) {
			String msg = e.getMensagem();
			switch (msg) {
			case "msg_small_pass": Assert.assertTrue(true);LOGGER.info(msg);break;
			default:Assert.assertTrue(false);
				break;
			}
		}finally{
			usuarioFacade.delete(user1);
		}
	}

	/**
	 * RN-017
	 */
	@Test
	public void testUsuarioEmptyCpf() {
		LOGGER.setLevel(Level.INFO);
		Usuario user1 = new Usuario("teste", "12345", "");
		user1.setRole(EUsuarioRole.ROLE_ADMIN.getKey());
		LOGGER.info(user1);

		try {
			usuarioFacade.save(user1);
		} catch (NegocioException e) {
			String msg = e.getMensagem();
			switch (msg) {
			case "msg_empty_cpf": Assert.assertTrue(true);LOGGER.info(msg);break;
			default:Assert.assertTrue(false);
			break;
			}
		}finally{
			usuarioFacade.delete(user1);
		}
	}

	/**
	 * RN-017
	 */
	@Test
	public void testUsuarioEmptyPass() {
		LOGGER.setLevel(Level.INFO);
		Usuario user1 = new Usuario("teste", "", "65467689258");
		user1.setRole(EUsuarioRole.ROLE_ADMIN.getKey());
		LOGGER.info(user1);

		try {
			usuarioFacade.save(user1);
		} catch (NegocioException e) {
			String msg = e.getMensagem();
			switch (msg) {
			case "msg_empty_pass": Assert.assertTrue(true);LOGGER.info(msg);break;
			default:Assert.assertTrue(false);
			break;
			}
		}finally{
			usuarioFacade.delete(user1);
		}
	}


	/**
	 * RN-021
	 */
	@Test
	public void testUsuarioCpfInvalido() {
		LOGGER.setLevel(Level.INFO);
		Usuario user1 = new Usuario("teste", "123456", "65467689258");
		user1.setRole(EUsuarioRole.ROLE_ADMIN.getKey());
		LOGGER.info(user1);

		try {
			usuarioFacade.save(user1);
		} catch (NegocioException e) {
			String msg = e.getMensagem();
			switch (msg) {
			case "msg_invalid_cpf": Assert.assertTrue(true);LOGGER.info(msg);break;
			default: Assert.assertTrue(false);
				break;
			}
		}finally{
			usuarioFacade.delete(user1);
		}
	}

	/**
	 * RN-033
	 */
	@Test
	public void testUsuarioRemoverCliFunc() {
		LOGGER.setLevel(Level.INFO);
		Usuario user1 = new Usuario("teste", "123456", "65467689258");
		Funcionario funcionario = null;
		user1.setRole(EUsuarioRole.ROLE_FUNCIONARIO.getKey());
		LOGGER.info(user1);
		try {
			usuarioFacade.save(user1);
			funcionario = new Funcionario(user1, "Teste", EFuncionarioFuncao.COZINHA.getKey());
			funcionarioFacade.save(funcionario);
			LOGGER.info(funcionario);
		} catch (NegocioException e) {
			LOGGER.info(e.getMensagem());
		}

		Integer idUsuario = user1.getId();
		String cpf = user1.getCpf();

		usuarioFacade.delete(user1);

		if(usuarioFacade.findById(idUsuario) != null) {
			Assert.assertTrue(false);
		} else if(funcionarioFacade.findByCpf(cpf) != null){
			Assert.assertTrue(false);
		} else {
			Assert.assertTrue(true);
		}
	}

}
