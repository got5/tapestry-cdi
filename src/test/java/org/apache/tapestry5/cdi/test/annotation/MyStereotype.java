package org.apache.tapestry5.cdi.test.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Stereotype;
import javax.inject.Named;

@Stereotype
@SessionScoped
@Named
@Retention(RUNTIME)
@Target(TYPE)
public @interface MyStereotype{

}
