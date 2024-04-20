package com.mycompany.app.hotel_management.enums;

public enum RoomStatus {
    AVAILABLE("Available Room"),
    OCCUPIED("Occupied Room"),
    MAINTENANCE("Maintenance Room"),;
    private final String text;

    private RoomStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
