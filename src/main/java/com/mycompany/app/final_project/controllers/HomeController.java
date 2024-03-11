package com.mycompany.app.final_project.controllers;

import com.mycompany.app.final_project.interfaces.IHome;
import com.mycompany.app.final_project.models.Fileinfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.ResourceBundle;

import static javafx.embed.swing.SwingFXUtils.toFXImage;

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
    }

    @FXML
    private void handleMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 1) {
            try {
                TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
                fx1.currDirName = item.getValue();
                fx1.currDirFile = new File(fx1.FindAbsolutePath(item, item.getValue()));
                label.setText(fx1.currDirStr);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
