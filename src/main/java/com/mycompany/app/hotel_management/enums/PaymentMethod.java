package com.mycompany.app.hotel_management.enums;

public enum PaymentMethod {
    CASH("Cash"),
    BANK_TRANSFER("Bank transfer"),
    PAYPAL("Paypal"),
    BITCOIN("Bitcoin");

    private final String text;

    private PaymentMethod(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
