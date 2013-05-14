package org.got5.tapestry5.cdi.test.services;


import org.apache.tapestry5.ioc.annotations.SubModule;
import org.got5.tapestry5.cdi.CDIInjectModule;

@SubModule({
    CDIInjectModule.class
})
public final class PojoModule {
}
