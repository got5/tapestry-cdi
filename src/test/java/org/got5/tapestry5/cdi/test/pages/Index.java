package org.got5.tapestry5.cdi.test.pages;



import javax.inject.Named;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.got5.tapestry5.cdi.beans.*;


public class Index {
    @javax.inject.Inject
    private Pojo pojo;

    @javax.inject.Inject
    @Named("named")
    private NamedPojo namedPojo;
    

    @javax.inject.Inject
    @Property
    private CounterService counterService;
    

    @javax.inject.Inject
    private Messages messageCDI;
    
    @org.apache.tapestry5.ioc.annotations.Inject
    private Messages messageTapestry;
    
    @javax.inject.Inject
    private StatelessEJBBean statelessBean;

    
    @javax.inject.Inject
    private Soup soup1;
    
    @javax.inject.Inject
    private Soup soup2;

    @javax.inject.Inject
    private Dessert dessert;


    
   
    public String getPojo() {
        return pojo.name();
    }
    public String getNamedPojo() {
        return namedPojo.name();
    }
    
    public String getMessageCDI(){
    	return messageCDI.get("messagecdi");
    }
    
    public String getMessageTapestry(){
    	return messageTapestry.get("messagetapestry");
    }
    public String getStatelessEJB(){
    	return statelessBean.helloStatelessEJB();
    }

    public String getRequestScopePojo(){
    	if(soup1 !=null){
    		soup1.changeName();
    		return "request:"+soup1.getName().equals(soup2.getName());
    	}
    	return "";
    }

    public String getSessionScopePojo(){
       if(dessert != null){
           return "session:"+dessert.getName().equals(dessert.getOtherName());
       }
        return "";
    }


    public void onActivate(){
    	counterService.increment();
    }
}
