package br.cefetmg.es.irest.controler.facade;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import br.cefetmg.es.irest.controler.exception.NegocioException;
import br.cefetmg.es.irest.controler.facade.util.IBaseFacede;
import br.cefetmg.es.irest.model.entity.Chamado;
import br.cefetmg.es.irest.model.entity.Mesa;
import br.cefetmg.es.irest.model.repositories.IChamadoRepository;
import br.cefetmg.es.irest.model.repositories.IMesaRepository;

@Scope(value = WebApplicationContext.SCOPE_SESSION)
@Named(value = "chamadoFacade")
public class ChamadoFacade extends IBaseFacede<Chamado>{

	private static final long serialVersionUID = 1L;

	@Inject
	private IChamadoRepository chamadoRepository;

	@Inject
	private IMesaRepository mesaRepository;

	@Override
	public List<Chamado> findAll() {
		return chamadoRepository.findByAtendido(false);
	}

	@Override
	public Chamado save(Chamado entity) throws NegocioException {
		validate(entity);
		entity.setAtendido(false);
		return chamadoRepository.save(entity);
	}

	@Override
	public Chamado update(Chamado entity) throws NegocioException {
		validateUpdate(entity);
		entity.setAtendido(true);
		return chamadoRepository.save(entity);
	}

	@Override
	protected void delete(Chamado entity) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void validate(Chamado entity) throws NegocioException {
		if(entity != null) {

			if(entity.getMesa() != null && entity.getMesa().getId() != null) {
				if(entity.getMesa().getIpTerminal() == null || entity.getMesa().getIpTerminal().trim().equals("")) {
					throw new NegocioException("request_no_ip");
				}
			} else {
				throw new NegocioException("request_no_table");

			}

			if(entity.isAtendido()) {
				throw new NegocioException("request_was_answered");
			}

			if(possuiChamadoAberto(entity.getMesa())) {
				throw new NegocioException("request_not_answered_yet");
			}

		}

	}

	private void validateUpdate(Chamado entity) throws NegocioException {
		if(entity != null) {

			if(entity.getMesa() != null && entity.getMesa().getId() != null) {
				if(entity.getMesa().getIpTerminal() == null || entity.getMesa().getIpTerminal().trim().equals("")) {
					throw new NegocioException("request_no_ip");
				}
			} else {
				throw new NegocioException("request_no_table");

			}

			if(entity.isAtendido()) {
				throw new NegocioException("request_was_answered");
			}

		}

	}

	private boolean possuiChamadoAberto(Mesa mesa) {
		Chamado chamado = this.chamadoRepository.findByMesa(mesa);
		return (chamado != null && !chamado.isAtendido());
	}

	public Mesa findMesaByIpTerminal(String ipTerminal) {
		return this.mesaRepository.findByIpTerminal(ipTerminal);
	}

}
