package br.cefetmg.es.irest.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.cefetmg.es.irest.model.entity.Chamado;
import br.cefetmg.es.irest.model.entity.Mesa;

public interface IChamadoRepository extends JpaRepository<Chamado, Integer>{

	List<Chamado> findByAtendido(boolean atendido);

	Chamado findByMesa(Mesa mesa);

}
