package com.school.data.enums;

import lombok.Getter;

@Getter
public enum UserRole
{
    Admin("ADMIN"),
    User("USER"),
    NotVerified("NOT_VERIFIED");

    private final String dbLabel;

    private UserRole(String str)
    {
        dbLabel = str;
    }

    public static UserRole getFromString(String str)
    {
        return switch(str) {
            case "ADMIN" -> Admin;
            case "USER" -> User;
            case "NOT_VERIFIED" -> NotVerified;
            default -> null;
        };
    }
}
