package com.mycompany.app.final_project.interfaces;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public interface IHome {

    String FindAbsolutePath(TreeItem<String> item, String s);

    void CreateTreeView(TreeView<String> treeview);


    void setLabelTxt();

    void Initialize();

}
