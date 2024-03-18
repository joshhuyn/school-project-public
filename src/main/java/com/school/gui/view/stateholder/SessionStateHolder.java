package com.school.gui.view.stateholder;

import java.sql.Date;

import com.school.data.model.UserModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionStateHolder
{
    private static SessionStateHolder stateHolder;

    private SessionStateHolder() { }

    public static SessionStateHolder get()
    {
        if (stateHolder == null)
        {
            stateHolder = new SessionStateHolder();
            return get();
        }

        return stateHolder;
    }

    public static SessionStateHolder getNew()
    {
        stateHolder = new SessionStateHolder();
        return get();
    }

    private UserModel user;   
    private Date loginTime;
}
