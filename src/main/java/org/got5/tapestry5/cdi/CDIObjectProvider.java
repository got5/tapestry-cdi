package org.got5.tapestry5.cdi;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.ioc.AnnotationProvider;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.ObjectProvider;
import org.got5.tapestry5.cdi.internal.utils.InternalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CDIObjectProvider implements ObjectProvider {

	private static Logger logger = LoggerFactory.getLogger(CDIObjectProvider.class); 

	@Override
	public <T> T provide(Class<T> objectType,
			AnnotationProvider annotationProvider, ObjectLocator locator) {
		
		if(InternalUtils.isManagedByTapestry(objectType, annotationProvider, locator)){
			return null;
		}
		
		Annotation[] qualifiers = InternalUtils.getFieldQualifiers(objectType,annotationProvider);
		
		logger.debug("Try to load "+objectType+" - qualifiers ? : "+qualifiers.length);
		
		return BeanHelper.get(objectType, qualifiers);
	}

	
}
