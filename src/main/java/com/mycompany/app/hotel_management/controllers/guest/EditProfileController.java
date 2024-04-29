package com.mycompany.app.hotel_management.controllers.guest;


import com.mycompany.app.hotel_management.controllers.GuestController;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.utils.Dialog;
import com.mycompany.app.hotel_management.utils.Md5;
import com.mycompany.app.hotel_management.utils.ToolFXML;
import com.mycompany.app.hotel_management.utils.Validate;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.*;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;


import static com.mycompany.app.hotel_management.controllers.ManagerController.user;


public class EditProfileController extends GuestController {


    public Label lbName;
    public TextField tfName;
    public AnchorPane passwordPane;
    public AnchorPane infoPane;
    public TextField tfEmail;
    public Label lbEmail;
    public AnchorPane editProfileScreen;
    public ImageView imgAvatar;
    public Hyperlink change;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private PasswordField pfNewPassword;
    @FXML
    private PasswordField pfConfirmPassword;
    @FXML
    private TextField tfPhone;

    @FXML
    private TextField tfAddress;


    @FXML
    private Label lbPhone;

    @FXML
    private Label lbAddress;


    @FXML
    private Label lbUsername;

    public Connection connect;

    public void initialize() throws SQLException {
        long startTime = System.nanoTime();

        // set avt
        setAvt();

        // set info
        if (lbUsername != null) {
            lbUsername.setText(user.getUsername());
        }
        if (guest != null) {
            if (lbPhone != null) {
                lbPhone.setText(guest.getPhone());
            }
            if (lbAddress != null) {
                lbAddress.setText(guest.getAddress());
            }
            if (lbName != null) {
                lbName.setText(guest.getName());
            }
            if (lbEmail != null) {
                lbEmail.setText(guest.getEmail());
            }
            System.out.println(guest.toString());
        }

        if (guest == null) {
            lbEmail.setText("");
            lbName.setText("");
            lbPhone.setText("");
            lbAddress.setText("");
        }

        ToolFXML.test("EditProfile : ", startTime);
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
    void openPassword() {
        passwordPane.setVisible(true);
        infoPane.setVisible(false);
    }

    @FXML
    void changeAvatar() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose an image");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fc.showOpenDialog(change.getScene().getWindow());

        if (file != null) {
            try {
                String fileUrl = file.toURI().toURL().toString();
                imgAvatar.setImage(new javafx.scene.image.Image(fileUrl));
                Path source = Paths.get(file.toURI());
                Path target = Paths.get("src/main/resources/com/mycompany/app/img/avt/" + user.getId() + ".png");
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void setAvt() {
        File directory = new File("src/main/resources/com/mycompany/app/img/avt/");
        if (directory.exists() && directory.isDirectory()) {
            // list files in the directory
            File[] files = directory.listFiles();
            if (files != null) {
                // Duyệt qua từng tệp
                for (File file : files) {
                    // Kiểm tra xem tên tệp có giống với user.getId()
                    if (removeExtension(file.getName()).equals(String.valueOf(user.getId()))) {
                        imgAvatar.setImage(new javafx.scene.image.Image(file.toURI().toString()));
                        break;
                    }
                }
            }
        }
    }

    @FXML
    void openInfo() {
        infoPane.setVisible(true);
        passwordPane.setVisible(false);
    }

    @FXML
    void updateInfo() throws SQLException, IOException {
        if (!tfPhone.getText().isEmpty()) {
            guest.setPhone(tfPhone.getText());
        }
        if (!tfAddress.getText().isEmpty()) {
            guest.setAddress(tfAddress.getText());
        }
        if (!tfName.getText().isEmpty()) {
            guest.setName(tfName.getText());
        }
        if (!tfEmail.getText().isEmpty()) {
            guest.setEmail(tfEmail.getText());
            if (Validate.validateEmail(tfEmail.getText())) {
                Dialog.showError("Error", null, "Email is invalid");
                return;
            }
        }

        connect = Database.connectDb();
        String sql = "SELECT * FROM guests WHERE user_id = '" + user.getId() + "'";
        assert connect != null;
        Statement statement = connect.createStatement();

        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            sql = "UPDATE guests SET name = '" + guest.getName() + "', phone = '" + guest.getPhone() + "', address = '" + guest.getAddress() + "', email = '" + guest.getEmail() + "' WHERE user_id = '" + user.getId() + "'";
            statement.executeUpdate(sql);
        } else {
            sql = "INSERT INTO guests (user_id, name, phone, address, email) VALUES ('" + user.getId() + "', '" + guest.getName() + "', '" + guest.getPhone() + "', '" + guest.getAddress() + "', '" + guest.getEmail() + "')";
            statement.executeUpdate(sql);
        }
        Dialog.showInformation("Success", null, "Information updated successfully");
        initialize();
    }

    @FXML
    void updatePassword() throws SQLException {
        String pass = pfPassword.getText();
        String newPass = pfNewPassword.getText();
        String confirmPass = pfConfirmPassword.getText();

        if (pass.isEmpty()) {
            Dialog.showError("Error", null, "Please enter your current password");
            return;
        }
        if (newPass.isEmpty()) {
            Dialog.showError("Error", null, "Please enter new password");
            return;
        }
        if (confirmPass.isEmpty()) {
            Dialog.showError("Error", null, "Please enter confirm password");
            return;
        }
        if (!(newPass.equals(confirmPass))) {
            Dialog.showError("Error", null, "Confirm password is not match with new password");
            return;
        }
        if (newPass.equals(pass)) {
            Dialog.showError("Error", null, "New password is the same as the old password");
            return;
        }
        if (newPass.length() < 6) {
            Dialog.showError("Error", null, "Password must be at least 6 characters");
            return;
        }

        String password = Md5.hashString(pass);
        String newPassword = Md5.hashString(newPass);

        connect = Database.connectDb();

        String sql0 = "SELECT * FROM guests WHERE user_id = '" + user.getId() + "'";
        assert connect != null;
        Statement statement2 = connect.createStatement();
        ResultSet resultSet2 = statement2.executeQuery(sql0);
        if (resultSet2.next())
            guest.setUser_id(resultSet2.getInt("user_id"));

        String sql = "SELECT * FROM users WHERE id = '" + guest.getUser_id() + "' AND password = '" + password + "'";
        assert connect != null;
        Statement statement = connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            sql = "UPDATE users SET password = '" + newPassword + "' WHERE id = '" + guest.getUser_id() + "'";
            statement.executeUpdate(sql);
            Dialog.showInformation("Success", null, "Password updated successfully");
            return;
        } else {
            Dialog.showError("Error", null, "Current password is incorrect");
            return;
        }
    }

    @FXML
    void phoneSupport() {
        String phoneNumber = "0376696037";
        String facetimeURL = "facetime://" + phoneNumber;

        try {
            Desktop.getDesktop().browse(new URI(facetimeURL));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void cusSupport() {
        String mail = "23010887@st.phenikaa-uni.edu.vn";
        String cusSupport = "mailto:" + mail;
        try {
            Desktop.getDesktop().browse(new URI(cusSupport));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void contactSupport() {
        String mail = "23010636@st.phenikaa-uni.edu.vn";
        String cusSupport = "mailto:" + mail;
        try {
            Desktop.getDesktop().browse(new URI(cusSupport));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void aboutUs() {
        String url = "https://github.com/nhatcoi/project_java";
        openBrowser(url);
    }

    @FXML
    void address() {
        String url = "https://s.net.vn/D9Nc";
        openBrowser(url);
    }

    private void openBrowser(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}