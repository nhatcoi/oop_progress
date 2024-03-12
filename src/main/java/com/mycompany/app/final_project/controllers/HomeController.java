package com.mycompany.app.final_project.controllers;

import com.mycompany.app.final_project.Home;
import com.mycompany.app.final_project.Ultis;
import javafx.beans.property.ReadOnlyProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class HomeController implements Initializable {

    @FXML
    private TreeView<String> treeView;
    @FXML
    private Label label;
    static com.mycompany.app.final_project.view.TreeView fx1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fx1 = new com.mycompany.app.final_project.view.TreeView();
        Home.currDirFile = new File("./");
        Home.currDirStr = Home.currDirFile.getAbsolutePath();
        Home.lbl = label;
        label.setText(Home.currDirStr);
        fx1.CreateTreeView(treeView);
        ContextMenu contextMenu = new ContextMenu();


        MenuItem addMenuItem = new MenuItem("Thêm File");
        addMenuItem.setOnAction(event -> {
            TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
            Home.createNewFile(selectedItem);
        });

        MenuItem addMenuItem2 = new MenuItem("Thêm Folder");
        addMenuItem2.setOnAction(event -> {
            TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
            Home.createNewFolder(selectedItem);
        });


        MenuItem editMenuItem = new MenuItem("Sửa");
        editMenuItem.setOnAction(event -> {
            TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
            Home.renameFileOrFolder(selectedItem);
            System.out.println("Sửa " + selectedItem.getValue());
        });

        MenuItem deleteMenuItem = new MenuItem("Xóa");
        deleteMenuItem.setOnAction(event -> {
            TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
            Home.removeFileOrFolder(selectedItem);
        });
        contextMenu.getItems().addAll(addMenuItem, addMenuItem2, editMenuItem, deleteMenuItem);
        treeView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {


                    contextMenu.show(treeView, event.getScreenX(), event.getScreenY());
                }
            }
        });

        TreeItem<String> rootItem = treeView.getRoot();
        addExpandListener(rootItem);
    }


    @FXML
    private void handleMouseClicked() {
        try {
            TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
            Home.currDirName = item.getValue();
            Home.currDirFile = new File(fx1.FindAbsolutePath(item, item.getValue()));
            Home.currDirStr = Home.currDirFile.getAbsolutePath();
            System.out.println(Home.currDirStr);
            label.setText(Home.currDirStr);
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
            if (newValue) {
                TreeItem<String> treeItem = (TreeItem<String>) ((ReadOnlyProperty<?>) observable).getBean();
                System.out.println("Expanded: " + treeItem.getValue());
                if (treeItem.getChildren().size() == 1 && treeItem.getChildren().get(0).getValue() == null) {
                    treeItem.getChildren().clear();
                    if (treeItem.getValue() != null) {
                        String path = Ultis.getFullPath(item);
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
        item.getChildren().forEach(this::addExpandListener);
    }

}
