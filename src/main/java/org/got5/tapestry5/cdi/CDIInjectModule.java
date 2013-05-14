package org.got5.tapestry5.cdi;

import org.apache.tapestry5.ioc.ObjectProvider;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.services.MasterObjectProvider;
import org.apache.tapestry5.services.transform.InjectionProvider2;

public final class CDIInjectModule {
	
	public static void bind(ServiceBinder binder) {
		binder.bind(ObjectProvider.class, CDIObjectProvider.class);
	} 
	
	@Contribute(value=MasterObjectProvider.class)
	public static void provideMasterObjectProvider(
			@Local ObjectProvider cdiProvider,
			OrderedConfiguration<ObjectProvider> configuration) {
		configuration.add("cdiProvider", cdiProvider, "after:*");
	}
	
	@Contribute(InjectionProvider2.class)
	public static void provideStandardInjectionProviders(final OrderedConfiguration<InjectionProvider2> configuration) {
		configuration.addInstance("CDI", CDIInjectionProvider.class, "before:*");
	}
}
