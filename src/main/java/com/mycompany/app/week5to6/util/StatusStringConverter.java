package com.mycompany.app.week5to6.util;

import com.mycompany.app.week5to6.enums.Status;
import javafx.util.StringConverter;

public class StatusStringConverter extends StringConverter<Status> {

    @Override
    public String toString(Status status) {
        return status.getText();
    }

    @Override
    public Status fromString(String string) {
        return null; // Chúng ta không cần implement method này trong trường hợp này
    }
}