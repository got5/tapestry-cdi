package org.got5.tapestry5.cdi.beans;

	import javax.annotation.PostConstruct;
	import javax.enterprise.context.RequestScoped;

	@RequestScoped
	public class Soup {

	    private String name = "Soup of the day";
	    private String secondName = "Soup of Tomorrow";

	    public String getName() {
	        return name;
	       
	    }
	    
	    public void changeName(){
	    	name = secondName;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }
	    
	    public boolean getCheckNames(){
	    	return name.equals(secondName);
	    }
	}