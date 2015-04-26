package br.cefetmg.es.irest.view.management.atendimento;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import br.cefetmg.es.irest.view.util.BaseAddEditBean;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@Named(value="atendimentoAddEditMB")
public class AtendimentoAddEditMB extends BaseAddEditBean{

	private static final long serialVersionUID = 1L;

	@Override
	protected void add() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void cancel() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void redirect() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void save() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void forgeCampos() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void limpaCampos() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void forgeEntity() {
		// TODO Auto-generated method stub

	}

}
