package com.mycompany.app.final_project.enums;

public enum Status {
    AVAILABLE("Trống"),
    BOOKED("Đã đặt"),
    OCCUPIED("Đã đặt cọc");

    private final String text;

    Status(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
