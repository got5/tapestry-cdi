package org.got5.tapestry5.cdi;

import javax.enterprise.context.spi.CreationalContext;

public class BeanInstance {
    private final Object bean;
    private boolean releasable;
    private final CreationalContext<?> context;

    public BeanInstance(final Object bean, final CreationalContext<?> context, boolean releasable) {
        this.bean = bean;
        this.context = context;
        this.releasable = releasable;
    }

    public boolean isResolved() {
        return bean != null;
    }

    public Object getBean() {
        return bean;
    }

    public void release() {
        if (isReleasable()) {
            context.release();
        }
    }

    public boolean isReleasable() {
        return releasable;
    }
}
