package com.mycompany.app.final_project.view;

import com.mycompany.app.final_project.Home;
import javafx.beans.property.ReadOnlyProperty;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.Objects;

public class TreeView extends Home {
    public TreeView() {
    }



    @Override
    public String FindAbsolutePath(TreeItem<String> item, String s) {
        if ((item.getParent() == null) || (item.getParent().getValue().equals("This PC"))) {
            return s;
        } else {
            String dir = item.getParent().getValue();
            dir = dir + "\\" + s;
            return FindAbsolutePath(item.getParent(), dir);
        }
    }

    @Override
    public void CreateTreeView(javafx.scene.control.TreeView<String> treeView) {
        TreeItem<String> rootNode = new TreeItem<>("This PC", new ImageView(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("img/pc.png")))));
        rootNode.setExpanded(true);

        treeView.setRoot(rootNode);

        File[] sysroots = File.listRoots();
        for (File root : sysroots) {
            TreeItem<String> rootItem = new TreeItem<>(root.getAbsolutePath(), new ImageView(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("img/drive.png")))));
            treeView.getRoot().getChildren().add(rootItem);
            loadDirectory(rootItem, root);
            rootItem.expandedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) { // Nếu nút được mở rộng
                    TreeItem<String> treeItem = (TreeItem<String>) ((ReadOnlyProperty<?>) observable).getBean();
                    if (treeItem.getChildren().size() == 1 && treeItem.getChildren().get(0).getValue() == null) { // Nếu chỉ có một nút giả
                        treeItem.getChildren().clear(); // Xóa nút giả
                        String path = treeItem.getValue();
                        loadDirectory(treeItem, new File(path));
                    }
                }
            });
        }
    }

    private void loadDirectory(TreeItem<String> parentItem, File directory) {
        listFileDict(parentItem, directory);
    }

    public static void listFileDict(TreeItem<String> parentItem, File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName().toLowerCase();
                if (file.isDirectory()) {
                    TreeItem<String> directoryNode = new TreeItem<>(file.getName(), new ImageView(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("img/folder.png")))));
                    File[] files2 = file.listFiles();
                    if (files2 != null && files2.length > 0) {
                        directoryNode.getChildren().add(new TreeItem<>());
                    }
                    parentItem.getChildren().add(directoryNode);
                } else if (fileName.endsWith(".txt")) {
                    TreeItem<String> fileNode = new TreeItem<>(file.getName(), new ImageView(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("img/txt.png")))));
                    parentItem.getChildren().add(fileNode);
                } else if (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
                    TreeItem<String> fileNode = new TreeItem<>(file.getName(), new ImageView(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("img/image.png")))));
                    parentItem.getChildren().add(fileNode);
                } else {
                    TreeItem<String> fileNode = new TreeItem<>(file.getName(), new ImageView(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("img/empty.png")))));
                    parentItem.getChildren().add(fileNode);
                }
            }
        }
    }



    @Override
    public void Initialize() {

    }
}
