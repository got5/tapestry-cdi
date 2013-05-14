package org.got5.tapestry5.cdi.beans;

import org.got5.tapestry5.cdi.annotation.CustomDessert;
import org.got5.tapestry5.cdi.annotation.DessertTime;

import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * User: pierremarot
 * Date: 30/04/13
 * Time: 19:06
 */
public class DessertFactory {

    @Produces
    @CustomDessert
    public Dessert getCustomDessert(){
        Dessert d = new IceCreamImpl();
        d.changeName();
        return d;
    }

    @Produces
    @DessertTime
    public Dessert getGoodDessert(@New DessertImpl dImpl,@New BrownieImpl brownie,@New IceCreamImpl iceCream){
        Calendar today = new GregorianCalendar();
        int hourOfDay = today.get(Calendar.HOUR_OF_DAY);
        if(hourOfDay < 12){
            return dImpl;
        }if(hourOfDay == 12){
            return iceCream;
        }else{
            return brownie;
        }

    }
}
