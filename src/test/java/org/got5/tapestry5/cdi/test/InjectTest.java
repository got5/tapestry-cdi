/**
 * Copyright 2013 GOT5
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.got5.tapestry5.cdi.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;

import javax.enterprise.inject.spi.Extension;

import org.antlr.runtime.Lexer;
import org.antlr.stringtemplate.StringTemplate;
import org.apache.commons.codec.StringEncoder;
import org.apache.tapestry5.TapestryFilter;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.ioc.IOCConstants;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.services.TapestryModule;
import org.apache.ziplock.IO;
import org.apache.ziplock.JarLocation;
import org.got5.tapestry5.cdi.CDIInjectModule;
import org.got5.tapestry5.cdi.annotation.Iced;
import org.got5.tapestry5.cdi.beans.NamedPojo;
import org.got5.tapestry5.cdi.beans.ws.HelloWorldService;
import org.got5.tapestry5.cdi.extension.BeanManagerHolder;
import org.got5.tapestry5.cdi.extension.TapestryExtension;
import org.got5.tapestry5.cdi.test.pages.DessertPage;
import org.got5.tapestry5.cdi.test.pages.InvalidateSessionPage;
import org.got5.tapestry5.cdi.test.pages.RequestScopePage;
import org.got5.tapestry5.cdi.test.pages.SessionScopePage;
import org.got5.tapestry5.cdi.test.pages.SomePage;
import org.got5.tapestry5.cdi.test.pages.StatefulPage;
import org.got5.tapestry5.cdi.test.pages.VegetablePage;
import org.got5.tapestry5.cdi.test.pages.WSPage;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.webapp30.WebAppDescriptor;
import org.junit.Test;
import org.junit.runner.RunWith;

import antlr.Grammar;

@RunWith(Arquillian.class)
public class InjectTest {

    @ArquillianResource
    private URL indexUrl;


    @Deployment(testable = false)
    public static WebArchive war() {
        return ShrinkWrap
                .create(WebArchive.class, "inject.war")
                        // our module (src/main), as we are in the same project building
                        // the jar on the fly
                .addAsLibraries(
                        ShrinkWrap
                                .create(JavaArchive.class,
                                        "tapestry-cdi.jar")
                                .addPackage(
                                        CDIInjectModule.class.getPackage()
                                                .getName())
                                .addAsManifestResource(
                                        new StringAsset(BeanManagerHolder.class
                                                .getName()),
                                        "services/" + Extension.class.getName())
                                .addAsManifestResource(
                                        new StringAsset(TapestryExtension.class
                                                .getName()),
                                        "services/" + Extension.class.getName()))

                        // our test classes (src/test) = the webapp

                        // apparently not mandatory
                        //.addClasses(NamedPojo.class, Pojo.class, Index.class,DumbComponent.class, PojoModule.class)
                        //minimum
                .addPackage(NamedPojo.class.getPackage().getName())
                .addPackage(Iced.class.getPackage().getName())
                .addPackage(HelloWorldService.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(
                        new StringAsset(Descriptors
                                .create(WebAppDescriptor.class).version("3.0")
                                .createContextParam()
                                .paramName("tapestry.app-package")
                                .paramValue(InjectTest.class.getPackage().getName())
                                .up().createContextParam()
                                .paramName("tapestry.production-mode")
                                .paramValue("false").up().createFilter()
                                .filterName("pojo")
                                .filterClass(TapestryFilter.class.getName())
                                .up().createFilterMapping().filterName("pojo")
                                .urlPattern("/*").up()
                                .exportAsString()),
                        "web.xml")

                        // tapestry dependencies, for real project put it in a helper
                        // class: new TapestryArchive(name)...
                .addAsLibraries(JarLocation.jarLocation(Lexer.class))
                .addAsLibraries(JarLocation.jarLocation(Grammar.class))
                .addAsLibraries(JarLocation.jarLocation(StringTemplate.class))
                .addAsLibraries(JarLocation.jarLocation(StringEncoder.class))
                .addAsLibraries(JarLocation.jarLocation(IOCConstants.class))
                .addAsLibraries(JarLocation.jarLocation(PlasticClass.class))
                .addAsLibraries(JarLocation.jarLocation(JSONArray.class))
                .addAsLibraries(JarLocation.jarLocation(InjectService.class))
                .addAsLibraries(JarLocation.jarLocation(Mapper.class))
                .addAsLibraries(JarLocation.jarLocation(TapestryModule.class));
    }

   /**
     * Todo - Move all Stateful tests in seperate pages and files. Also make a separate shrink archive for each kind of Bean    
     */

    @Test
    @InSequence(0)
    public void checkApplicationScope() throws IOException {
        //get the index page (that increments an applicationScope counter)
        String output = IO.slurp(indexUrl);

        //check that the counter has been incremented
        assertTrue("Injection of Application Scope Bean failed in page Index", output.contains("Counter : 1"));

        //change the page
        output = IO.slurp(new URL(indexUrl.toString() + SomePage.class.getSimpleName()));
        assertNotNull(output);

        //get the index page (that increments an applicationScope counter)
        output = IO.slurp(indexUrl);

        //check that the counter has been incremented based on previous value (has not been re-initialized)
        assertTrue("Injection of Application Scope Bean failed in page Index", output.contains("Counter : 2"));

    }

    @Test
    @InSequence(1)
    public void checkInjectionsPojoFromOutput() throws IOException {

        final String output = IO.slurp(indexUrl);
        System.out.println(output);
        assertTrue("Injection of Pojo failed in page index",
                output.contains("injected pojo"));
        assertTrue("Injection of NamedPojo failed in page index",
                output.contains("injected named pojo"));
        assertTrue("Injection of Pojo failed in component DumbComponent",
                output.contains("I named pojo into component"));
        assertTrue("Injection of NamedPojo failed in component DumbComponent",
                output.contains("I pojo into component"));

    }

    @Test
    @InSequence(2)
    public void checkInjectionTapestryServices() throws IOException {
        final String output = IO.slurp(indexUrl);

        assertTrue(
                "Injection of Tapestry Service Messages by CDI annotation failed in page Index",
                output.contains("message_cdi"));
        assertTrue(
                "Injection of Tapestry Service Messages by Tapestry annotation failed in page Index",
                output.contains("message_tapestry"));

    }

    @Test
    @InSequence(3)
    public void checkInjectionSessionBeans() throws IOException {

        String output = IO.slurp(indexUrl);
        assertTrue("Injection of Stateless Session Bean failed in page Index", output.contains("Hello Stateless EJB"));

        output = IO.slurp(new URL(indexUrl.toString() + StatefulPage.class.getSimpleName()));
        assertTrue("Injection of Stateful Session Bean failed in page MyStateful\n" + output, output.contains("011stateful"));

        output = IO.slurp(new URL(indexUrl.toString() + StatefulPage.class.getSimpleName()));
        assertTrue("Injection of Stateful Session Bean failed in page MyStateful\n" + output, output.contains("122stateful"));

    }


    @Test
    @InSequence(4)
    public void checkInjectionRequestScope() throws IOException {

        String output = IO.slurp(indexUrl);
        assertTrue("Injection of RequestScope pojo failed in page Index", output.contains("request:true"));

        output = IO.slurp(new URL(indexUrl.toString() + RequestScopePage.class.getSimpleName()));
        assertTrue("Injection of RequestScope pojo failed in page Index", output.contains("request:false"));

    }


    @Test
    @InSequence(5)
    public void checkSessionScope() throws IOException {

        String output = IO.slurp(new URL(indexUrl.toString() + SessionScopePage.class.getSimpleName()));
        assertTrue("Injection of SessionScope pojo failed in page Index 1", output.contains("session:true"));

        output = IO.slurp(indexUrl);

        assertTrue("Injection of SessionScope pojo failed in page Index 2", output.contains("session:true"));

        output = IO.slurp(new URL(indexUrl.toString() + InvalidateSessionPage.class.getSimpleName()));

        assertNotNull(output);

        output = IO.slurp(indexUrl);

        assertTrue("Injection of SessionScope pojo failed in page Index 3", output.contains("session:false"));
    }

    /**
     * Todo - Add tests for session state. How  notify cdi about changes in session state objects ?
     *
     */

    @Test
    @InSequence(6)
    public void checkQualifierBasic() throws IOException {

        String output = IO.slurp(new URL(indexUrl.toString() + DessertPage.class.getSimpleName()));
        assertTrue("Injection of pojo with qualifier failed in page Dessert", output.contains("dessert1:true"));
        assertTrue("Injection of pojo with qualifier failed in page Dessert", output.contains("dessert2:true"));
        assertTrue("Injection of pojo with qualifier and produces method failed in page Dessert", output.contains("dessert3:true"));
        assertTrue("Injection of pojo with qualifier and produces method + @new failed in page Dessert", output.contains("dessert4:true"));

        /**
         Todo - Add support to @Inject method | uncomment the line below to test it
         */
        //assertTrue("Injection of pojo with qualifier and inject method in page Dessert",output.contains("dessert5:true"));


    }

    @Test
    @InSequence(7)
    public void checkConversationScope() throws IOException {

        String output = IO.slurp(new URL(indexUrl.toString() + VegetablePage.class.getSimpleName()));
        /**
         Todo - Create a test with drone to play with the conversation scope
         */

    }

    @Test
    @InSequence(8)
    public void checkEventBasic() throws IOException {
        /**
         Todo - find a usecase... issues while fire event in page/ cannot observes in page either
         */
        
    }

    @Test
    @InSequence(9)
    public void checkBindingType() throws IOException {
        /**
         Todo - Use Produces method with parameter to present a great use case
         */


    }

    @Test
    @InSequence(10)
    public void checkWebService() throws IOException {
    	 String output = IO.slurp(new URL(indexUrl.toString() + WSPage.class.getSimpleName()));
    	 assertNotNull(output);
    	 assertTrue("Injection of webservice failed in page WSPage", output.contains("Hello John"));
    }

}

