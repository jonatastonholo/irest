package br.cefetmg.es.test.irest.facade;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.cefetmg.es.irest.controler.facade.AtendimentoFacade;
import br.cefetmg.es.test.irest.AbstractDatabaseTest;
/**
 * Testes CDU-007
 * @author Jônatas
 *
 */
public class AtendimentoFacadeTest extends AbstractDatabaseTest {

	private static final Logger LOGGER = Logger
			.getLogger(AtendimentoFacadeTest.class);

	@Inject
	private AtendimentoFacade atendimentoFacade;

	/**
	 * Sem regra de negócio para testar aqui*/
}
