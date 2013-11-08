Tapestry-cdi
============
As a reminder, CDI is the Java EE standard for Dependency Injection and Aspect Oriented Programming.  

Tapestry-CDI module allows injecting all kind of JSR 299 managed beans (POJO, EJB, web service, ...) as they are managed by the CDI-container.  

The implementation is standard and has been tested against [Apache TomEE](http://tomee.apache.org/apache-tomee.html), [JBoss Application Server 7](http://www.jboss.org/jbossas) and [Glassfish v3](https://wikis.oracle.com/display/glassfish/PlanForGlassFishV3) !

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

* Stereotypes

  [Stereotypes](http://docs.jboss.org/weld/reference/latest/en-US/html/stereotypes.html) are handled such as you can define and use your own stereotypes.  
  A [stereotype](http://docs.jboss.org/weld/reference/latest/en-US/html/stereotypes.html) is an annotation, annotated @Stereotype, that packages several other annotations.
  
* Standard
  
  The implementation is standard, not container specific!   
  Actually, the module has been tested against [Apache TomEE](http://tomee.apache.org/apache-tomee.html), [JBoss Application Server 7](http://www.jboss.org/jbossas) and [Glassfish v3](https://wikis.oracle.com/display/glassfish/PlanForGlassFishV3) !  
  See [build.gradle](https://github.com/got5/tapestry-cdi/blob/master/build.gradle) for more details on test environments 

Installation 
------------
By adding the module to your project’s dependencies, you are good to go.   
No more configurations are needed. 

Add the tapestry-cdi maven dependency for tapestry 5.4

    <dependency>
      <groupId>org.got5</groupId>
	    <artifactId>tapestry-cdi</artifactId>
	    <version>1.0.0-SNAPSHOT</version>
    </dependency>
    
for tapestry 5.3, use the 0.0.1 release
    
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

Note : To install the module locally , just do "__mvn install__"

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
    

For more use cases, you can already take a look at the unit tests.

### Notes ###

* CDI beans can come from a business layer and/or declared inside your tapestry front project if you want. 

* __Every bean present in a tapestry instrumented package will not be loaded by CDI__. Actually, a [veto](http://docs.jboss.org/cdi/api/1.0/javax/enterprise/inject/spi/ProcessAnnotatedType.html#veto%28%29) is set on beans under _yourRootPackage_.pages/components/mixins/base.
This is to prevent loading conflicts between CDI and tapestry-ioc. So, make sure that your beans are well packaged.

* If you bind a bean as a tapestry service in your appModule, tapestry-ioc will take precedence over CDI
 
Using tapestry services with CDI beans
--------------------------------------

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
------------------------------------

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

Using CDI beans in AppModule
---------

If you have to inject a CDI bean in your appModule (for service contribution, ValueEncoder creation, ...), you will have to use the @org.apache.tapestry5.ioc.annotations.Inject annotation, as the @javax.inject.Inject annotation can not be applied on method parameters
  
As an illustration, we show a ValueEncoderSource contribution declared in the appModule
 
        @SuppressWarnings("rawtypes")
        @Contribute(ValueEncoderSource.class)
		public static void provideEncoders(
   					MappedConfiguration<Class, ValueEncoderFactory> configuration,
  					final @org.apache.tapestry5.ioc.annotations.Inject ChoiceManager choiceManager) {

  			ValueEncoderFactory<Choice> factory = new ValueEncoderFactory<Choice>() {
  				public ValueEncoder<Choice> create(Class<Choice> clazz) {
   					return new ChoiceEncoder(choiceManager);
  				}
 			};
  			configuration.add(Choice.class, factory);
		}

Where ChoiceManager is an CDI bean injected thanks to the @org.apache.tapestry5.ioc.annotations.Inject annotation.  
The ChoiceEncoder implementation looks like :

		package mypackage.gui.services;

		import mypackage.core.entity.Choice;
		import mypackage.core.usecase.ChoiceManager;
		import org.apache.tapestry5.ValueEncoder;
		
		public class ChoiceEncoder implements ValueEncoder<Choice> {
			
			private ChoiceManager choiceManager;
			
			public ChoiceEncoder(ChoiceManager choiceManager) {
 				super();
 				this.choiceManager = choiceManager;
			}
			public String toClient(Choice choice) {//...}
			public Choice toValue(String clientValue) {//...}
		}

Note that we do not use any @Inject here

### Notes ###

* For pure tapestry services, you can let them as is, to benefit of tapestry-ioc features (decorator, advisor, ...) and keep backward compatibility.

* For beans coming from core project and already managed by CDI (existing META-INF/beans.xml) , there is no need for any declaration in the appModule.

Tests
-----

To run the unit tests against one of the following application servers : 

* For Apache TomEE : "__gradle tomeeEmbeddedTest__"
* For Glassfish v3 : "__gradle glassfishManagedTest__"
* For JBoss Application Server 7 : "__gradle jbossAS7ManagedTest__" 

What’s next ?
-------------
The project is fully functional but it is far from complete. 

Indeed, CDI brings with it a lot of powerful functionalities we would like to see in Tapestry.  
Specially, the @ConversationScope, @Tansactional and @Secure annotations provided by CDI 1.1 api (not yet implemented by application servers except Glassfish 4)   

A demo project is coming soon ...
