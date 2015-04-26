package br.cefetmg.es.irest.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.cefetmg.es.irest.model.entity.Atendimento;

public interface IAtendimentoRepository extends JpaRepository<Atendimento, Integer>{

	@Query("select a from Atendimento a join a.mesa m where m.ipTerminal = :ip")
	public Atendimento findAtendimentoByMesaIpTerminal(@Param("ip") String ipTerminal);

}
