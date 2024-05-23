package com.mycompany.app.hotel_management.controllers.guest;

import com.mycompany.app.hotel_management.controllers.GuestController;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.utils.Dialog;
import com.mycompany.app.hotel_management.utils.Md5;
import com.mycompany.app.hotel_management.utils.ToolFXML;
import com.mycompany.app.hotel_management.utils.Security;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;

import static com.mycompany.app.hotel_management.controllers.ManagerController.user;

public class EditProfileController extends GuestController {

    @FXML
    private Label lbName, lbEmail, lbPhone, lbAddress, lbUsername;
    @FXML
    private TextField tfName, tfEmail, tfPhone, tfAddress;
    @FXML
    private PasswordField pfPassword, pfNewPassword, pfConfirmPassword;
    @FXML
    private AnchorPane passwordPane, infoPane, editProfileScreen;
    @FXML
    private ImageView imgAvatar;
    @FXML
    private Hyperlink change;

    private Connection connect;

    public void initialize() throws SQLException {
        long startTime = System.nanoTime();

        setAvatar();
        setUserInfo();

        ToolFXML.test("EditProfile : ", startTime);
    }

    private void setUserInfo() throws SQLException {
        if (lbUsername != null) lbUsername.setText(user.getUsername());
        if (guest != null) {
            if (lbPhone != null) lbPhone.setText(guest.getPhone());
            if (lbAddress != null) lbAddress.setText(guest.getAddress());
            if (lbName != null) lbName.setText(guest.getName());
            if (lbEmail != null) lbEmail.setText(guest.getEmail());
            System.out.println(guest.toString());
        } else {
            clearLabels();
        }
    }

    private void clearLabels() {
        lbEmail.setText("");
        lbName.setText("");
        lbPhone.setText("");
        lbAddress.setText("");
    }

    @FXML
    private void openPassword() {
        passwordPane.setVisible(true);
        infoPane.setVisible(false);
    }

    @FXML
    private void openInfo() {
        infoPane.setVisible(true);
        passwordPane.setVisible(false);
    }

    @FXML
    private void changeAvatar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(change.getScene().getWindow());

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

    private void setAvatar() {
        File directory = new File("src/main/resources/com/mycompany/app/img/avt/");
        if (directory.exists() && directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                if (file != null && removeExtension(file.getName()).equals(String.valueOf(user.getId()))) {
                    imgAvatar.setImage(new javafx.scene.image.Image(file.toURI().toString()));
                    break;
                }
            }
        }
    }

    private String removeExtension(String filename) {
        if (filename == null) return null;
        int extensionIndex = filename.lastIndexOf('.');
        return (extensionIndex == -1) ? filename : filename.substring(0, extensionIndex);
    }

    @FXML
    private void updateInfo() throws SQLException, IOException {
        updateGuestInfo();
        updateDatabase();
        Dialog.showInformation("Success", null, "Information updated successfully");
        initialize();
    }

    private void updateGuestInfo() {
        if (!tfPhone.getText().isEmpty()) guest.setPhone(tfPhone.getText());
        if (!tfAddress.getText().isEmpty()) guest.setAddress(tfAddress.getText());
        if (!tfName.getText().isEmpty()) guest.setName(tfName.getText());
        if (!tfEmail.getText().isEmpty()) {
            guest.setEmail(tfEmail.getText());
            if (Security.validateEmail(tfEmail.getText())) {
                Dialog.showError("Error", null, "Email is invalid");
                return;
            }
        }
    }

    private void updateDatabase() throws SQLException {
        connect = Database.connectDb();
        String sql = "SELECT * FROM guests WHERE user_id = '" + user.getId() + "'";
        Statement statement = connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()) {
            sql = "UPDATE guests SET name = ?, phone = ?, address = ?, email = ? WHERE user_id = ?";
            try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
                pstmt.setString(1, guest.getName());
                pstmt.setString(2, guest.getPhone());
                pstmt.setString(3, guest.getAddress());
                pstmt.setString(4, guest.getEmail());
                pstmt.setInt(5, user.getId());
                pstmt.executeUpdate();
            }
        } else {
            sql = "INSERT INTO guests (user_id, name, phone, address, email) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
                pstmt.setInt(1, user.getId());
                pstmt.setString(2, guest.getName());
                pstmt.setString(3, guest.getPhone());
                pstmt.setString(4, guest.getAddress());
                pstmt.setString(5, guest.getEmail());
                pstmt.executeUpdate();
            }
        }
    }

    @FXML
    private void updatePassword() throws SQLException {
        String pass = pfPassword.getText();
        String newPass = pfNewPassword.getText();
        String confirmPass = pfConfirmPassword.getText();

        if (validatePassword(pass, newPass, confirmPass)) return;

        String password = Md5.hashString(pass);
        String newPassword = Md5.hashString(newPass);

        connect = Database.connectDb();

        String sql0 = "SELECT * FROM guests WHERE user_id = '" + user.getId() + "'";
        Statement statement2 = connect.createStatement();
        ResultSet resultSet2 = statement2.executeQuery(sql0);
        if (resultSet2.next()) guest.setUser_id(resultSet2.getInt("user_id"));

        String sql = "SELECT * FROM users WHERE id = '" + guest.getUser_id() + "' AND password = '" + password + "'";
        Statement statement = connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            sql = "UPDATE users SET password = ? WHERE id = ?";
            try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
                pstmt.setString(1, newPassword);
                pstmt.setInt(2, guest.getUser_id());
                pstmt.executeUpdate();
            }
            Dialog.showInformation("Success", null, "Password updated successfully");
        } else {
            Dialog.showError("Error", null, "Current password is incorrect");
        }
    }

    private boolean validatePassword(String pass, String newPass, String confirmPass) {
        if (pass.isEmpty()) {
            Dialog.showError("Error", null, "Please enter your current password");
            return true;
        }
        if (newPass.isEmpty()) {
            Dialog.showError("Error", null, "Please enter new password");
            return true;
        }
        if (confirmPass.isEmpty()) {
            Dialog.showError("Error", null, "Please enter confirm password");
            return true;
        }
        if (!newPass.equals(confirmPass)) {
            Dialog.showError("Error", null, "Confirm password does not match new password");
            return true;
        }
        if (newPass.equals(pass)) {
            Dialog.showError("Error", null, "New password is the same as the old password");
            return true;
        }
        if (newPass.length() < 6) {
            Dialog.showError("Error", null, "Password must be at least 6 characters");
            return true;
        }
        return false;
    }

    @FXML
    private void phoneSupport() {
        openURL("facetime://0376696037");
    }

    @FXML
    private void cusSupport() {
        openURL("mailto:23010887@st.phenikaa-uni.edu.vn");
    }

    @FXML
    private void contactSupport() {
        openURL("mailto:23010636@st.phenikaa-uni.edu.vn");
    }

    @FXML
    private void aboutUs() {
        openURL("https://github.com/nhatcoi/project_java");
    }

    @FXML
    private void address() {
        openURL("https://s.net.vn/D9Nc");
    }

    private void openURL(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
