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
public class Reservation {
    // info booking
    int id;
    int user_id;
    int room_id;
    Date checkInDate;
    Date checkoutDate;
}
