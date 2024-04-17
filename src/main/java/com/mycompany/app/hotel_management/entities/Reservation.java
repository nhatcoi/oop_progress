package com.mycompany.app.hotel_management.entities;

import java.util.Date;
import java.util.Objects;

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
    int Id;
    int user_id;
    int room_id;
    Date checkInDate;
    Date checkoutDate;

    @Override
    public String toString() {
        return "Reservation{" +
                "Id=" + Id +
                ", user_id=" + user_id +
                ", room_id=" + room_id +
                ", checkInDate=" + checkInDate +
                ", checkoutDate=" + checkoutDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return user_id == that.user_id && room_id == that.room_id && Objects.equals(checkInDate, that.checkInDate) && Objects.equals(checkoutDate, that.checkoutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, room_id, checkInDate, checkoutDate);
    }
}
