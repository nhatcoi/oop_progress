package com.mycompany.app.week2;

public class Time {
    int hour;
    int minute;
    int second;

    // set hour method
    public void setHour(int h) {
        if (h >= 0 && h < 24) {
            hour = h;
        } else {
            System.out.println("Invalid hour");
        }
    }

    // set minute method
    public void setMinute(int m) {
        if (m >= 0 && m < 60) {
            minute = m;
        } else {
            System.out.println("Invalid minute");
        }
    }

    // set second method
    public void setSecond(int s) {
        if (s >= 0 && s < 60) {
            second = s;
        } else {
            System.out.println("Invalid second");
        }
    }

    // string to string method
    public String toString() {
        return hour + ":" + minute + ":" + second;
    }
}