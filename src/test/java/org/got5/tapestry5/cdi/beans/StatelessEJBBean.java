package org.got5.tapestry5.cdi.beans;

import javax.ejb.Local;

@Local
public interface StatelessEJBBean {
	
	String helloStatelessEJB();
	

}
