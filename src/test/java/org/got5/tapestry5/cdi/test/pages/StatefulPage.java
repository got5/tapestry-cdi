package org.got5.tapestry5.cdi.test.pages;

import javax.inject.Inject;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.CleanupRender;
import org.got5.tapestry5.cdi.beans.StatefulEJBBean;

public class StatefulPage {

	@Inject
	private StatefulEJBBean statefulBean;

	@BeginRender
	public void rend(MarkupWriter mw) {
		
		mw.writeRaw(String.valueOf(statefulBean.num()) + String.valueOf(statefulBean.inc())
				+ String.valueOf(statefulBean.num())+"stateful");//prepend XXXstateful at the root of the page

	}

	@CleanupRender
	public void clean() {
		
	}

}
