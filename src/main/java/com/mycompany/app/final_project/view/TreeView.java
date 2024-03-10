package com.mycompany.app.final_project.view;

import com.mycompany.app.final_project.Home;
import com.mycompany.app.final_project.models.Fileinfo;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.Objects;

public class TreeView extends Home {
    TreeView() {
    }

    ; // Constructor

    @Override
    public TreeItem<String>[] TreeCreate(File dir) {
        TreeItem<String>[] A = null;
        File[] fl = dir.listFiles();
        assert fl != null;
        int n = fl.length - FilesHiddenCount(dir);
        A = new TreeItem[n];
        int pos = 0;
        for (File file : fl) {

            if (!file.isFile() && !file.isHidden() && file.isDirectory() && n == 0) {
                A[pos] = new TreeItem<>(file.getName(), new ImageView(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("img/folderOpen.png")))));
                pos++;
            } else if (!file.isFile() && !file.isHidden() && file.isDirectory() && n > 0) {
                A[pos] = new TreeItem<>(file.getName(), new ImageView(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("img/folderOpen.png")))));
                try {
                    A[pos].getChildren().addAll(TreeCreate(file));
                    pos++;
                } catch (Exception x) {
                    System.out.println("Exception x in treecreate at " + file.getAbsolutePath() + " " + x.getMessage());
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

    @Override
    public void CreateTreeView(javafx.scene.control.TreeView<String> treeview) {
        File[] sysroots = File.listRoots();
        TreeItem<String> ThisPc = new TreeItem<>("This PC", new ImageView(new Image(ClassLoader.getSystemResourceAsStream("img/pc.png"))));
        TreeItem<String>[] drives = new TreeItem[sysroots.length];
        for (int i = 0; i < sysroots.length; i++) {
            drives[i] = new TreeItem<>(sysroots[i].getAbsolutePath(), new ImageView(new Image(ClassLoader.getSystemResourceAsStream("img/thumb_Hard_Drive.png"))));
            try {
                drives[i].getChildren().addAll(TreeCreate(sysroots[i]));
            } catch (NullPointerException x) {
                System.out.println("Exeption x detected: " + x.fillInStackTrace() + drives[i].toString());
            } finally {
                ThisPc.getChildren().add(drives[i]);
            }
        }
        treeview.setRoot(ThisPc);
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
