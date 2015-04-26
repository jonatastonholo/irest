package br.cefetmg.es.test.irest.facade;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.cefetmg.es.irest.controler.facade.ChamadoFacade;
import br.cefetmg.es.test.irest.AbstractDatabaseTest;
/**
 * Testes CDU-008
 * @author Jônatas
 *
 */
public class ChamadoFacadeTest extends AbstractDatabaseTest {

	private static final Logger LOGGER = Logger
			.getLogger(ChamadoFacadeTest.class);

	@Inject
	private ChamadoFacade chamadoFacade;

	/**
	 * Sem RN para testar aqui
	 */

}
