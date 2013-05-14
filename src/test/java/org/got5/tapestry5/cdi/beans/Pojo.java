package org.got5.tapestry5.cdi.beans;

public class Pojo {
    public String name() {
        return "injected pojo";
    }
    
    public String getNameForComponent(){
    	return "I pojo into component";
    }
}
