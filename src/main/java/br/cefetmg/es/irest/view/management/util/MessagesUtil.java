package br.cefetmg.es.irest.view.management.util;

import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public final class MessagesUtil {

	public static String getResourceProperty(String resource, String label, FacesContext context) {
		Application application = context.getApplication();
		ResourceBundle bundle = application.getResourceBundle(context, resource);
		return bundle.getString(label);
	}

	public static String addMessage(String msg, FacesContext context) {
		String message = getResourceProperty("labels", msg, context);
		FacesMessage fMsg = new FacesMessage(message, "");
        FacesContext.getCurrentInstance().addMessage(null, fMsg);

        return message;
	}

}
