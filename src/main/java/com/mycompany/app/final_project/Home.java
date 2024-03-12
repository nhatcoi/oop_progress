package com.mycompany.app.final_project;

import com.mycompany.app.final_project.interfaces.IHome;
import com.mycompany.app.final_project.models.Fileinfo;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

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
import java.util.Optional;

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

    public static void createNewFile(TreeItem<String> selectedItem) {
        if (selectedItem != null) {
            File parentFolder = new File(Ultis.getFullPath(selectedItem));
            if (parentFolder.isDirectory()) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Tạo file mới");
                dialog.setHeaderText("Nhập tên file mới:");
                dialog.setContentText("Tên file:");

                Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                stage.setAlwaysOnTop(true);

                Optional<String> result = dialog.showAndWait();
                result.ifPresent(name -> {
                    File newFile = new File(parentFolder, name);
                    int count = 1;
                    while (newFile.exists()) {
                        newFile = new File(parentFolder, name + "_" + count);
                        count++;
                    }
                    try {
                        if (newFile.createNewFile()) {
                            System.out.println("Đã tạo file mới: " + newFile.getAbsolutePath());
                            TreeItem<String> newFileItem = new TreeItem<>(newFile.getName());
                            selectedItem.getChildren().add(newFileItem);
                        } else {
                            System.out.println("Không thể tạo file mới");
                        }
                    } catch (IOException e) {
                        System.out.println("Lỗi khi tạo file mới: " + e.getMessage());
                    }
                });
            }
        }
    }

    public static void renameFileOrFolder(TreeItem<String> selectedItem) {
        if (selectedItem != null) {
            String oldName = selectedItem.getValue();
            TextInputDialog dialog = new TextInputDialog(oldName);
            dialog.setTitle("Sửa tên");
            dialog.setHeaderText("Nhập tên mới:");
            dialog.setContentText("Tên mới:");

            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.setAlwaysOnTop(true);

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(newName -> {
                if (!newName.isEmpty() && !newName.equals(oldName)) {
                    File parentDir = new File(Ultis.getFullPath(selectedItem.getParent()));
                    File oldFile = new File(parentDir, oldName);

                    if (oldFile.exists()) {
                        if (selectedItem.isLeaf()) { // Là tệp tin
                            File newFile = new File(parentDir, newName);
                            if (oldFile.renameTo(newFile)) {
                                selectedItem.setValue(newName);
                                System.out.println("Đã đổi tên tệp tin thành công từ " + oldName + " thành " + newName);
                            } else {
                                System.out.println("Không thể đổi tên tệp tin");
                            }
                        } else {
                            File newDir = new File(parentDir, newName);
                            if (oldFile.renameTo(newDir)) {
                                selectedItem.setValue(newName);
                                System.out.println("Đã đổi tên thư mục thành công từ " + oldName + " thành " + newName);
                            } else {
                                System.out.println("Không thể đổi tên thư mục");
                            }
                        }
                    } else {
                        System.out.println("Không tìm thấy tệp tin hoặc thư mục");
                    }
                }
            });
        }
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

    public static void createNewFolder(TreeItem<String> selectedItem) {
        if (selectedItem != null) {
            File parentFolder = new File(Ultis.getFullPath(selectedItem));
            if (parentFolder.isDirectory()) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Tạo thư mục mới");
                dialog.setHeaderText("Nhập tên thư mục mới:");
                dialog.setContentText("Tên thư mục:");

                Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                stage.setAlwaysOnTop(true);

                Optional<String> result = dialog.showAndWait();

                result.ifPresent(name -> {
                    // Tạo thư mục mới với tên được nhập
                    File newFolder = new File(parentFolder, name);
                    int count = 1;
                    while (newFolder.exists()) {
                        newFolder = new File(parentFolder, name + "_" + count);
                        count++;
                    }
                    if (newFolder.mkdir()) {
                        System.out.println("Đã tạo thư mục mới: " + newFolder.getAbsolutePath());
                        // Cập nhật TreeView
                        TreeItem<String> newFolderItem = new TreeItem<>(newFolder.getName(), new ImageView(new Image(ClassLoader.getSystemResourceAsStream("img/folder.png"))));
                        selectedItem.getChildren().add(newFolderItem);
                    } else {
                        System.out.println("Không thể tạo thư mục mới");
                    }
                });
            }
        }
    }
}
