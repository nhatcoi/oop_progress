package com.mycompany.app.final_project.view;

import com.mycompany.app.final_project.Home;
import com.mycompany.app.final_project.models.Fileinfo;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.*;

public class TreeView extends Home {
    public TreeView() {
    }

    ; // Constructor

    @Override
    public TreeItem<String>[] TreeCreate(File dir) {
        File[] fl = dir.listFiles();
        //System.out.println(fl.length);
        int n = fl.length - FilesHiddenCount(dir);
        TreeItem<String>[] A = new TreeItem[n];
        int pos = 0;
        for (File file : fl) {
            if (file.isHidden()) {
                continue;
            }
            if (file.isDirectory()) {
                A[pos] = new TreeItem<>(file.getName(), new ImageView(new Image((ClassLoader.getSystemResourceAsStream("img/folder.png")))));
                try {
                    A[pos].getChildren().addAll(TreeCreate(file));
                    pos++;
                } catch (Exception x) {
                    System.out.println("treecreate " + file.getAbsolutePath() + " " + x.getMessage());
                }
            }
        }
        return A;
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

    private Set<String> loadedDirectories = new HashSet<>();

    @Override
    public void CreateTreeView(javafx.scene.control.TreeView<String> treeView) {
        TreeItem<String> rootNode = new TreeItem<>("This PC", new ImageView(new Image(ClassLoader.getSystemResourceAsStream("img/pc.png"))));
        rootNode.setExpanded(true);

        treeView.setRoot(rootNode);

        treeView.setOnMouseClicked(event -> {
            TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
            if (selectedItem != null && selectedItem.getValue() != null) {
                String path = getFullPath(selectedItem);
                // Chỉ load nếu thư mục chưa được load trước đó và không có thư mục con
                if (!loadedDirectories.contains(path) && selectedItem.getChildren().isEmpty()) {
                    selectedItem.setExpanded(true); // Mở rộng nút khi được click vào
                    loadDirectory(selectedItem, new File(path));
                    loadedDirectories.add(path); // Đánh dấu thư mục đã được load
                }
            }
        });


        File[] sysroots = File.listRoots();
        for (File root : sysroots) {
            TreeItem<String> rootItem = new TreeItem<>(root.getAbsolutePath(), new ImageView(new Image(ClassLoader.getSystemResourceAsStream("img/drive.png"))));
            treeView.getRoot().getChildren().add(rootItem);
            loadDirectory(rootItem, root);
        }
    }

    private String getFullPath(TreeItem<String> item) {
        StringBuilder fullPath = new StringBuilder(item.getValue());
        TreeItem<String> parent = item.getParent();
        while (parent != null && parent.getParent() != null) {
            fullPath.insert(0, parent.getValue() + File.separator);
            parent = parent.getParent();
        }
        return fullPath.toString();
    }

    private void loadDirectory(TreeItem<String> parentItem, File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName().toLowerCase();
                if (file.isDirectory()) {
                    TreeItem<String> directoryNode = new TreeItem<>(file.getName(), new ImageView(new Image(ClassLoader.getSystemResourceAsStream("img/folder.png"))));
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

    @Override
    public void CreateTableView(TableView<Fileinfo> tableView, TableColumn<Fileinfo, String> nameColumn, TableColumn<Fileinfo, String> sizeColumn, TableColumn<Fileinfo, String> dateColumn, TableColumn<Fileinfo, ImageView> imageColumn) {

    }

    @Override
    public void CreateTableView() {

    }

    @Override
    public void CreateTitlesView() {

    }

    @Override
    public void Initialize() {

    }

    @Override
    public void setValues(TableView<Fileinfo> tableView, TableColumn<Fileinfo, ImageView> imageColumn, TableColumn<Fileinfo, String> nameColumn, TableColumn<Fileinfo, String> sizeColumn, TableColumn<Fileinfo, String> dateColumn) {

    }

    @Override
    public void CreateTitles() {

    }
}
