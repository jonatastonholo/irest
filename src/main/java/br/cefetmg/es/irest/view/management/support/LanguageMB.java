package br.cefetmg.es.irest.view.management.support;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

@Scope(value = WebApplicationContext.SCOPE_SESSION)
@ManagedBean
public class LanguageMB implements Serializable {

	private static final long serialVersionUID = 1L;
	private String localeCode;

	private static Map<String, Locale> countries;

	public LanguageMB() {
	}
	
	@PostConstruct
    public void init() {
		
		countries = new LinkedHashMap<String, Locale>();
		countries.put("English", new Locale("en"));
		countries.put("Português", new Locale("pt"));
    }
 

	public Map<String, Locale> getCountries() {
		return countries;
	}

	public String getLocaleCode() {
		if ((this.localeCode == null)
				&& (FacesContext.getCurrentInstance().getViewRoot() != null)
				&& (FacesContext.getCurrentInstance().getViewRoot().getLocale() != null)) {
			this.localeCode = FacesContext.getCurrentInstance().getViewRoot()
					.getLocale().getLanguage();
		}

		return this.localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	public void localeCodeChanged(AjaxBehaviorEvent e) {
		String newLocaleValue = ((SelectOneMenu) e.getSource()).getValue() + "";

		for (Entry<String, Locale> entry : countries.entrySet()) {
			if (entry.getValue().toString().equals(newLocaleValue)) {
				FacesContext.getCurrentInstance().getViewRoot()
						.setLocale((Locale) entry.getValue());
			}
		}
	}
}
