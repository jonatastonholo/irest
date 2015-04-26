package br.cefetmg.es.irest.view.util;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import br.cefetmg.es.irest.model.enuns.EUsuarioRole;

public abstract class BaseAddEditBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	protected WebContextHolder webContextHolder;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	protected boolean isRoleAdmin() {
		return webContextHolder.getUserRole().equals(EUsuarioRole.ROLE_ADMIN.getKey());
	}

	protected abstract void add();
	protected abstract void update();
	protected abstract void cancel();
	protected abstract void redirect();
	protected abstract void save();
	protected abstract void forgeCampos();
	protected abstract void limpaCampos();
	protected abstract void forgeEntity();

	protected FacesContext getFacesConext() {
		return FacesContext.getCurrentInstance();
	}


}
