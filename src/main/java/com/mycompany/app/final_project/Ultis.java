package com.mycompany.app.final_project;

import com.mycompany.app.final_project.view.TreeView;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.io.IOException;

public class Ultis {
    public static String getFullPath(TreeItem<String> item) {
        return getString(item);
    }

    public static String getString(TreeItem<String> item) {
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
        TreeView.listFileDict(parentItem, directory);
    }


}
