package org.got5.tapestry5.cdi.extension;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;

public class BeanManagerHolder implements Extension {
    private static BeanManagerHolder HOLDER = new BeanManagerHolder();
    private BeanManager beanManager;

    public static BeanManager get() {
        return HOLDER.beanManager;
    }

    protected void saveBeanManager(@Observes final AfterBeanDiscovery afterBeanDiscovery, final BeanManager bm) {
        HOLDER.beanManager = bm;
    }
}

