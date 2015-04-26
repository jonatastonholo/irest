package br.cefetmg.es.irest.controler.facade;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import br.cefetmg.es.irest.controler.exception.NegocioException;
import br.cefetmg.es.irest.controler.facade.util.IBaseFacede;
import br.cefetmg.es.irest.controler.util.IpUtils;
import br.cefetmg.es.irest.model.entity.Mesa;
import br.cefetmg.es.irest.model.enuns.ECapacidadeMesa;
import br.cefetmg.es.irest.model.enuns.EStatusMesa;
import br.cefetmg.es.irest.model.repositories.IMesaRepository;

@Scope(value = WebApplicationContext.SCOPE_SESSION)
@Named(value = "mesaFacade")
public class MesaFacade extends IBaseFacede<Mesa>{

	private static final long serialVersionUID = 1L;

	@Inject
	IMesaRepository mesaRepository;


	@Override
	public List<Mesa> findAll() {
		return this.mesaRepository.findAll();
	}

	@Override
	public Mesa save(Mesa entity) throws NegocioException {
		validate(entity);
		return mesaRepository.save(entity);
	}

	@Override
	protected Mesa update(Mesa entity) throws NegocioException {
		return null;
	}

	@Override
	public void delete(Mesa entity) {
		if (entity != null) {
			this.mesaRepository.delete(entity.getId());
		}
	}

	@Override
	protected void validate(Mesa entity) throws NegocioException {
		if(entity == null) {
			throw new NegocioException("msg_invalid_costumer");
		}

		if(entity.getIpTerminal() != null && !entity.getIpTerminal().equals("")) {
			if (hasDependency(entity)) {
				throw new NegocioException("msg_duplicated_table_ip");
			}
			if(!IpUtils.validate(entity.getIpTerminal())) {
				throw new NegocioException("msg_bad_table_ip");
			}
		} else {
			throw new NegocioException("table_ip_required");
		}

		if(entity.getCapacidade() != null
				&& !ECapacidadeMesa.contains(ECapacidadeMesa.getKey(entity.getCapacidade().toString()))){
			throw new NegocioException("msg_invalid_table_capacity");
		} else if(entity.getCapacidade() == null){
			throw new NegocioException("table_capacity_required");
		}

		if(entity.getEstadoMesa() != null && !entity.getEstadoMesa().equals("")) {
			if(!EStatusMesa.contains(entity.getEstadoMesa())) {
				throw new NegocioException("msg_invalid_table_status");
			}
		} else {
			throw new NegocioException("table_status_required");
		}

	}

	private Mesa buscarDependenciaIpTerminal(Mesa mesa) {
		return mesaRepository.findByIpTerminal(mesa.getIpTerminal());
	}

	private boolean hasDependency(Mesa mesa) {
		Mesa m = buscarDependenciaIpTerminal(mesa);
		return (m!=null && (m.getId() != mesa.getId()));
	}
}
