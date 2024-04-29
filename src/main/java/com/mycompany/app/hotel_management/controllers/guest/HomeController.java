package com.mycompany.app.hotel_management.controllers.guest;

import com.mycompany.app.hotel_management.controllers.GuestController;
import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.service.Impl.RoomServiceImpl;
import com.mycompany.app.hotel_management.utils.Dialog;
import com.mycompany.app.hotel_management.utils.ToolFXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.mycompany.app.hotel_management.controllers.guest.PaymentController.roomBooking;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class HomeController extends GuestController {

    @FXML
    private AnchorPane homePane;
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private ImageView image3;

    @FXML
    private Label lbPrice2;

    @FXML
    private Label lbPrice1;

    @FXML
    private Label lbName1;

    @FXML
    private Label lbName2;

    @FXML
    private Label lbName3;

    @FXML
    private TextField tfSearch;

    @FXML
    private Label lbPrice3;
    @FXML
    private Label clockLabel;

    Connection connect;


    static ObservableList<Room> rooms = FXCollections.observableArrayList();
    static ObservableList<Image> images = FXCollections.observableArrayList();
    RoomServiceImpl sv = new RoomServiceImpl();
    int[] index = new int[3];
    List<Room> selectedRooms;

    private void randomRoom(ObservableList<Room> rooms, ObservableList<Image> images) throws SQLException {
        List<Label> names = List.of(lbName1, lbName2, lbName3);
        List<Label> prices = List.of(lbPrice1, lbPrice2, lbPrice3);

        if (rooms.size() > names.size()) {
            selectedRooms = getRandomSublist(rooms, names.size());
        } else {
            selectedRooms = rooms;
        }
        IntStream.range(0, selectedRooms.size())
                .forEach(i -> {
                    names.get(i).setText(selectedRooms.get(i).getName());
                    prices.get(i).setText(String.valueOf(selectedRooms.get(i).getPrice()));
                    index[i] = rooms.indexOf(selectedRooms.get(i));
                    matchImg(images, index, i);
                });
    }

    private List<Room> getRandomSublist(List<Room> list, int size) {
        List<Room> sublist = new ArrayList<>(list);
        Collections.shuffle(sublist, new Random());
        return sublist.subList(0, size);
    }

    private void matchImg(ObservableList<Image> images, int[] index, int i) {
        List<ImageView> imageList = List.of(image1, image2, image3);
        imageList.get(i).setImage(images.get(index[i]));
    }

    @FXML
    void otherRoom() throws SQLException {
        randomRoom(rooms, images);
    }

    @FXML
    public void searchRoom() throws SQLException {
        String search = tfSearch.getText().trim();
        if (search.isEmpty()) {
            sv.getAllRoom(connect, rooms, "rooms");
            images = sv.getImage(connect, rooms, images);
        } else {
            try {
                ObservableList<Room> searchRooms = FXCollections.observableArrayList();
                ObservableList<Image> searchImages = FXCollections.observableArrayList();
                for (int i = 0; i < rooms.size(); i++) {
                    if (rooms.get(i).getName().contains(search)) {
                        searchRooms.add(rooms.get(i));
                        searchImages.add(images.get(i));
                    }
                }
                randomRoom(searchRooms, searchImages);

                // Lọc lại index sau search
                Map<Room, Integer> roomIndexMap = IntStream.range(0, rooms.size())
                        .boxed()
                        .collect(Collectors.toMap(rooms::get, i -> i));
                index = selectedRooms.stream()
                        .filter(roomIndexMap::containsKey)
                        .mapToInt(roomIndexMap::get)
                        .toArray();
                Arrays.stream(index).forEach(System.out::println);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void booking1() {
        booking(1);
    }

    public void booking2() {
        booking(2);
    }

    public void booking3() {
        booking(3);
    }

    void booking(int idxTag) {
        idxTag = idxTag - 1;
//        if(rooms.get(id[idxTag]).getStatus().equals(RoomStatus.OCCUPIED.getText())) {
//            Dialog.showError("Room is occupied", null, "Room " + rooms.get(id[idxTag]).getName() + " is occupied, please choose another room");
//            return;
//        }

//        int a = 0;
//        for(Room room : selectedRooms) {
//            for(int i = 0; i < rooms.size(); i++) {
//                if(room.equals(rooms.get(i))) {
//                    index[a] = i;
//                    System.out.println(index[a]);
//                    a++;
//                    break;
//                }
//            }
//        }

        if (roomBooking.contains(rooms.get(index[idxTag]))) {
            Dialog.showError("Room is already in cart", null, "Room " + rooms.get(index[idxTag]).getName() + " is already in cart, please open cart to check in room");
            return;
        }
        roomBooking.add(rooms.get(index[idxTag]));
        Dialog.showInformation("Add to Cart", null, "Add room " + rooms.get(index[idxTag]).getName() + " to payment Successfully \nCarry out payment to finish booking");
    }


    public void initialize() throws SQLException {
        long startTime = System.nanoTime();

        rooms = roomsIni;
        images = imagesIni;
        randomRoom(rooms, images);

        for (Room room : rooms) {
            System.out.println(room.toString());
        }

        // clock
        Timeline clockTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        LocalTime currentTime = LocalTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String formattedTime = currentTime.format(formatter);
                        clockLabel.setText(formattedTime);
                    }
                }),
                new KeyFrame(Duration.seconds(1))
        );
        clockTimeline.setCycleCount(Animation.INDEFINITE);
        clockTimeline.play();


        ToolFXML.test("Home : ", startTime);
    }
}