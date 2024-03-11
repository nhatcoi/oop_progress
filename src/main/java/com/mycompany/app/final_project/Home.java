package com.mycompany.app.final_project;

import com.mycompany.app.final_project.interfaces.IHome;
import com.mycompany.app.final_project.models.Fileinfo;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Objects;

import static javafx.embed.swing.SwingFXUtils.toFXImage;

public abstract class Home implements IHome {
    public static File currDirFile;
    public static String currDirStr;
    public static Label lbl;
    public static String currDirName;
    static TilePane tilePane;
    SimpleDateFormat sdf;

    TableView<Fileinfo> tableView;
    TableColumn<Fileinfo, String> nameColumn;
    TableColumn<Fileinfo, String> sizeColumn;
    TableColumn<Fileinfo, String> dateColumn;
    TableColumn<Fileinfo, ImageView> imageColumn;

    public Home() {
    }

    @Override
    public Image getIconImage(File f) {
        ImageIcon icon = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(f);
        java.awt.Image image = icon.getImage();
        BufferedImage bImg = (BufferedImage) image;
        Image imgfx = toFXImage(bImg, null);
        return imgfx;
    }

    @Override
    public String calculateSize(File f) {
        String s;
        long sizeInByte = 0;
        Path path;
        if (isDrive(f)) {
            return Long.toString(f.getTotalSpace() / (1024 * 1024 * 1024)) + "GB";
        }

        path = Paths.get(f.toURI());
        //sizeInByte = f.getTotalSpace(); // terrible idea cz sob subfolder e 199GB, 99GB esob dekhay >_<
        try {
            sizeInByte = Files.size(path);//at least works ^_^
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (sizeInByte < (1024)) {
            s = Long.toString(sizeInByte) + "B";
            return s;
        } else if (sizeInByte < 1024 * 1024) {
            long sizeInKb = sizeInByte / 1024;
            s = Long.toString(sizeInKb) + "KB";
            return s;
        } else if (sizeInByte < 1024 * 1024 * 1024) {
            long sizeInMb = sizeInByte / (1024 * 1024);
            s = Long.toString(sizeInMb) + "MB";
            return s;
        } else {
            long sizeInGb = sizeInByte / (1024 * 1024 * 1024);
            s = Long.toString(sizeInGb) + "GB";
            return s;
        }
    }

    @Override
    public boolean isDrive(File f) {
        File[] sysroots = File.listRoots();
        for (File sysroot : sysroots) {
            if (f.equals(sysroot)) return true;
        }
        return false;
    }

    @Override
    public int FilesHiddenCount(File dir) {
        int count = 0;
        try {
            File[] fl = dir.listFiles();
            if (fl != null) {
                for (File file : fl) {
                    if (file.isHidden() || file.isFile()) {
                        count++;
                    }
                }
            } else {
                System.out.println("Directory is empty or cannot be accessed: " + dir.getAbsolutePath());
            }
        } catch (SecurityException e) {
            System.out.println("SecurityException while accessing directory: " + e.getMessage());
        } catch (Exception ex) {
            System.out.println("Exception while counting hidden files: " + ex.getMessage());
        }
        return count;
    }


    public int NumOfDirectChilds(File f) {
        return Objects.requireNonNull(f.listFiles()).length;
    }

    @Override
    public void setLabelTxt() {
        lbl.setText(currDirStr);
    }
}
