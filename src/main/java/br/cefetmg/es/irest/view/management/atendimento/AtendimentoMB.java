package br.cefetmg.es.irest.view.management.atendimento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import br.cefetmg.es.irest.controler.exception.NegocioException;
import br.cefetmg.es.irest.controler.facade.AtendimentoFacade;
import br.cefetmg.es.irest.model.entity.Atendimento;
import br.cefetmg.es.irest.model.entity.Item;
import br.cefetmg.es.irest.model.entity.ItemPedido;
import br.cefetmg.es.irest.model.entity.Mesa;
import br.cefetmg.es.irest.model.enuns.EFormaPagamento;
import br.cefetmg.es.irest.model.enuns.EStatusPedido;
import br.cefetmg.es.irest.model.enuns.ETipoItem;
import br.cefetmg.es.irest.view.management.util.MessagesUtil;
import br.cefetmg.es.irest.view.util.BaseBean;
import br.cefetmg.es.irest.view.util.PageUtils;
import br.cefetmg.es.irest.view.util.WebContextHolder;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@Named(value="atendimentoMB")
public class AtendimentoMB extends BaseBean{
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(AtendimentoMB.class);

	@Inject
	private AtendimentoFacade atendimentoFacade;

	@Inject
	private WebContextHolder webContextHolder;

	private String message;

	private List<ItemPedido> itensPedido;

	private List<ItemPedido> filteredItensPedido;

	private Atendimento atendimento;

	private Integer id;

	private Mesa mesa;

	private List<String> tiposItens;

	private Map<String,String> tipoConverter;

	private List<Item> filteredItems;

	private Item selectedItem;

	private Integer quantidade;

	private List<String> formasPagamento;

	private String formaPagamento;

	public AtendimentoMB() {
	}

	@PostConstruct
	public void onLoad() {
		tiposItens = new ArrayList<String>();
		formasPagamento = new ArrayList<String>();
		atendimento = this.atendimentoFacade.findAtendimentoByMesaIpTerminal(webContextHolder.getIpLogado());
		webContextHolder.setAtendimento(atendimento);
		if(atendimento != null) {
			this.itensPedido = atendimento.getItensPedido();
		} else {
			this.itensPedido = new ArrayList<ItemPedido>();
		}
	}

	public List<ItemPedido> getItensPedido() {
		return this.itensPedido;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void save() {
		Map<Integer, ItemPedido> map = webContextHolder.getMapItensPedido();
		List<ItemPedido> list = new ArrayList<ItemPedido>();
		if(!map.isEmpty()) {
			Set<Entry<Integer, ItemPedido>> s =  webContextHolder.getMapItensPedido().entrySet();
			Iterator<Entry<Integer, ItemPedido>> i = s.iterator();

			while(i.hasNext()) {
				list.add(i.next().getValue());
			}
		}

		if(list!= null && !list.isEmpty()) {
			mesa = this.atendimentoFacade.findMesaByIpTerminal(webContextHolder.getIpLogado());
			if(mesa != null) {
				Atendimento a = new Atendimento(mesa, list, false, "");
				try {
					a = this.atendimentoFacade.save(a);
					webContextHolder.setAtendimento(a);
					redirect("/pages/pedidos/order.faces");
				} catch (NegocioException e) {
					setMessage(e.getMensagem());
				}
			}

		}
	}

	public void setMessage(String msgError) {
		this.message = MessagesUtil.getResourceProperty("labels", msgError, FacesContext.getCurrentInstance());
		FacesMessage fMsg = new FacesMessage(this.message, "");
        FacesContext.getCurrentInstance().addMessage(null, fMsg);
	}

	public void cancel() {
		webContextHolder.flushItemPedido();
		redirect("/pages/main.faces");
	}

	private void redirect(String url) {
		PageUtils.redirect(FacesContext.getCurrentInstance(), url);
	}


	public List<ItemPedido> getFilteredItensPedido() {
		return filteredItensPedido;
	}

	public void setFilteredItensPedido(List<ItemPedido> filteredItensPedido) {
		this.filteredItensPedido = filteredItensPedido;
	}
	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}

	public void setItensPedido(List<ItemPedido> itensPedido) {
		this.itensPedido = itensPedido;
	}

	private Atendimento findAtendimentoByMesaIpTerminal(String ipTerminal) {
		return this.atendimentoFacade.findAtendimentoByMesaIpTerminal(ipTerminal);
	}

	public boolean existeItensAtendimento() {
		Atendimento a = findAtendimentoByMesaIpTerminal(this.webContextHolder.getIpLogado());
		return (a != null && a.getItensPedido() != null);
	}

	/**
	 * @return the tiposItens
	 */
	public List<String> getTiposItens() {
		tiposItens = new ArrayList<String>();
		tipoConverter = new HashMap<String, String>();

		for(String s : ETipoItem.descs()) {
			String key = MessagesUtil.getResourceProperty("labels", s, FacesContext.getCurrentInstance());
			tiposItens.add(key);
			tipoConverter.put(key, s);
		}
		return tiposItens;
	}

	/**
	 * @param tiposItens the tiposItens to set
	 */
	public void setTiposItens(List<String> tiposItens) {
		this.tiposItens = tiposItens;
	}

