package org.got5.tapestry5.cdi.beans.ws;

import javax.ejb.Stateless;
import javax.jws.WebService;

/**
 * The implementation of the HelloWorld JAX-WS Web Service.
 * 
 */
@Stateless
@WebService(
		serviceName = "HelloWorldService", 
		portName = "HelloWorldPort", 
		endpointInterface = "org.got5.tapestry5.cdi.beans.ws.HelloWorldService", 
		targetNamespace = "https://github.com/got5/tapestry-cdi/beans/ws/HelloWorld")
public class HelloWorldServiceImpl implements HelloWorldService {

    @Override
    public String sayHello() {
        return "Hello World!";
    }

    @Override
    public String sayHelloToName(final String name) {
          return "Hello "+name;
    }
}
