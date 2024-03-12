package com.mycompany.app.final_project.controllers;

import com.mycompany.app.final_project.Home;
import com.mycompany.app.final_project.Ultis;
import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class HomeController implements Initializable {

    @FXML
    private Button btn;
    @FXML
    private TreeView<String> treeView;
    @FXML
    private Label label;
    private int count;
    static com.mycompany.app.final_project.view.TreeView fx1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        count = 0;
        fx1 = new com.mycompany.app.final_project.view.TreeView();
        fx1.currDirFile = new File("./");
        fx1.currDirStr = fx1.currDirFile.getAbsolutePath();
        fx1.lbl = label;
        label.setText(fx1.currDirStr);
        fx1.CreateTreeView(treeView);
        ContextMenu contextMenu = new ContextMenu();


        MenuItem addMenuItem = new MenuItem("Thêm File");
        addMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
                Home.createNewFile(selectedItem);
            }
        });

        MenuItem addMenuItem2 = new MenuItem("Thêm Folder");
        addMenuItem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
                Home.createNewFolder(selectedItem);
            }
        });


        MenuItem editMenuItem = new MenuItem("Sửa");
        editMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
                Home.renameFileOrFolder(selectedItem);
                System.out.println("Sửa " + selectedItem.getValue());
            }
        });

        MenuItem deleteMenuItem = new MenuItem("Xóa");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
                System.out.println("Xóa " + selectedItem.getValue());
            }
        });
        contextMenu.getItems().addAll(addMenuItem, addMenuItem2, editMenuItem, deleteMenuItem);
        treeView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY) {
                    TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
                    if (selectedItem != null) {


                        contextMenu.show(treeView, event.getScreenX(), event.getScreenY());
                    }
                }
            }
        });

        TreeItem<String> rootItem = treeView.getRoot();
        addExpandListener(rootItem);
    }


    @FXML
    private void handleMouseClicked(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
            fx1.currDirName = item.getValue();
            fx1.currDirFile = new File(fx1.FindAbsolutePath(item, item.getValue()));
            fx1.currDirStr = fx1.currDirFile.getAbsolutePath();
            System.out.println(fx1.currDirStr);
            label.setText(fx1.currDirStr);
            TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
            if (selectedItem != null && selectedItem.getValue() != null) {
                String path = Ultis.getFullPath(selectedItem);
                if (selectedItem.getChildren().isEmpty()) {
                    selectedItem.setExpanded(true);
                    Ultis.loadDirectory(selectedItem, new File(path));
                } else {
                    selectedItem.getChildren().clear();
                    Ultis.loadDirectory(selectedItem, new File(path));
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addExpandListener(TreeItem<String> item) {
        item.expandedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) { // Nếu nút được mở rộng
                TreeItem<String> treeItem = (TreeItem<String>) ((ReadOnlyProperty<?>) observable).getBean();
                if (treeItem.getChildren().size() == 1 && treeItem.getChildren().get(0).getValue() == null) { // Nếu chỉ có một nút giả
                    treeItem.getChildren().clear(); // Xóa nút giả
                    // Thực hiện các hành động khác khi mở rộng nút ở đây
                    if (treeItem != null && treeItem.getValue() != null) {
                        String path = Ultis.getFullPath(treeItem);
                        if (treeItem.getChildren().isEmpty()) {
                            treeItem.setExpanded(true);
                            Ultis.loadDirectory(treeItem, new File(path));
                        } else {
                            treeItem.getChildren().clear();
                            Ultis.loadDirectory(treeItem, new File(path));
                        }
                    }
                }
            }
        });

        // Đệ quy để thêm trình lắng nghe cho tất cả các nút con của nút hiện tại
        item.getChildren().forEach(this::addExpandListener);
    }

}