	public List<Item> obterItensPorTipo(String tipo) {
		String desc = tipoConverter.get(tipo);
		return this.atendimentoFacade.findItensByType(ETipoItem.getKey(desc));
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

	public void selectItem(SelectEvent evt) {
		try {
			if (evt.getObject() != null) {
				this.selectedItem = (Item) evt.getObject();
			} else {
				this.selectedItem = null;
			}
		} catch (Exception e) {
			this.selectedItem = null;
			logger.error(e.getMessage(), e);
		}
	}

	public void unselectItem() {
		this.selectedItem = null;
	}

	public Item getSelectedItem() {
		return this.selectedItem;
	}

	public void setSelectedItem(Item selectedItem) {
		this.selectedItem = selectedItem;
	}



	/**
	 * @return the quantidade
	 */
	public Integer getQuantidade(Item item) {
		return quantidade;
	}

	/**
	 * @param quantidade the quantidade to set
	 */
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public String getDescTipoItem(String key) {
		return MessagesUtil.getResourceProperty("labels", ETipoItem.getDesc(tipoConverter.get(key)), FacesContext.getCurrentInstance());
	}

	public void updateQuantidade(ValueChangeEvent event) {
		quantidade = Integer.parseInt(event.getNewValue().toString());
	}

	public void refreshQuantidade(Item item) {
		ItemPedido itemPedido = new ItemPedido();
		item.setQuantidade(quantidade);
		itemPedido.setItem(item);
		itemPedido.setQuantidade(item.getQuantidade());
		itemPedido.setStatusPedido(EStatusPedido.SOLICITADO_CLIENTE.getKey());
		refreshTotal(item,itemPedido);
		refreListItensPedido(item, itemPedido);
		updateTotalAtendimento();
	}

	private void refreListItensPedido(Item item, ItemPedido itemPedido) {
		if(webContextHolder.getMapItensPedido().containsKey(item.getId())) {
			webContextHolder.getMapItensPedido().remove(item.getId());
		}
		webContextHolder.getMapItensPedido().put(item.getId(), itemPedido);
	}

	private void refreshTotal(Item item, ItemPedido itemPedido) {
		BigDecimal quant = new BigDecimal(itemPedido.getQuantidade().toString());
		if(quant != null) {
			itemPedido.setTotal(quant.multiply(item.getPreco()));
		}
	}

	private void updateTotalAtendimento() {
		BigDecimal total = new BigDecimal(0);
		Set<Entry<Integer, ItemPedido>> s =  webContextHolder.getMapItensPedido().entrySet();
		Iterator<Entry<Integer, ItemPedido>> i = s.iterator();

		while(i.hasNext()) {
			total = total.add(i.next().getValue().getTotal());
		}

		webContextHolder.setTotalAtendimento(total);
	}

	public BigDecimal getTotalItemPedido(ItemPedido itemPedido) {
		BigDecimal total = new BigDecimal(0);
		Integer quantidade = itemPedido.getQuantidade();
		total = itemPedido.getItem().getPreco().multiply(new BigDecimal(quantidade));
		return total;
	}

	public BigDecimal getTotalAtendimento(Atendimento atendimento) {
		BigDecimal total = new BigDecimal(0);
		for(ItemPedido i : atendimento.getItensPedido()) {
			total = total.add(i.getItem().getPreco().multiply(new BigDecimal(quantidade)));
		}
		return total;
	}

	public BigDecimal getTaxaServico(Atendimento atendimento) {
		BigDecimal total = new BigDecimal(0);
		BigDecimal porc = new BigDecimal(0.1);
		total = getTotalAtendimento(atendimento);
		total = total.multiply(porc);
		return total;
	}

	public BigDecimal getTotalTaxaServico(Atendimento atendimento) {
		BigDecimal total = new BigDecimal(0);

		total = getTotalAtendimento(atendimento);
		total = total.add(getTaxaServico(atendimento));
		return total;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the atendimento
	 */
	public Atendimento getAtendimento() {
		this.atendimento = webContextHolder.getAtendimento();
		return atendimento;
	}

	/**
	 * @param atendimento the atendimento to set
	 */
	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}

	/**
	 * @return the formasPagamento
	 */
	public List<String> getFormasPagamento() {
		formasPagamento = EFormaPagamento.descs();
		return formasPagamento;
	}

	/**
	 * @param formasPagamento the formasPagamento to set
	 */
	public void setFormasPagamento(List<String> formasPagamento) {
		this.formasPagamento = formasPagamento;
	}

	/**
	 * @return the formaPagamento
	 */
	public String getFormaPagamento() {
		formaPagamento = webContextHolder.getFormaPagamento();
		return formaPagamento;
	}

	/**
	 * @param formaPagamento the formaPagamento to set
	 */
	public void setFormaPagamento(String formaPagamento) {
		webContextHolder.setFormaPagamento(formaPagamento);
		this.formaPagamento = formaPagamento;
	}

	public String getLabelFormaPagamento(String forma) {
		return MessagesUtil.getResourceProperty("labels", forma, FacesContext.getCurrentInstance());
	}
}
