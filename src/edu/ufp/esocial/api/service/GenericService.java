package edu.ufp.esocial.api.service;

import java.util.List;

public abstract interface GenericService<K extends Comparable<K>, T> {

	public void create(T model);

	public T findByKey(K key);

	public List<T> getAll();

	public void update(T model);

	public void delete(K key);

}
