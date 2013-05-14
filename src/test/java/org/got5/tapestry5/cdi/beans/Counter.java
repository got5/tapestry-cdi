package org.got5.tapestry5.cdi.beans;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Counter implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AtomicInteger counter = new AtomicInteger();

	public int getCount()
	{
		return counter.get();
	}

	public void increment()
	{
		counter.incrementAndGet();
	}
}
