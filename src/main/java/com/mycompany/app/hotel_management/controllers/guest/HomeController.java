package com.mycompany.app.hotel_management.controllers.guest;

import com.mycompany.app.hotel_management.controllers.GuestController;
import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.service.Impl.RoomServiceImpl;
import com.mycompany.app.hotel_management.utils.Dialog;
import com.mycompany.app.hotel_management.utils.ToolFXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.mycompany.app.hotel_management.controllers.guest.PaymentController.roomBooking;

public class HomeController extends GuestController {

    @FXML
    private AnchorPane homePane;
    @FXML
    private ImageView image1, image2, image3;
    @FXML
    private Label lbPrice1, lbPrice2, lbPrice3, lbName1, lbName2, lbName3, clockLabel;
    @FXML
    private Label cmSize1, cmSize2, cmSize3;
    @FXML
    private TextField tfSearch;

    private Connection connect;

    public static ObservableList<Room> rooms = FXCollections.observableArrayList();
    public static Map<Integer, Image> imageCache = new HashMap<>();

    private final RoomServiceImpl roomService = new RoomServiceImpl();
    private List<Room> selectedRooms;
    private final int TAG = 3;
    private int[] idxTag = new int[TAG];
    public void initialize() throws SQLException {
        long startTime = System.nanoTime();
        rooms = roomsIni;
        randomRoom(rooms);
        initializeClock();
        ToolFXML.test("Home : ", startTime);
    }

    private void initializeClock() {
        Timeline clockTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, event -> {
                    LocalTime currentTime = LocalTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    clockLabel.setText(currentTime.format(formatter));
                }),
                new KeyFrame(Duration.seconds(1))
        );
        clockTimeline.setCycleCount(Animation.INDEFINITE);
        clockTimeline.play();
    }

    public void randomRoom(ObservableList<Room> rooms) throws SQLException {
        List<Label> names = List.of(lbName1, lbName2, lbName3);
        List<Label> prices = List.of(lbPrice1, lbPrice2, lbPrice3);

        int numLabels = names.size();

        // Nếu số phòng ít hơn số nhãn, lấy tất cả phòng có sẵn
        if (rooms.size() < numLabels) {
            selectedRooms = new ArrayList<>(rooms);
            // Thêm các phòng ngẫu nhiên từ danh sách ban đầu cho đến khi đủ số lượng nhãn
            for (int i = selectedRooms.size(); i < numLabels; i++) {
                selectedRooms.add(HomeController.rooms.get(i % HomeController.rooms.size()));
            }
        } else {
            selectedRooms = getRandomSublist(rooms, numLabels);
        }

        // Hiển thị thông tin phòng lên các nhãn
        IntStream.range(0, numLabels).forEach(i -> {
            Room room = selectedRooms.get(i);
            names.get(i).setText(room.getName());
            prices.get(i).setText(String.valueOf(room.getPrice()));
            idxTag[i] = rooms.indexOf(room);
            matchImg(room, i);
        });
    }

    private List<Room> getRandomSublist(ObservableList<Room> rooms, int size) {
        Random rand = new Random();
        return rand.ints(0, rooms.size())
                .distinct()
                .limit(size)
                .mapToObj(rooms::get)
                .collect(Collectors.toList());
    }

    private void matchImg(Room room, int idx) {
        List<ImageView> imageList = List.of(image1, image2, image3);
        Image img = imageCache.computeIfAbsent(rooms.indexOf(room), key -> roomService.fetchImageRoom(room));
        imageList.get(idx).setImage(img);
    }

    @FXML
    void otherRoom() throws SQLException {
        randomRoom(rooms);
    }

    @FXML
    public void searchRoom() throws SQLException {
        String search = tfSearch.getText().trim().toLowerCase();
        if (search.isEmpty()) {
            return;
        }

        ObservableList<Room> searchRooms = rooms.stream()
                .filter(room -> room.getName().toLowerCase().contains(search))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        randomRoom(searchRooms);

        // Lọc lại index sau search
        Map<Room, Integer> roomIndexMap = IntStream.range(0, rooms.size())
                .boxed()
                .collect(Collectors.toMap(rooms::get, i -> i));
        idxTag = selectedRooms.stream()
                .filter(roomIndexMap::containsKey)
                .mapToInt(roomIndexMap::get)
                .toArray();
    }

    @FXML
    public void booking1() {
        booking(0);
    }

    @FXML
    public void booking2() {
        booking(1);
    }

    @FXML
    public void booking3() {
        booking(2);
    }

    private void booking(int idx) {
        Room room = rooms.get(idxTag[idx]);
        if (roomBooking.contains(room)) {
            Dialog.showError("Room is already in cart", null, "Room " + room.getName() + " is already in cart, please open cart to check in room");
            return;
        }
        roomBooking.add(room);
        Dialog.showInformation("Add to Cart", null, "Add room " + room.getName() + " to payment Successfully. Carry out payment to finish booking.");
    }
}
