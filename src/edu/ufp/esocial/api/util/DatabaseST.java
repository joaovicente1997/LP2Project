package edu.ufp.esocial.api.util;

public interface DatabaseST<K, V> {

	public void put(K key, V val);

	public V get(K key);

	public Iterable<K> keys();

	public void delete(K key);

}
