package org.got5.tapestry5.cdi.test.pages;


import javax.inject.Inject;

/**
 * User: pierremarot
 * Date: 29/04/13
 * Time: 15:49
 */
public class InvalidateSessionPage {


    @Inject
    private org.apache.tapestry5.services.Request request;


    public void onActivate(){
        request.getSession(true).invalidate();
    }
}
