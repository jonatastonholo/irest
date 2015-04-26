package br.cefetmg.es.irest.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.cefetmg.es.irest.model.entity.Atendimento;
import br.cefetmg.es.irest.model.entity.ItemPedido;

public interface IItemPedidoRepository extends JpaRepository<ItemPedido, Integer>{

	public List<ItemPedido> findByAtendimento(Atendimento atendimento);

}
