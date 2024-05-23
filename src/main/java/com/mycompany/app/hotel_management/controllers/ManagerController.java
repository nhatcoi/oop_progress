package com.mycompany.app.hotel_management.controllers;

import com.mycompany.app.hotel_management.entities.Guest;
import com.mycompany.app.hotel_management.entities.User;
import com.mycompany.app.hotel_management.enums.UserRole;
import com.mycompany.app.hotel_management.utils.ToolFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static com.mycompany.app.hotel_management.controllers.GuestController.guest;

public class ManagerController extends AuthController {
    @FXML
    private AnchorPane paneHome;
    @FXML
    private AnchorPane showRoomDetails;
    @FXML
    private AnchorPane overview;
    @FXML
    private AnchorPane edit;
    @FXML
    private AnchorPane staff;
    @FXML
    private AnchorPane guests;
    @FXML
    private AnchorPane booking;
    @FXML
    private Label lbName;
    @FXML
    private ImageView imgAvt;
    @FXML
    private Button btnEditStaff;
    @FXML
    private Button btnControl;
    @FXML
    private Button btnUs;

    public static User user;

    public void initialize() {
        // delete 'edit staff' function if user is staff
        if (user != null) {
            lbName.setText(user.getUsername());
            // phân quyền
            btnEditStaff.setVisible(user.getRole() == UserRole.ADMIN.getValue());
        }

        File directory = new File("src/main/resources/com/mycompany/app/img/avt/");
        if (directory.exists() && directory.isDirectory()) {
            // list files in the directory
            File[] files = directory.listFiles();
            if (files != null) {
                // Duyệt qua từng tệp
                for (File file : files) {
                    // Kiểm tra xem tên tệp có giống với user.getId()
                    if (removeExtension(file.getName()).equals(String.valueOf(user.getId()))) {
                        imgAvt.setImage(new javafx.scene.image.Image(file.toURI().toString()));
                        break;
                    }
                }
            }
        }


        showPane(overview);
    }

    private String removeExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int extensionIndex = filename.lastIndexOf('.');
        if (extensionIndex == -1) {
            return filename;
        }
        return filename.substring(0, extensionIndex);
    }

    @FXML
    void signOut() throws IOException, SQLException, ParseException {
        guest = new Guest();
        user = new User();
        super.initialize();
        ToolFXML.openFXML("views/authForm.fxml", 600, 400);
        ToolFXML.closeFXML(paneHome);
    }

    @FXML
    void showRoom(ActionEvent actionEvent) {
        showPane(showRoomDetails);
    }

    @FXML
    void overview(ActionEvent event) {
        showPane(overview);
    }

    @FXML
    void manageRoom(ActionEvent event) {
        showPane(edit);
    }

    @FXML
    void manageGuest(ActionEvent event) {
        showPane(guests);
    }

    @FXML
    void booking(ActionEvent actionEvent) {
        showPane(booking);
    }

    @FXML
    void manageStaff(ActionEvent actionEvent) {
        showPane(staff);
    }


    @FXML
    void aboutUs(ActionEvent event) {
        if (event.getSource() == this.btnUs) {
            try {
                // open link browser
                URI uri = new URI("https://github.com/nhatcoi/project_java");
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(uri);
                } else {
                    System.out.println("Desktop browsing is not supported on this platform.");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    void showPane(AnchorPane paneToShow) {
        List<AnchorPane> allPanes = Arrays.asList(overview, edit, guests, staff, showRoomDetails, booking);
        for (AnchorPane pane : allPanes) {
            pane.setVisible(pane == paneToShow);
        }
    }


    @FXML
    void changeImg(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose an image");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fc.showOpenDialog(btnControl.getScene().getWindow());

        if (file != null) {
            try {
                String fileUrl = file.toURI().toURL().toString();
                imgAvt.setImage(new javafx.scene.image.Image(fileUrl));
                Path source = Paths.get(file.toURI());
                Path target = Paths.get("src/main/resources/com/mycompany/app/img/avt/" + user.getId() + ".png");
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void Info(ActionEvent actionEvent) {

    }
}