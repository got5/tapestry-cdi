package org.got5.tapestry5.cdi.test.pages;

import org.got5.tapestry5.cdi.beans.Dessert;
import org.got5.tapestry5.cdi.beans.DessertImpl;

import javax.inject.Inject;

/**
 * User: pierremarot
 * Date: 29/04/13
 * Time: 15:29
 */
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

