package org.got5.tapestry5.cdi.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import java.io.Serializable;

/**
 * User: pierremarot
 * Date: 29/04/13
 * Time: 18:18
 */


@ConversationScoped
public class Vegetable implements Serializable{
    private String name = "salad";
    private String secondName = "tomato";

    @Inject
    private
    javax.enterprise.context.Conversation conversation;

    @PostConstruct
    public void init(){
        if(conversation.isTransient()){
            conversation.begin();
        }
        throw new IllegalStateException();
    }


    public String getName(){
        return name;
    }


    public void changeName(){
        name = secondName;
    }

    public void getEndConversation(){
        if(!conversation.isTransient()){
            conversation.end();
        }
        throw new IllegalStateException();
    }

    public boolean getCheckName(){
        return name.equals(secondName);
    }
    public String getSecondName(){
        return secondName;
    }
}
