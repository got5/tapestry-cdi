package org.got5.tapestry5.cdi;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

import org.got5.tapestry5.cdi.extension.BeanManagerHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BeanHelper {

	private static Logger logger = LoggerFactory.getLogger(BeanHelper.class); 

	private BeanHelper() {
		// no-op
	}

	/**
	 * Get a BeanInstance from a type and its qualifiers
	 * @param clazz the class representing the bean's type 
	 * @param qualifiers an array representing the bean's qualifiers
	 * @return a BeanInstance
	 */
	public static BeanInstance getInstance(final Class<?> clazz, final Annotation[] qualifiers) {
		final BeanManager beanManager = getBeanManager();
		final Set<Bean<?>> beans = beanManager.getBeans(clazz, qualifiers);
		logger.debug("getInstance() - beans found : "+beans+" for class "+clazz.getSimpleName());
		if (beans == null || beans.isEmpty()) {
			return null;
		}
		final Bean<?> bean = beanManager.resolve(beans);
		logger.debug("getInstance() - bean resolved : "+bean);
		final CreationalContext<?> creationalContext = beanManager.createCreationalContext(bean);
		final Object result = beanManager.getReference(bean, clazz, creationalContext);
		return new BeanInstance(result, creationalContext, Dependent.class.equals(bean.getScope()));
	}


	/**
	 * Handy method that returns a bean from its type and qualifiers
	 * @param clazz the class representing the bean's type 
	 * @param qualifiers an array representing the bean's qualifiers
	 * @return a instance of the bean 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(final Class<T> clazz, final Annotation[] qualifiers) {
		final BeanManager beanManager = getBeanManager();
		Annotation[] beanQualifiers = qualifiers;
		if(beanQualifiers == null || beanQualifiers.length == 0){
			beanQualifiers = new Annotation[]{};
		}
		Set<Bean<?>> beans = beanManager.getBeans(clazz, beanQualifiers);
		if(beans!=null && beans.size()>0) {
			Bean<T> bean = (Bean<T>) beanManager.resolve(beans);
			logger.debug("Bean found : "+bean);
			CreationalContext<T> ctx = beanManager.createCreationalContext(bean);
			T o = clazz.cast(beanManager.getReference(bean, clazz, ctx));
			logger.debug("Found and returning: "+clazz.getCanonicalName());
			return o;
		}
		return null;
	}
	
	/**
	 * Handy method that returns a bean without qualifiers from its type
	 * @param clazz the class representing the bean's type 
	 * @return a instance of the bean 
	 */
	public static <T> T get(Class<T> clazz) {
		return get(clazz, null);
	}

	/**
	 * Filter qualifiers from a list of annotations
	 * @param annotations an annotation array that may contain qualifiers
	 * @return an annotation array that contains exclusively qualifiers
	 */
	public static Annotation[] getQualifiers(final Annotation[] annotations) {
		final BeanManager bm = getBeanManager();
		final List<Annotation> qualifiers = new ArrayList<Annotation>();
		for (Annotation annotation : annotations) {
			logger.debug("Check annotation : "+annotation+"...");
			if (bm.isQualifier(annotation.annotationType())) {
				qualifiers.add(annotation);
			}
		}
		return qualifiers.toArray(new Annotation[qualifiers.size()]);
	}

	/**
	 * Get all qualifiers of a type
	 * @param clazz, the class of which we retrieve the qualifiers
	 * @return an annotation array
	 */
	public static Annotation[] getQualifiers(final Class<?> clazz) {
		final BeanManager beanManager = getBeanManager();
		final List<Annotation> qualifiers = new ArrayList<Annotation>();
		// Get all qualified bean by using the @Any annotation
		final Set<Bean<?>> beans = beanManager.getBeans(clazz, 
				new Annotation[]{
				new Any() {
					@Override
					public Class<? extends Annotation> annotationType() {
						return Any.class;
					}
				}
		}
				);
		logger.debug("Qualified bean found for class "+clazz.getSimpleName());
		for (Bean<?> bean : beans) {
			for (Annotation annotation : bean.getQualifiers()) {
				if(! annotation.annotationType().isAssignableFrom(Any.class)){
					logger.debug("==> ["+annotation.annotationType()+"] Qualifier found for bean "+bean.toString());
					qualifiers.add(annotation);
				}
			}
		}
		return qualifiers.toArray(new Annotation[qualifiers.size()]);

	}

	/**
	 * @return the BeanManager
	 */
	private static BeanManager getBeanManager() {
		return BeanManagerHolder.get();
	}
}
