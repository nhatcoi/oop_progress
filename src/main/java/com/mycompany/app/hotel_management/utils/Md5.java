package com.mycompany.app.hotel_management.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
    public static String hashString(String input) {
        try {
            // Tạo một instance của MessageDigest với thuật toán MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Cải thiện hiệu suất bằng cách sử dụng byte array để xử lý chuỗi
            byte[] bytes = md.digest(input.getBytes());

            // Chuyển đổi byte array thành chuỗi hex
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
