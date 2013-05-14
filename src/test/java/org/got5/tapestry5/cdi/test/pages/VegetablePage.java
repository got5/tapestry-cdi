package org.got5.tapestry5.cdi.test.pages;

import org.got5.tapestry5.cdi.annotation.Choco;
import org.got5.tapestry5.cdi.annotation.Iced;
import org.got5.tapestry5.cdi.beans.BrownieImpl;
import org.got5.tapestry5.cdi.beans.Dessert;
import org.got5.tapestry5.cdi.beans.IceCreamImpl;
import org.got5.tapestry5.cdi.beans.Vegetable;

import javax.enterprise.inject.Any;
import javax.inject.Inject;

/**
 * User: pierremarot
 * Date: 30/04/13
 * Time: 18:20
 */
public class VegetablePage {

    @Inject
    private Vegetable vegetable;



    public String getVegetable(){
        if(vegetable != null){
            return "vegetable:" + vegetable.getName().equals(vegetable.getSecondName());
        }
        else
            return "";
    }



}
