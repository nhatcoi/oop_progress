package com.mycompany.app.final_project;

import com.mycompany.app.final_project.interfaces.IHome;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public abstract class Home implements IHome {
    public static File currDirFile;
    public static String currDirStr;
    public static Label lbl;
    public static String currDirName;

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

    public static void removeFileOrFolder(TreeItem<String> selectedItem) {
        if (selectedItem != null) {
            String fullPath = Ultis.getFullPath(selectedItem);
            File fileToDelete = new File(fullPath);
            try {
                Ultis.deleteDirectory(fileToDelete);
                selectedItem.getParent().getChildren().remove(selectedItem);
            } catch (Exception e) {
                e.printStackTrace();
                // Xử lý lỗi xóa thư mục
            }
        }
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
                        TreeItem<String> newFolderItem = new TreeItem<>(newFolder.getName(), new ImageView(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("img/folder.png")))));
                        selectedItem.getChildren().add(newFolderItem);
                    } else {
                        System.out.println("Không thể tạo thư mục mới");
                    }
                });
            }
        }
    }
}
