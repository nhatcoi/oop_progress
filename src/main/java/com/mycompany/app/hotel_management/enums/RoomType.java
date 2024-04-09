package com.mycompany.app.hotel_management.enums;

public enum RoomType {
    SINGLE("Phòng đơn"),
    DOUBLE("Phòng đôi"),
    TRIPLE("Phòng ba"),
    QUAD("Phòng bốn"),
    QUEEN("Phòng queen"),
    KING("Phòng king"),
    STUDIO("Phòng studio"),
    SUITE("Phòng suite"),
    DUPLEX("Phòng duplex"),
    PENTHOUSE("Phòng penthouse");

    private final String text;

    private RoomType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
