package com.mycompany.app.final_project.interfaces;

import com.mycompany.app.final_project.models.Fileinfo;
import javafx.scene.control.*;
import javafx.scene.image.*;

import java.io.File;

public interface IHome {
    Image getIconImage(File f);

    TreeItem<String>[] TreeCreate(File dir);

    String calculateSize(File size);

    String FindAbsolutePath(TreeItem<String> item, String s);

    boolean isDrive(File file);

    int FilesHiddenCount(File dir);

    void CreateTreeView(TreeView<String> treeview);


    void CreateTableView(TableView<Fileinfo> tableView,
                         TableColumn<Fileinfo, String> nameColumn,
                         TableColumn<Fileinfo, String> sizeColumn,
                         TableColumn<Fileinfo, String> dateColumn,
                         TableColumn<Fileinfo, ImageView> imageColumn
    );

    void CreateTableView();

    void CreateTitlesView();

    void setLabelTxt();

    void Initialize();

    void setValues(TableView<Fileinfo> tableView,
                   TableColumn<Fileinfo, ImageView> imageColumn,
                   TableColumn<Fileinfo, String> nameColumn,
                   TableColumn<Fileinfo, String> sizeColumn,
                   TableColumn<Fileinfo, String> dateColumn
    );

    void CreateTitles();
}
