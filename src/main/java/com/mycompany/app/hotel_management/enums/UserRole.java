package com.mycompany.app.hotel_management.enums;

public enum UserRole {
    GUEST(0),
    STAFF(1),

    ADMIN(2);

    private final int value;

    UserRole(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static UserRole fromInt(int value) {
        for (UserRole role : UserRole.values()) {
            if (role.getValue() == value) {
                return role;
            }
        }
        return null;
    }
}
