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
    private String email;
    private int user_id;

    @Override
    public String toString() {
        return "Guest{" +
                "Id=" + Id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", user_id=" + user_id +
                '}';
    }
}
