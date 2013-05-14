package org.got5.tapestry5.cdi.extension;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import org.apache.tapestry5.internal.InternalConstants;
import org.got5.tapestry5.cdi.BeanHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An spi extension to exclude tapestry resources from CDI management
 * Veto each Tapestry pages, components and mixins to avoid CDI try to manage them
 * Without this extension, CDI will complain about Tapestry services not well loaded in injection points from the webapp's pages,components and mixins 
 */
public class TapestryExtension implements Extension {

	private static Logger logger = LoggerFactory.getLogger(TapestryExtension.class); 

	/**
	 * Exclude Tapestry resources from CDI management
	 * Veto each Tapestry pages, components and mixins
	 * @param pat a ProcessAnnotatedType
	 */
	protected <T> void excludeTapestryResources(@Observes final ProcessAnnotatedType<T> pat){
		String annotatedTypeClassName = pat.getAnnotatedType().getJavaClass().getName(); 
		logger.debug("Annotated type : "+annotatedTypeClassName);
		for (String subpackage : InternalConstants.SUBPACKAGES){
			if(annotatedTypeClassName.contains("."+subpackage+".")){
				logger.debug("Tapestry page/component/mixins found! - will be exclude from CDI management : "+annotatedTypeClassName);
				pat.veto();
			}
		}
	}
}
