package com.mycompany.app.hotel_management.enums;

public enum RoomType {
    SINGLE("Single Room"),
    DOUBLE("Double Room"),
    TRIPLE("Triple Room"),
    QUAD("Quad Room"),
    QUEEN("Queen Room"),
    KING("King Room"),
    STUDIO("Studio Room"),
    SUITE("Suite Room"),
    DUPLEX("Duplex Room"),
    PENTHOUSE("Penthouse Room"),;

    private final String text;

    private RoomType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
