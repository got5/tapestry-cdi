package org.got5.tapestry5.cdi.test.pages;

import javax.inject.Inject;

import org.apache.tapestry5.annotations.Property;
import org.got5.tapestry5.cdi.beans.ws.HelloWorldService;

public class WSPage {

	@Inject
	private HelloWorldService client;
	
	@Property
	private String message;

	public void onActivate(){
		message = client.sayHelloToName("John");
	}

}
