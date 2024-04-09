package com.mycompany.app.hotel_management.utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class imgTool {

    public static String imageViewToBase64(ImageView imageView) {
        // Lấy hình ảnh từ ImageView
        javafx.scene.image.Image fxImage = imageView.getImage();

        // Chuyển đổi hình ảnh thành BufferedImage
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(fxImage, null);

        // Chuyển đổi BufferedImage thành mảng byte
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] imageBytes = outputStream.toByteArray();

        // Chuyển đổi mảng byte thành chuỗi base64
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    public static Image base64ToImage(String base64Image) {
        // Giải mã chuỗi base64 thành mảng byte
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        // Tạo một đối tượng ByteArrayInputStream từ mảng byte
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);

        // Đọc hình ảnh từ ByteArrayInputStream và trả về
        return new Image(bis);
    }
}
