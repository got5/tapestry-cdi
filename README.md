Tapestry-cdi
============
As a reminder, CDI is the Java EE standard for Dependency Injection and Aspect Oriented Programming.
Tapestry-CDI module allows injecting all kind of JSR 299 managed beans (POJO, EJB, web service, ...) as they are managed by the CDI-container. 

Features
--------

* @Inject CDI beans

  Contributes to InjectionProvider so that we can @Inject CDI beans into pages, components and tapestry services.

* Injection by constructor

  Contributes to ObjectProvider so that we can @Inject CDI beans into tapestry services contructors.
  So beans can be injected by field or by constructor.

* Qualifiers

  To disambiguate when injecting same type but different implementations, the module provides support for qualifier which is part of CDI specification. 
  You can use qualifiers into pages, components and services.
  For the time being, this module is the only implementation that supports CDI Qualifiers in Tapestry.

* Helpers

  Add method helpers to ease the cdi bean instanciation 

* IOC isolation
 
  The module assures to have no conflict with the tapestry's IOC (Inversion of Control). 
  Indeed, as there are beans managed by the java EE container and others beans managed by the framework, work has been made to prevent one to take control over the beans it is not supposed to manage.


Installation 
------------
By adding the module to your project’s dependencies, you are good to go. 
No more configurations are needed. 

Add the tapestry-cdi maven dependency

    <dependency>
      <groupId>org.got5</groupId>
	    <artifactId>tapestry-cdi</artifactId>
	    <version>0.0.1-SNAPSHOT</version>
    </dependency>
    
and the following maven repositories

    <repository>
      <id>pullrequest-snapshot</id>
      <url>http://nexus.pullrequest.org/content/repositories/snapshots/</url>
      <releases>
        <enabled>false</enabled>
      </releases>
      <snapshots>
        <enabled>true</enabled>
      </snapshots>
    </repository>

    <repository>
      <id>pullrequest-release</id>
      <url>http://nexus.pullrequest.org/content/repositories/releases/</url>
      <releases>
        <enabled>true</enabled>
      </releases>
      <snapshots>
        <enabled>false</enabled>
      </snapshots>
    </repository>


Usage
-----
You just have to annotate your beans with CDI annotations and @Inject them into pages, components or services, and that’s pretty much it.

    import javax.inject.Inject;
    import javax.inject.Named;
    ...
    @Inject
    private MyCdiBean bean;
    
    @Inject
    @Named("named")
    private MyNamedCdiBean namedBean;
    
    @Inject
    @MyQualifier
    private MyQualifiedBean qualifiedBean;
    

For more use cases, you can already take a look at the unit tests.

A demo project is coming soon ...

What’s next ?
-------------
The project is fully functional but it is far from complete. 

Indeed, CDI brings with it a lot of powerful functionality we would like to see in Tapestry. 

For example, CDI scope management is essential when we speak about stateful bean. Then the CDI Event API greatly simplifies the use of events and could be well used in the framework. They are part of the goals for the next release. 

