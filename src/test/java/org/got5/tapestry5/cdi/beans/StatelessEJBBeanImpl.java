package org.got5.tapestry5.cdi.beans;

import javax.ejb.Stateless;

@Stateless
public class StatelessEJBBeanImpl implements StatelessEJBBean{

	@Override
	public String helloStatelessEJB() {
		return "Hello Stateless EJB";
	}

	
}
