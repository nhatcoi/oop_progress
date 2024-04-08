package com.mycompany.app.hotel_management.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Guest {
    private int Id;
    private String username;
    private String name;
    private String phone;
    private String address;
    private int user_id;
}
