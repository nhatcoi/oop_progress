package com.mycompany.app.hotel_management.utils;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PDFScanner {

    public static void scanToPDF(Stage stage) {
        WritableImage snapshot = stage.getScene().snapshot(null);
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, Files.newOutputStream(Paths.get("scan_bill_vn_hotel.pdf")));
            document.open();
            Image image = Image.getInstance(SwingFXUtils.fromFXImage(snapshot, null), null);
            document.add(image);
            document.close();
            System.out.println("PDF saved successfully.");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void openPDF(String path) {
        try {
            Desktop.getDesktop().open(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}