package edu.ufp.esocial.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import edu.ufp.esocial.api.service.GenericService;
import edu.ufp.esocial.api.util.DatabaseST;

public abstract class GenericServiceImpl<K extends Comparable<K>, T> implements GenericService<K, T> {

	protected DatabaseST<K, T> databaseST;

	public GenericServiceImpl(DatabaseST<K, T> databaseST) {
		this.databaseST = databaseST;
	}

	@Override
	public T findByKey(K key) {
		return this.databaseST.get(key);
	}

	@Override
	public List<T> getAll() {
		List<T> list = new ArrayList<>();
		for (K key : this.databaseST.keys()) {
			list.add(this.databaseST.get(key));
		}
		return list;
	}

}
