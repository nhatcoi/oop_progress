package com.mycompany.app.hotel_management.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    public static boolean validateEmail(String emailStr) {
        Matcher matcher = Validate.VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return !matcher.matches();
    }
}
