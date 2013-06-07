Tapestry-cdi
============
As a reminder, CDI is the Java EE standard for Dependency Injection and Aspect Oriented Programming.
Tapestry-CDI module allows injecting all kind of JSR 299 managed beans (POJO, EJB, web service, ...) as they are managed by the CDI-container. 

Features
--------

* @Inject CDI beans

  Contributes to [InjectionProvider](http://tapestry.apache.org/current/apidocs/org/apache/tapestry5/services/InjectionProvider.html) so that we can @Inject CDI beans into pages, components and tapestry services.

* Injection by constructor

  Contributes to [ObjectProvider](http://tapestry.apache.org/current/apidocs/org/apache/tapestry5/ioc/ObjectProvider.html) so that we can @Inject CDI beans into tapestry services contructors.
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
You just have to annotate your beans coming from the core project with CDI annotations and @Inject them into pages, components or services, and that’s pretty much it.

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
    

 
Using tapestry services inside CDI beans and vice versa
-------------------------------------------------------

Suppose you have implemented a bean in your tapestry webapp. It will be, by default, managed by CDI. 
Now if you want to use some tapestry services into it, you will have to let tapestry-ioc manage it. See snippet below. 

Let tapestry-ioc manage your bean. It will become a tapestry service.

    // A tapestry service declared in AppModule
    public static void bind(ServiceBinder binder) {
          binder.bind(TestManager.class);
    }

Then, your are allowed to add any tapestry services in your implementation in addition of the use of existing CDI beans (coming from the core project for instance) 

      // a tapestry service that uses CDI beans and other tapestry service
      public class TestManagerImpl implements TestManager {
           
           //a CDI bean coming from the core project
           private UserManager userManager;
           
           //a tapestry service injected by constructor (do not use @Inject here)
           private Request request;
           
           public TestManagerImpl(@MyQualifier UserManager userManager, Request request) {
                  this.userManager = userManager;
                  this.request = request;
           }
           
           @Override
           public void doSomething() {
              // Do something		
           }
      }

Note that injection are done by constructor and only CDI bean can be @injected by field. 


Using CDI beans as tapestry services
----------------------------------------

If your CDI bean is coming from the core project and you want tapestry to manage it (for instance to benefit of advanced tapestry-ioc features like decorators or advisors), do it like that :

In your AppModule
    
       // declare a CDI bean as a singleton scope tapestry service
       public static void bind(ServiceBinder binder) {
           binder.bind(ChoiceManager.class, new ServiceBuilder<ChoiceManager>() {
              public ChoiceManager buildService(ServiceResources serviceResources) {
                  return BeanHelper.get(ChoiceManager.class);
              }
           });
       }

For perthread scope, pay attention to handle Dependent scope. Indeed, as you instanciate the bean yourself, it's your responsability to release the bean : 

     // Declare a CDI bean as a per thread scope tapestry service
     public static void bind(ServiceBinder binder) {
         binder.bind(ChoiceManager.class, new ServiceBuilder<ChoiceManager>() {
                public ChoiceManager buildService(ServiceResources serviceResources) {
                        final BeanInstance beanInstance = BeanHelper.getInstance(ChoiceManager.class, new Annotation[]{});       
                        ThreadCleanupListener cleanupListener = new ThreadCleanupListener() {
                               public void threadDidCleanup() {
                                       beanInstance.release();
                               }
                        };
                        serviceResources.getService(PerthreadManager.class).addThreadCleanupListener(cleanupListener);
                        return (ChoiceManager)beanInstance.getBean();
                }
         }).scope(ScopeConstants.PERTHREAD);
    }


For more use cases, you can already take a look at the unit tests.

A demo project is coming soon ...

What’s next ?
-------------
The project is fully functional but it is far from complete. 

Indeed, CDI brings with it a lot of powerful functionality we would like to see in Tapestry. 

For example, CDI Event API greatly simplifies the use of events and could be well used in the framework. They are part of the goals for the next release. 

