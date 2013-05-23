package org.got5.tapestry5.cdi.beans.ws;

import javax.ejb.Local;
import javax.jws.WebService;

/**
 * A simple example of how to setup a JAX-WS Web Service. It can say hello to someone in particular.
 * 
 */

@WebService(targetNamespace = "https://github.com/got5/tapestry-cdi/beans/ws/HelloWorld")
@Local
public interface HelloWorldService {

    /**
     * Say hello as a response
     * 
     * @return A simple hello world message
     */
    public String sayHello();

    /**
     * Say hello to someone 
     * 
     * @param name The name of the person to say hello to
     */
    public String sayHelloToName(String name);
}

