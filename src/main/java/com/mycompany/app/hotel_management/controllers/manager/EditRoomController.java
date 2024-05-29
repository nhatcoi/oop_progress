package com.mycompany.app.hotel_management.controllers.manager;

import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.enums.RoomStatus;
import com.mycompany.app.hotel_management.enums.RoomType;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.service.Impl.RoomServiceImpl;
import com.mycompany.app.hotel_management.utils.Dialog;
import com.mycompany.app.hotel_management.utils.imgTool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.*;

import static com.mycompany.app.hotel_management.controllers.guest.HomeController.imageCache;

public class EditRoomController extends OverviewController {
    @FXML
    private Button btnAdd;
    @FXML
    private TextField nameField, priceField, searchField;
    @FXML
    private ComboBox<String> cbTypeRoom, cbStatus;
    @FXML
    private TableView<Room> tableView;
    @FXML
    private ImageView imgView;

    private Connection connect;
    private final ObservableList<Room> roomList = FXCollections.observableArrayList();
    RoomServiceImpl sv = new RoomServiceImpl();

    @FXML
    public void initialize() throws SQLException {
        populateComboBoxes();
        tableView.setItems(roomList);
        tableView.setOnMouseClicked(this::handleTableClick);
        fetchDataFromDatabase();
    }

    private void populateComboBoxes() {
        for (RoomType value : RoomType.values()) {
            cbTypeRoom.getItems().add(value.getText());
        }
        for (RoomStatus value : RoomStatus.values()) {
            cbStatus.getItems().add(value.getText());
        }
        cbStatus.setValue(cbStatus.getItems().get(0));
        cbTypeRoom.setValue(cbTypeRoom.getItems().get(0));
    }

    private void handleTableClick(MouseEvent event) {
        Room room = tableView.getSelectionModel().getSelectedItem();
        if (room != null) {
            populateFields(room);
            displayRoomImage(room);
        }
    }

    private void populateFields(Room room) {
        nameField.setText(room.getName());
        cbTypeRoom.setValue(room.getType());
        priceField.setText(String.valueOf(room.getPrice()));
        cbStatus.setValue(room.getStatus());
    }

    private void displayRoomImage(Room room) {
        Image img = imageCache.getOrDefault(room.getId(), null);
        if (img == null) {
            img = sv.fetchImageRoom(room);
            imageCache.put(room.getId(), img);
        }
        imgView.setImage(img);
    }
    public void clearInput() {
        nameField.clear();
        priceField.clear();
    }

