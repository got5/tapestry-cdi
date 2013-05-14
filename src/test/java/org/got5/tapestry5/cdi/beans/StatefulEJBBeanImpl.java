package org.got5.tapestry5.cdi.beans;

import javax.ejb.Remove;
import javax.ejb.Stateful;

@Stateful
public class StatefulEJBBeanImpl implements StatefulEJBBean{
	
	private int num = 0;

	@Override
	public int num() {
		return num;
	}
	@Override
	public int inc(){
		return ++num;
	}
	
	@Override
	public int reset(){
		return (num = 0);
	}
	
	
	
}
