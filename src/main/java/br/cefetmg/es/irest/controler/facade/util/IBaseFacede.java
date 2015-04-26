package br.cefetmg.es.irest.controler.facade.util;

import java.io.Serializable;
import java.util.List;

import br.cefetmg.es.irest.controler.exception.NegocioException;

public abstract class IBaseFacede<K> implements Serializable{
	private static final long serialVersionUID = 1L;
	protected abstract List<K> findAll();
	protected abstract K save(K entity) throws NegocioException;
	protected abstract K update(K entity) throws NegocioException;
	protected abstract void delete(K entity);
	protected abstract void validate(K entity) throws NegocioException ;
}
