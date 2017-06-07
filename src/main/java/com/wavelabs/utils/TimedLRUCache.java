package com.wavelabs.utils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for caching Time based Least Recently Used Map. This class would cache
 * the most recent user. Once a threshold time is met, the cache would be
 * emptied and required to be instatiated.
 * 
 * @author bikshapathi
 */
public class TimedLRUCache<K, V> implements Map<K, V>, TimedLRUCacheMBean {
	private Logger logger = LoggerFactory.getLogger(TimedLRUCache.class);
	private final LinkedHashMap<K, TimedValueWrapper<V>> map;
	// ttl in milliseconds
	private final long timeToLive;
	private long misses;
	private int maxSize;
	private long hits;

	public TimedLRUCache(final int maxEntries, final long timeToLive) {
		map = new SynchronizedLRUCacheMap<K, TimedValueWrapper<V>>(100, maxEntries);
		this.timeToLive = timeToLive;
		this.maxSize = maxEntries;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#clear()
	 */
	public void clear() {
		map.clear();
		hits = 0;
		misses = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	public boolean containsKey(Object key) {
		// call this.get()
		return this.get(key) != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	public boolean containsValue(Object value) {
		return map.containsValue(new TimedValueWrapper<Object>(value));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public V get(Object key) {
		TimedValueWrapper<V> wrapper = map.get(key);
		if (wrapper != null && !wrapper.hasExpired()) {
			hits++;
			// reset the time, so that it doesn't expire
			wrapper.whenAdded = System.currentTimeMillis();
			return wrapper.getValue();
		} else {
			misses++;
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#isEmpty()
	 */
	public boolean isEmpty() {
		return map.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#entrySet()
	 */
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		logger.error("Not supported");
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	public void putAll(Map<? extends K, ? extends V> t) {
		logger.error("Not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#values()
	 */
	public Collection<V> values() {
		logger.error("Not supported");
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#keySet()
	 */
	public Set<K> keySet() {
		return map.keySet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public V put(K key, V value) {
		TimedValueWrapper<V> wrapper = map.put(key, new TimedValueWrapper<V>(value));
		if (wrapper != null) {
			return wrapper.getValue();
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	public V remove(Object key) {
		TimedValueWrapper<V> wrapper = map.remove(key);
		if (wrapper != null) {
			return wrapper.getValue();
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#size()
	 */
	public int size() {
		return map.size();
	}

	private class TimedValueWrapper<V1> {

		private V1 value;
		private long whenAdded;

		TimedValueWrapper(V1 value) {
			this.value = value;
			this.whenAdded = System.currentTimeMillis();
		}

		/**
		 * @return
		 */
		public boolean hasExpired() {
			return (whenAdded + timeToLive) < System.currentTimeMillis();
		}

		public V1 getValue() {
			return value;
		}

		// public long getWhenAdded() {
		// return whenAdded;
		// }

		public boolean equals(Object obj) {
			if (!(obj instanceof TimedValueWrapper)) {
				return false;
			}
			return value.equals(((TimedValueWrapper<V>) obj).value);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.czen.util.TimedLRUCacheMBean#maxSize()
	 */
	public int getMaxSize() {
		return maxSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.czen.util.TimedLRUCacheMBean#getSize()
	 */
	public int getSize() {
		return size();
	}
}
