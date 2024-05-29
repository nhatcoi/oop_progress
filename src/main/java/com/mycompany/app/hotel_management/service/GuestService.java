package com.mycompany.app.hotel_management.service;

import com.mycompany.app.hotel_management.entities.Guest;
import javafx.collections.ObservableList;

public interface GuestService {
    void getData(ObservableList<Guest> guestsList);
    void deleteData(ObservableList<Guest> guestsList, Guest guest);
    void updateData(ObservableList<Guest> guestsList, Guest guest);
    void createDate(ObservableList<Guest> guestsList, Guest guest);
}
