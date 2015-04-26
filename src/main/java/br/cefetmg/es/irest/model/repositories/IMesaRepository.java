package br.cefetmg.es.irest.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.cefetmg.es.irest.model.entity.Mesa;

public interface IMesaRepository  extends JpaRepository<Mesa, Integer>{

	public Mesa findByIpTerminal(String ipTerminal);

}
