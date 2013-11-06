package org.apache.tapestry5.cdi.test.beans;

import java.io.Serializable;

import org.apache.tapestry5.cdi.test.annotation.MyStereotype;

@SuppressWarnings("serial")
@MyStereotype
public class Stereotyped implements Serializable {

	private String name = "Stereotyped";

	private String secondName = "Stereotyped name changed";

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
