package com.mycompany.app.hotel_management.enity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class User {
    private int Id;
    private String username;
    private String email;
    private String password;
    private String type;
}

