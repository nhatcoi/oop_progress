package com.mycompany.app.hotel_management.controllers.manager;

import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.enums.RoomStatus;
import com.mycompany.app.hotel_management.enums.RoomType;
import com.mycompany.app.hotel_management.repositories.database;
import com.mycompany.app.hotel_management.utils.Dialog;
import com.mycompany.app.hotel_management.utils.imgTool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

import static com.mycompany.app.hotel_management.controllers.manager.OverviewController.roomList;

public class EditRoomController extends OverviewController{
    public Button btnAdd;
    public TextField rnameField;
    public ComboBox<String> cbTypeRoom;
    public TextField rpriceField;
    public TableView<Room> tableView;
    public TextField searchField;
    public Button btnSearch;
    public ImageView imgView;

    Connection connect;
//    private final ObservableList<Room> roomList = FXCollections.observableArrayList();


    public void initialize() throws SQLException {
        // Add data to combobox
        for (RoomType value : RoomType.values()) {
            cbTypeRoom.getItems().add(value.getText());
        }
        if (!cbTypeRoom.getItems().isEmpty()) {
            cbTypeRoom.setValue(cbTypeRoom.getItems().get(0));
        }

        // Add data to tableview
        tableView.setItems(roomList);

        // Click to show data
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                //if (event.getClickCount() == 2) { // Double click
                    Room room = tableView.getSelectionModel().getSelectedItem();
                    if (room != null) {
                        rnameField.setText(room.getName());
                        cbTypeRoom.setValue(room.getType());
                        rpriceField.setText(String.valueOf(room.getPrice()));
                        connect = database.connectDb();
                        String query = "SELECT * FROM pictures WHERE room_id = " + room.getId();
                        try {
                            assert connect != null;
                            ResultSet resultSet = connect.createStatement().executeQuery(query);
                            if (resultSet.next()) {
                                String base64 = resultSet.getString("base64");
                                Image image = imgTool.base64ToImage(base64);
                                imgView.setImage(image);
                            }
                            else {
                                imgView.setImage(null);
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                //}
            }
        });
    }
    public void clearInput() {
        rnameField.clear();
        rpriceField.clear();
    }

