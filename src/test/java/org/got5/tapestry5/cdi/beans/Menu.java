package org.got5.tapestry5.cdi.beans;

import com.google.inject.Inject;
import org.got5.tapestry5.cdi.annotation.Iced;

/**
 * User: pierremarot
 * Date: 06/05/13
 * Time: 14:10
 */
public class Menu {

    private Dessert dessert;

    @Inject
    void initQuery(@Iced Dessert dessert){

            this.dessert = dessert;

    }

    public String getDessert(){
        if(dessert !=null){
            return dessert.getName();
        }else{
            return "no dessert";
        }

    }
}
