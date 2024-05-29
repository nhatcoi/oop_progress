package com.mycompany.app.hotel_management.entities;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Comment {
    int id;
    String name;
    String comment;
    Date date;
    int guest_id;
    int room_id;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                ", guest_id=" + guest_id +
                ", room_id=" + room_id +
                '}';
    }
}
