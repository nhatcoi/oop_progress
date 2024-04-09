package com.mycompany.app.hotel_management.enums;

public enum RoomStatus {
    AVAILABLE("Phòng đang trống"),
    OCCUPIED("Phòng đã được thuê"),
    MAINTENANCE("Phòng đang bảo trì");
    private final String text;

    private RoomStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
