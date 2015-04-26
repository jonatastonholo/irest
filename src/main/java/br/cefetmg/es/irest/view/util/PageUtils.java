package br.cefetmg.es.irest.view.util;

import java.io.IOException;

import javax.faces.context.FacesContext;

public final class PageUtils {

	public static void redirect(FacesContext context, String dir) {
		String path = context.getExternalContext().getApplicationContextPath();
		try {
			if(dir!=null && !dir.trim().equals("")) {
				context.getExternalContext().redirect(path + dir);
			} else {
				context.getExternalContext().redirect(path);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
