package com.wavelabs.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class to have the Least Recently Used Map algorithm. This extends
 * LinkedHashMap
 * 
 * @author bikshapathi
 * @see TimedLRUCache
 *
 * @param <K>
 * @param <V>
 */
public class SynchronizedLRUCacheMap<K, V> extends LinkedHashMap<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int maxSize;

	public SynchronizedLRUCacheMap(int initialSize, final int maxEntries) {
		super(initialSize, .75f, true);
		this.maxSize = maxEntries;
	}

	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		return size() > maxSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public synchronized V get(Object key) {
		return super.get(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public synchronized V put(K key, V value) {
		return super.put(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	public synchronized void putAll(Map<? extends K, ? extends V> t) {
		super.putAll(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	public synchronized V remove(Object key) {
		return super.remove(key);
	}
}