    public void addData() {
        String name = nameField.getText();
        int type = cbTypeRoom.getSelectionModel().getSelectedIndex();
        int status = cbStatus.getSelectionModel().getSelectedIndex();

        try {
            double price = Double.parseDouble(priceField.getText());
            String query = "INSERT INTO rooms (name, type, status, price) VALUES (?, ?, ?, ?)";
            try (Connection connect = Database.connectDb()) {
                assert connect != null;
                try (PreparedStatement preparedStatement = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                    preparedStatement.setString(1, name);
                    preparedStatement.setInt(2, type);
                    preparedStatement.setInt(3, status);
                    preparedStatement.setDouble(4, price);
                    preparedStatement.executeUpdate();
                    int roomId = -1;
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            roomId = generatedKeys.getInt(1);
                        }
                    }
                    fetchDataFromDatabase();
                    clearInput();
                    saveRoomImage(roomId);
                    Dialog.showInformation("Information", "Information", "Thêm phòng thành công!");
                }
            }
        } catch (NumberFormatException e) {
            Dialog.showError("Error", "Error", "Price phải là số!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveRoomImage(int roomId) throws SQLException {
        Image image = imgView.getImage();
        if (image != null && roomId != -1) {
            String base64 = imgTool.imageViewToBase64(imgView);
            String query = "INSERT INTO pictures (base64, room_id) VALUES (?, ?)";
            try (Connection connect = Database.connectDb()) {
                assert connect != null;
                try (PreparedStatement preparedStatement = connect.prepareStatement(query)) {
                    preparedStatement.setString(1, base64);
                    preparedStatement.setInt(2, roomId);
                    preparedStatement.executeUpdate();
                }
            }
        }
    }

    public void searchData() throws SQLException {
        String search = searchField.getText();
        if (search.isEmpty()) {
            fetchDataFromDatabase();
        } else {
            try (Connection connect = Database.connectDb()) {
                sv.searchRoom(connect, roomList, search);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteData() {
        Room room = tableView.getSelectionModel().getSelectedItem();
        if (room == null) {
            Dialog.showError("Error", "Error", "Chọn phòng cần xóa!");
            return;
        }
        String query = "DELETE FROM rooms WHERE id = ?";
        try (Connection connect = Database.connectDb()) {
            assert connect != null;
            try (PreparedStatement preparedStatement = connect.prepareStatement(query)) {
                preparedStatement.setInt(1, room.getId());
                preparedStatement.executeUpdate();
                fetchDataFromDatabase();
                Dialog.showInformation("Information", "Information", "Xóa " + room.getName() + " thành công!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void fetchDataFromDatabase() throws SQLException {
        String query = "SELECT * FROM rooms";
        try (Connection connect = Database.connectDb()) {
            assert connect != null;
            try (Statement statement = connect.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                roomList.clear();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String type = RoomType.values()[resultSet.getInt("type")].getText();
                    String status = RoomStatus.values()[resultSet.getInt("status")].getText();
                    double price = resultSet.getDouble("price");
                    roomList.add(new Room(id, name, type, status, price));
                }
            }
        }
    }

    public void openSelectFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Photo");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(btnAdd.getScene().getWindow());
        if (selectedFile != null) {
            try {
                String fileUrl = selectedFile.toURI().toURL().toString();
                imgView.setImage(new Image(fileUrl));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void changeData() {
        Room currRoom = tableView.getSelectionModel().getSelectedItem();
        if (currRoom == null) {
            Dialog.showError("Error", "Error", "Chọn phòng cần sửa!");
            return;
        }
        String name = nameField.getText();
        int type = cbTypeRoom.getSelectionModel().getSelectedIndex();
        int status = cbStatus.getSelectionModel().getSelectedIndex();

        try {
            double price = Double.parseDouble(priceField.getText());
            String query = "UPDATE rooms SET name = ?, type = ?, price = ?, status = ? WHERE id = ?";
            try (Connection connect = Database.connectDb()) {
                assert connect != null;
                try (PreparedStatement preparedStatement = connect.prepareStatement(query)) {
                    preparedStatement.setString(1, name);
                    preparedStatement.setInt(2, type);
                    preparedStatement.setDouble(3, price);
                    preparedStatement.setInt(4, status);
                    preparedStatement.setInt(5, currRoom.getId());
                    preparedStatement.executeUpdate();
                    fetchDataFromDatabase();
                    updateRoomImage(currRoom);
                    Dialog.showInformation("Information", "Information", "Cập nhật phòng thành công!");
                }
            }
        } catch (NumberFormatException e) {
            Dialog.showError("Error", "Error", "Price phải là số!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateRoomImage(Room currRoom) throws SQLException {
        Image image = imgView.getImage();
        if (image != null && currRoom.getId() != -1) {
            String base64 = imgTool.imageViewToBase64(imgView);
            String checkQuery = "SELECT COUNT(*) FROM pictures WHERE room_id = ?";
            try (Connection connect = Database.connectDb()) {
                assert connect != null;
                try (PreparedStatement checkStatement = connect.prepareStatement(checkQuery)) {
                    checkStatement.setInt(1, currRoom.getId());
                    try (ResultSet resultSet = checkStatement.executeQuery()) {
                        resultSet.next();
                        int count = resultSet.getInt(1);
                        String query;
                        if (count > 0) {
                            query = "UPDATE pictures SET base64 = ? WHERE room_id = ?";
                        } else {
                            query = "INSERT INTO pictures (base64, room_id) VALUES (?, ?)";
                        }
                        try (PreparedStatement preparedStatement = connect.prepareStatement(query)) {
                            preparedStatement.setString(1, base64);
                            preparedStatement.setInt(2, currRoom.getId());
                            preparedStatement.executeUpdate();
                        }
                    }
                }
            }
        }
    }

    public void refreshTable(ActionEvent actionEvent) throws SQLException {
        fetchDataFromDatabase();
    }
}
