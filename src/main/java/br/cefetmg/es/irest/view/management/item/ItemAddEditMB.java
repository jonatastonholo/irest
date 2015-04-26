package br.cefetmg.es.irest.view.management.item;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import br.cefetmg.es.irest.controler.exception.NegocioException;
import br.cefetmg.es.irest.controler.facade.ItemFacade;
import br.cefetmg.es.irest.model.entity.Item;
import br.cefetmg.es.irest.model.enuns.ETipoItem;
import br.cefetmg.es.irest.model.util.ImagemUtils;
import br.cefetmg.es.irest.view.management.util.MessagesUtil;
import br.cefetmg.es.irest.view.util.BaseBean;
import br.cefetmg.es.irest.view.util.PageUtils;
import br.cefetmg.es.irest.view.util.WebContextHolder;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@Named(value = "itemAddEditMB")
public class ItemAddEditMB extends BaseBean{

	private static final long serialVersionUID = 1L;

	@Inject
	private ItemFacade itemFacade;

	@Inject
	private ItemMB itemMB;

	@Inject
	private FacesContext context;

	@Inject
	private WebContextHolder webContextHolder;


	private Item item;

	private String title;

	private String messageError;

	private String itemTipoDesc;

	private String urlImg="";

	private InputStream imagemIs;

	private String imagemNome;

	private Map<String,String> tipoConverter;

	private boolean addOperation = false;

	@SuppressWarnings("unused")
	private List<String> tipos;

	public ItemAddEditMB() {
		this.item = new Item();
		this.addOperation = true;
	}

	public void add() {
		this.title = MessagesUtil.getResourceProperty("labels", "item_add", this.context);
		this.addOperation = true;
	}

	public void update() {
		addOperation = false;
		webContextHolder.setItem(this.itemMB.getSelectedItem());
		webContextHolder.setEditOp(true);
		this.item = this.itemMB.getSelectedItem();
		this.title = MessagesUtil.getResourceProperty("labels", "item_update", this.context);
		this.itemTipoDesc = MessagesUtil.getResourceProperty("labels", ETipoItem.getDesc(item.getTipoItem()),this.context);
	}

	public void cancel() {
		redirect();
	}

	protected void redirect() {
		ImagemUtils.removeTmpImage(webContextHolder.getNomeImagemItem());
		ImagemUtils.removerTmpDir();
		webContextHolder.flushItem();
		if (this.itemMB != null) {
			this.itemMB.unselectItem();
		}
		PageUtils.redirect(this.context, "/pages/item/list.faces");
	}

	public void save() {
		if (this.item != null) {
			if(webContextHolder.isEditOp()) {
				if(webContextHolder.getItem()!= null) {
					this.item.setDirImagem(webContextHolder.getItem().getDirImagem());
				} else {
					this.item.setDirImagem(ImagemUtils.getCompleteImageDir(context, webContextHolder.getNomeImagemItem()));
				}
				if(webContextHolder.getTipoItemSelected() == null) {
					this.item.setTipoItem(webContextHolder.getItem().getTipoItem());
				} else {
					this.item.setTipoItem(webContextHolder.getTipoItemSelected().toUpperCase());
				}
			}
			this.imagemNome = webContextHolder.getNomeImagemItem();
			this.imagemIs = webContextHolder.getImagemItem();
			updateTipoItem(this.item);
			forgeImagem(item);
			if (this.item.getId() == null) {
				// Add
				try {
					this.itemFacade.save(this.item);
					if(imagemIs != null) {
						ImagemUtils.salvarImagem(this.imagemNome, this.imagemIs, context);
					}
					redirect();
				} catch (NegocioException e) {
					setMessage(e.getMensagem());
				}
			} else {
				// Update
				try {
					this.itemFacade.save(this.item);
					if(imagemIs != null) {
						ImagemUtils.salvarImagem(this.imagemNome, this.imagemIs, context);
					}
					redirect();
				} catch (NegocioException e) {
					setMessage(e.getMensagem());
				}
			}
		}
	}

