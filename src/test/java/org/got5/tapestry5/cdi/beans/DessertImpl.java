package org.got5.tapestry5.cdi.beans;

import org.got5.tapestry5.cdi.annotation.Iced;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Default;

/**
 * User: pierremarot
 * Date: 29/04/13
 * Time: 18:18
 */


@SessionScoped
@Default
public class DessertImpl implements Dessert{
    private String name = "Ice Cream Sandwich";

    private String secondName = "Jelly Bean";

    public String getName(){
        return name;
    }

    public String getOtherName(){
        return secondName;
    }

    public void setName(String name){
        this.name = name;
    }

    public void changeName(){
        name = secondName;
    }

    public boolean getCheckName(){
        return name.equals(secondName);
    }
}
