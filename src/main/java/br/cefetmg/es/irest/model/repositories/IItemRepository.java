package br.cefetmg.es.irest.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.cefetmg.es.irest.model.entity.Item;

public interface IItemRepository extends JpaRepository<Item, Integer>{

	public Item findByNomeIgnoreCase(String nome);

	public List<Item> findItemByTipoItem(String tipoItem);
}