	private void forgeImagem(Item item) {
		if(item.getDirImagem() != null && !item.getDirImagem().trim().equals("")) {
			if(this.imagemNome != null && !item.getDirImagem().equalsIgnoreCase(ImagemUtils.PATH_IMAGE + this.imagemNome)) {
				ImagemUtils.removeOldImage(ImagemUtils.getNomeImagem(item.getDirImagem()));
				item.setDirImagem(ImagemUtils.PATH_IMAGE + this.imagemNome);
			}
		} else {
			item.setDirImagem(ImagemUtils.PATH_IMAGE + webContextHolder.getNomeImagemItem());
		}
	}


	private void updateTipoItem(Item item) {
		if(this.item.getTipoItem() == null && itemTipoDesc != null) {
			this.item.setTipoItem(ETipoItem.getKey(tipoConverter.get(itemTipoDesc)));
		} else if(ETipoItem.getDesc(item.getTipoItem()).equals("")){ //o tipo é uma desc
			this.item.setTipoItem(ETipoItem.getKey(ETipoItem.getKey(item.getTipoItem())));
		}
	}

	public void setMessage(String msgError) {
		this.messageError = MessagesUtil.getResourceProperty("labels", msgError, this.context);
		FacesMessage fMsg = new FacesMessage(this.messageError, "");
        FacesContext.getCurrentInstance().addMessage(null, fMsg);
	}

	public String getMessageError() {
		return this.messageError;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}


	/**
	 * @param item the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	/**
	 * Gets the tipos.
	 *
	 * @return the tipos
	 */
	public List<String> getTipos() {
		List<String> descs = new ArrayList<String>();
		tipoConverter = new HashMap<String, String>();

		for(String s : ETipoItem.descs()) {
			String key = MessagesUtil.getResourceProperty("labels", s, this.context);
			descs.add(key);
			tipoConverter.put(key, s);
		}
		return descs;
	}

	/**
	 * @return the itemTipoDesc
	 */
	public String getItemTipoDesc() {
		if(!isAddOperation() && item != null && this.itemTipoDesc == null) {
			this.itemTipoDesc =  MessagesUtil.getResourceProperty("labels", ETipoItem.getDesc(item.getTipoItem()), this.context);
		}
		return this.itemTipoDesc;
	}

	/**
	 * @param itemTipoDesc the itemTipoDesc to set
	 */
	public void setItemTipoDesc(String itemTipoDesc) {
		this.itemTipoDesc = itemTipoDesc;
	}

	private boolean isAddOperation() {
		addOperation =
				(webContextHolder.isRoleAdmin() || webContextHolder.isRoleFuncionario()) && this.itemMB.getSelectedItem() == null ;

		return addOperation;
	}

	public void handleFileUpload(FileUploadEvent event) {
		if(event.getFile() != null) {
			setMessage("msg_item_img_uploaded");
			try {
				this.imagemIs = event.getFile().getInputstream();
				webContextHolder.setImagemItem(event.getFile().getInputstream());
				this.imagemNome = event.getFile().getFileName();
				ImagemUtils.salvarImagemDiretorioTmp(imagemNome, this.imagemIs, context);
				webContextHolder.setNomeImagemItem(imagemNome);

			} catch (IOException e) {
				setMessage("msg_item_img_uploaded_error");
				e.printStackTrace();
			}
		} else {
			setMessage("msg_item_img_uploaded_error");
		}
    }

	/**
	 * @return the urlImg
	 */
	public String getUrlImg() {
		if(this.item.getDirImagem() != null && !this.item.getDirImagem().trim().equals("")) {
			this.urlImg = this.item.getDirImagem();
		} else {
			this.urlImg = ImagemUtils.getContextPath() + ImagemUtils.TMP_PATH_IMAGE + webContextHolder.getNomeImagemItem();
		}
		return urlImg;
	}

	/**
	 * @param urlImg the urlImg to set
	 */
	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}

	public void selectTipoItem(AjaxBehaviorEvent evt) {
		try {
			if (!evt.getComponent().getAttributes().get("value").toString().trim().equalsIgnoreCase("")) {
				webContextHolder.setTipoItemSelected(evt.getComponent().getAttributes().get("value").toString());
			} else {
				webContextHolder.setTipoItemSelected(null);
			}
		} catch (Exception e) {
			webContextHolder.setTipoItemSelected(null);
		}
	}

	public String getDescTipoItem(String code) {
		return MessagesUtil.getResourceProperty("labels", ETipoItem.getDesc(code),FacesContext.getCurrentInstance());
	}
}
