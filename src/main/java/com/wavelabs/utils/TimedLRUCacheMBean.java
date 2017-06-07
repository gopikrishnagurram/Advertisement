package com.wavelabs.utils;

/**
 * Interface for Timed LRU map
 * 
 * @author bikshapathi
 *
 */
public interface TimedLRUCacheMBean {

	/* Various stats */
	public int getSize();

	public int getMaxSize();

	/** Clears the cache */
	public void clear();
}
