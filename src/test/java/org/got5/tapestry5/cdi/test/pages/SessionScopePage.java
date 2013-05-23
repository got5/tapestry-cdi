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
package org.got5.tapestry5.cdi.test.pages;

import javax.inject.Inject;

import org.got5.tapestry5.cdi.beans.DessertImpl;

public class SessionScopePage {

   @Inject
   private DessertImpl dessert1;

   @Inject
   private DessertImpl dessert2;


    public String getSessionScopePojo(){
        if(dessert1!=null && dessert1.getName().equals(dessert2.getName())){
          dessert1.changeName();
          return "session:" + dessert1.getName().equals(dessert2.getName());
        }else{
            return "";
        }
    }
}
