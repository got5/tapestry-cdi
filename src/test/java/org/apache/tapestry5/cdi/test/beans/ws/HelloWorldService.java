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
package org.apache.tapestry5.cdi.test.beans.ws;

import javax.ejb.Local;
import javax.jws.WebService;

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
