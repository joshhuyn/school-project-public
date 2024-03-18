package com.school.gui.view.stateholder.admin;

import com.school.data.model.UserModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountStateHolder
{
    private static AccountStateHolder stateHolder;

    private AccountStateHolder() { }

    public static AccountStateHolder get()
    {
        if (stateHolder == null)
        {
            stateHolder = new AccountStateHolder();
            return get();
        }

        return stateHolder;
    }

    public static AccountStateHolder getNew()
    {
        stateHolder = new AccountStateHolder();
        return get();
    }

    private UserModel userModel;   
}
