package br.cefetmg.es.irest.controler.facade;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import br.cefetmg.es.irest.controler.exception.NegocioException;
import br.cefetmg.es.irest.controler.facade.util.IBaseFacede;
import br.cefetmg.es.irest.model.entity.Item;
import br.cefetmg.es.irest.model.enuns.ETipoItem;
import br.cefetmg.es.irest.model.repositories.IItemRepository;

@Scope(value = WebApplicationContext.SCOPE_SESSION)
@Named(value = "itemFacade")
public class ItemFacade extends IBaseFacede<Item> {

	private static final long serialVersionUID = 1L;

	@Inject
	private IItemRepository itemRepository;

	private List<Item> items;

	private Item item;

	public ItemFacade() {
	}

	@Override
	protected Item update(Item item) throws NegocioException {
		return null;
	}

	@Override
	public List<Item> findAll() {
		this.items = this.itemRepository.findAll();
		return this.items;
	}

	@Override
	public Item save(Item item) throws NegocioException {
		validate(item);
		return itemRepository.save(item);
	}

	@Override
	public void delete(Item item) {
		if (item != null) {
			String dir = item.getDirImagem();
			this.itemRepository.delete(item.getId());
			if(dir != null && !dir.trim().equals("")) {
				removeImage(dir);
			}
		}
	}

	private void removeImage(String dir) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validate(Item item) throws NegocioException {
		if(item == null) {
			throw new NegocioException("msg_invalid_item");
		}

		if(item.getNome() != null && !item.getNome().trim().equals("")) {
			if(hasDependencyOtherName(item)) {
				throw new NegocioException("msg_duplicated_item_name");
			}
		} else {
			throw new NegocioException("msg_required_item_name");
		}

		if (item.getTipoItem() != null && !item.getTipoItem().isEmpty()
				&& !item.getTipoItem().trim().equals("")) {
			String tipo;
			if((tipo = ETipoItem.getDesc(item.getTipoItem())).equals("")) {
				// Chegou a descrição do tipo
				if((tipo = ETipoItem.getKey(item.getTipoItem())).equals("")) {
					throw new NegocioException("msg_invalid_item_tipo");
				} else {
					item.setTipoItem(tipo);
				}

			} else if(ETipoItem.getKey(item.getTipoItem()).equals("")) {
				// Chegou a Key do tipo
				item.setTipoItem(item.getTipoItem());
			}

		} else {
			throw new NegocioException("msg_empty_item_tipo");
		}

		if(item.getPreco() == null) {
			throw new NegocioException("msg_empty_item_preco");
		}

		if(!isValidDirImagem(item)) {
			throw new NegocioException("msg_empty_item_preco");
		}

	}

	private boolean isValidDirImagem(Item item) {
		// TODO Auto-generated method stub
		return true;
	}

	public Item buscarDependenciaNome(String nome) {
		return itemRepository.findByNomeIgnoreCase(nome);
	}

	public boolean hasDependencyOtherName(Item item) {
		Item dep = buscarDependenciaNome(item.getNome());
		return (dep != null && (dep.getId() != item.getId()));
	}

	/**
	 * @return the items
	 */
	public List<Item> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(List<Item> items) {
		this.items = items;
	}

	/**
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	public Item findById(Integer userId) {
		return this.itemRepository.findOne(userId);
	}

}
