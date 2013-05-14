package org.got5.tapestry5.cdi.test.pages;

import javax.inject.Inject;

import org.got5.tapestry5.cdi.beans.Soup;

public class RequestScopePage {

	@Inject
	private Soup soup1;
	
	@Inject
	private Soup soup2;
	
	 public String getRequestScopePojo(){
	    	
	    	if(soup1 !=null){
	    		return "request:" + soup1.getCheckNames();
	    	}
	    	return "";
	    		
	    }
}
