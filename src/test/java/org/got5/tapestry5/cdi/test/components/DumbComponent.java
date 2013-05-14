package org.got5.tapestry5.cdi.test.components;

import javax.inject.Inject;

import org.apache.tapestry5.annotations.Property;
import org.got5.tapestry5.cdi.beans.NamedPojo;
import org.got5.tapestry5.cdi.beans.Pojo;

public class DumbComponent {

	@Inject
	@Property
	private Pojo pojo;
	
	@Inject
	@Property
	private NamedPojo namedPojo;
	
}