    public void addData() {
        connect = database.connectDb();
        String name = rnameField.getText();
        int type = cbTypeRoom.getSelectionModel().getSelectedIndex();

        try {
            double price = Double.parseDouble(rpriceField.getText());
            String query = "INSERT INTO rooms (name, type, status, price) VALUES (?, ?, ?, ?)";
            try {
                assert connect != null;
                PreparedStatement preparedStatement = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, type);
                preparedStatement.setInt(3, RoomStatus.AVAILABLE.ordinal());
                preparedStatement.setDouble(4, price);
                preparedStatement.executeUpdate();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

                int roomId = -1; // Giá trị mặc định nếu không có ID được trả về
                if (generatedKeys.next()) {
                    roomId = generatedKeys.getInt(1);
                }
//                fetchDataFromDatabase();
                clearInput();

                Image image = imgView.getImage();
                if(image != null)
                {
                    String base64 = imgTool.imageViewToBase64(imgView);
                    if(roomId != -1)
                    {
                        query = "INSERT INTO pictures (base64, room_id) VALUES (?, ?)";
                        preparedStatement = connect.prepareStatement(query);
                        preparedStatement.setString(1, base64);
                        preparedStatement.setInt(2, roomId);
                        preparedStatement.executeUpdate();
                    }
                    //System.out.println(base64);
                } Dialog.showInformation("Information", "Information", "Thêm phòng thành công!");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (NumberFormatException e) {
            Dialog.showError("Error", "Error", "Price Phải là số!");
            return;
        }
    }

    public void searchData() throws SQLException {
        String search = searchField.getText();
        if (search.isEmpty()) {
            fetchDataFromDatabase();
        } else {
            try {
                connect = database.connectDb();
                String query = "SELECT * FROM rooms WHERE name LIKE '%" + search + "%'";
                assert connect != null;
                ResultSet resultSet = connect.createStatement().executeQuery(query);
                roomList.clear();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String type = RoomType.values()[resultSet.getInt("type")].getText();
                    String status = RoomStatus.values()[resultSet.getInt("status")].getText();
                    double price = resultSet.getDouble("price");

                    roomList.add(new Room(id, name, type, status, price));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    public void deleteData() {
        Room room = tableView.getSelectionModel().getSelectedItem();
        if (room == null) {
            Dialog.showError("Error", "Error", "Chọn phòng cần xóa!");
            return;
        }
        connect = database.connectDb();
        String query = "DELETE FROM rooms WHERE id = " + room.getId();
        try {
            assert connect != null;
            connect.createStatement().executeUpdate(query);
//            fetchDataFromDatabase();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Dialog.showInformation("Information", "Information", "Xóa" + room.getName() + " thành công!");
    }

    private void fetchDataFromDatabase() throws SQLException {

        connect = database.connectDb();
        String query = "SELECT * FROM rooms";
        assert connect != null;
        ResultSet resultSet = connect.createStatement().executeQuery(query);
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
                System.out.println("Selected File URL: " + fileUrl);
                imgView.setImage(new javafx.scene.image.Image(fileUrl));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    public void changeData() {
        Room currRoom = tableView.getSelectionModel().getSelectedItem();
        Image currImg = imgView.getImage();
        if (currRoom == null) {
            Dialog.showError("Error", "Error", "Chọn phòng cần sửa!");
            return;
        }

        connect = database.connectDb();

        String name = rnameField.getText();
        int type = cbTypeRoom.getSelectionModel().getSelectedIndex();

        try {
            double price = Double.parseDouble(rpriceField.getText());
            String query = "UPDATE rooms SET name = ?, type = ?, price = ? WHERE id = ?";
            try {
                assert connect != null;
                PreparedStatement preparedStatement = connect.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, type);
                preparedStatement.setDouble(3, price);
                preparedStatement.setInt(4, currRoom.getId());
                preparedStatement.executeUpdate();
//                fetchDataFromDatabase();

                Image image = imgView.getImage();
                if(image != null)
                {
                    String base64 = imgTool.imageViewToBase64(imgView);
                    if(currRoom.getId() != -1)
                    {
                        String checkQuery = "SELECT COUNT(*) FROM pictures WHERE room_id = ?";
                        PreparedStatement checkStatement = connect.prepareStatement(checkQuery);
                        checkStatement.setInt(1, currRoom.getId());
                        ResultSet resultSet = checkStatement.executeQuery();
                        resultSet.next();
                        int count = resultSet.getInt(1);

                        if (count > 0) {
                            // Nếu có bản ghi có room_id tương ứng, thực hiện cập nhật
                            query = "UPDATE pictures SET base64 = ? WHERE room_id = ?";
                            preparedStatement = connect.prepareStatement(query);
                            preparedStatement.setString(1, base64);
                            preparedStatement.setInt(2, currRoom.getId());
                            preparedStatement.executeUpdate();
                        } else {
                            // Nếu không có bản ghi có room_id tương ứng, thực hiện chèn dữ liệu mới
                            query = "INSERT INTO pictures (base64,room_id) VALUES (?, ?)";
                            preparedStatement = connect.prepareStatement(query);
                            preparedStatement.setInt(2, currRoom.getId()); // Giả sử roomId là ID của phòng mà bạn đang thao tác
                            preparedStatement.setString(1, base64);
                            preparedStatement.executeUpdate();
                        }
                    }
                    //System.out.println(base64);
                }

                Dialog.showInformation("Information", "Information", "Cập nhật phòng thành công!");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (NumberFormatException e) {
            Dialog.showError("Error", "Error", "Price Phải là số!");
            return;
        }
    }

    public void refreshTable(ActionEvent actionEvent) throws SQLException {
        super.initialize();
        initialize();
    }
}
