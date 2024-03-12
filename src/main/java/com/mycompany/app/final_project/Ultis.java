package com.mycompany.app.final_project;

import javafx.beans.property.ReadOnlyProperty;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;

public class Ultis {
    public static String getFullPath(TreeItem<String> item) {
        StringBuilder fullPath = new StringBuilder(item.getValue());
        TreeItem<String> parent = item.getParent();
        while (parent != null && parent.getParent() != null) {
            fullPath.insert(0, parent.getValue() + File.separator);
            parent = parent.getParent();
        }
        return fullPath.toString();
    }
    public static void deleteDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    if (!file.delete()) {
                        throw new IOException("Failed to delete file: " + file.getAbsolutePath());
                    }
                }
            }
        }

        if (!directory.delete()) {
            throw new IOException("Failed to delete directory: " + directory.getAbsolutePath());
        }
    }

    public static void loadDirectory(TreeItem<String> parentItem, File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName().toLowerCase();
                if (file.isDirectory()) {
                    TreeItem<String> directoryNode = new TreeItem<>(file.getName(), new ImageView(new Image(ClassLoader.getSystemResourceAsStream("img/folder.png"))));
                    File[] files2 = file.listFiles();
                    if (files2 != null && files2.length > 0) {
                        directoryNode.getChildren().add(new TreeItem<>());
                    }
                    parentItem.getChildren().add(directoryNode);
                } else if (fileName.endsWith(".txt")) {
                    TreeItem<String> fileNode = new TreeItem<>(file.getName(), new ImageView(new Image(ClassLoader.getSystemResourceAsStream("img/txt.png"))));
                    parentItem.getChildren().add(fileNode);
                } else if (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
                    TreeItem<String> fileNode = new TreeItem<>(file.getName(), new ImageView(new Image(ClassLoader.getSystemResourceAsStream("img/image.png"))));
                    parentItem.getChildren().add(fileNode);
                } else {
                    TreeItem<String> fileNode = new TreeItem<>(file.getName(), new ImageView(new Image(ClassLoader.getSystemResourceAsStream("img/empty.png"))));
                    parentItem.getChildren().add(fileNode);
                }
            }
        }
    }



    public static TreeItem<String> findTreeItem(TreeView<String> treeView, String path) {
        return findTreeItem(treeView.getRoot(), path);
    }

    private static TreeItem<String> findTreeItem(TreeItem<String> root, String path) {
        if (root == null || path == null || path.isEmpty()) {
            return null;
        }

        if (root.getValue().equals(path)) {
            return root;
        }

        for (TreeItem<String> child : root.getChildren()) {
            TreeItem<String> found = findTreeItem(child, path);
            if (found != null) {
                return found;
            }
        }
        return null;
    }
}
