package com.school.data.model;

import com.school.data.enums.UserRole;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModel extends AbstractModel
{
    private UserRole role;
    private String username;
    private String password;
    private String salt;
}
