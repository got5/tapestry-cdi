package org.got5.tapestry5.cdi.beans;

import javax.inject.Named;

@Named("named")
public class NamedPojo {
    public String name() {
        return "injected named pojo";
    }
    
    public String getNameForComponent(){
    	return "I named pojo into component";
    }
}
