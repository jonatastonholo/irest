package br.cefetmg.es.irest.view.management.item;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import br.cefetmg.es.irest.controler.facade.ItemFacade;
import br.cefetmg.es.irest.model.entity.Item;
import br.cefetmg.es.irest.model.enuns.ETipoItem;
import br.cefetmg.es.irest.model.util.ImagemUtils;
import br.cefetmg.es.irest.view.management.util.MessagesUtil;
import br.cefetmg.es.irest.view.util.BaseBean;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@Named(value="itemMB")
public class ItemMB extends BaseBean{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(ItemMB.class);

	@Inject
	private ItemFacade itemFacade;

	@Inject
	private FacesContext context;

	private List<Item> items;

	private List<Item> filteredItems;

	private Item selectedItem;

	private Integer id;

	private String itemTipoDesc;

	private boolean editDisabled = false;

	public ItemMB() {
	}

	@PostConstruct
	public void onLoad() {
		this.items = this.itemFacade.findAll();
	}

	public List<Item> getItems() {
		return this.items;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void delete() {
		if (this.selectedItem != null) {
			String url = selectedItem.getDirImagem();
			this.itemFacade.delete(this.selectedItem);
			ImagemUtils.removeOldImage(ImagemUtils.getNomeImagem(url));
			unselectItem();
		}
	}

	public void selectItem(SelectEvent evt) {
		try {
			if (evt.getObject() != null) {
				this.selectedItem = (Item) evt.getObject();
				this.editDisabled = true;
			} else {
				this.selectedItem = null;
				this.editDisabled = false;
			}
		} catch (Exception e) {
			this.selectedItem = null;
			this.editDisabled = false;
			logger.error(e.getMessage(), e);
		}
	}

	public void unselectItem() {
		this.selectedItem = null;
		this.editDisabled = false;
	}

	public Item getSelectedItem() {
		return this.selectedItem;
	}

	public void setSelectedItem(Item selectedItem) {
		this.selectedItem = selectedItem;
	}

	/**
	 * @return the itemTipoDesc
	 */
	public String getItemTipoDesc() {
		return itemTipoDesc;
	}

	/**
	 * Gets the user role desc.
	 *
	 * @param key the role
	 * @return the user role desc
	 */
	public String getItemTipoDesc(String key) {
		return MessagesUtil.getResourceProperty("labels", ETipoItem.getDesc(key), this.context);
	}

	/**
	 * @param itemTipoDesc the itemTipoDesc to set
	 */
	public void setItemRoleDesc(String itemTipoDesc) {
		this.itemTipoDesc = itemTipoDesc;
	}

	/**
	 * @return the filteredItems
	 */
	public List<Item> getFilteredItems() {
		return filteredItems;
	}

	/**
	 * @param filteredItems the filteredItems to set
	 */
	public void setFilteredItems(List<Item> filteredItems) {
		this.filteredItems = filteredItems;
	}

	/**
	 * @return the editDisabled
	 */
	public boolean isEditDisabled() {
		return editDisabled;
	}

	/**
	 * @param editDisabled the editDisabled to set
	 */
	public void setEditDisabled(boolean editDisabled) {
		this.editDisabled = editDisabled;
	}
}
