package org.got5.tapestry5.cdi.beans;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 * User: pierremarot
 * Date: 29/04/13
 * Time: 15:39
 */

public interface Dessert extends Serializable {

    public String getName();

    public String getOtherName();

    public void setName(String name);

    public void changeName();

    public boolean getCheckName();
}
