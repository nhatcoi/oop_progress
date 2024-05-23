package com.mycompany.app.hotel_management.controllers.guest;

import com.mycompany.app.hotel_management.entities.Comment;
import com.mycompany.app.hotel_management.entities.Reservation;
import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.service.Impl.RoomServiceImpl;
import com.mycompany.app.hotel_management.utils.Dialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import static com.mycompany.app.hotel_management.controllers.guest.PaymentController.roomBooking;

public class DetailController extends HomeController {

    @FXML
    private Label lbDate1, lbDate2, lbDate3, lbNameRoom, user1, user2, user3, lbType, lbPrice;
    @FXML
    private Text fbText1, fbText2, fbText3;
    @FXML
    private ImageView imgView;
    @FXML
    private TextField tfFeedback;

    private Connection connect;
    private int currentIndex = 0;
    private int currentPage = 0;
    private static final int COMMENTS_PER_PAGE = 3;
    private final ObservableList<Comment> comments = FXCollections.observableArrayList();
    RoomServiceImpl sv = new RoomServiceImpl();
    public void initialize() throws SQLException {
        switchImage(0);
    }

    @FXML
    private void switchRight() throws SQLException {
        switchImage(1);
    }

    @FXML
    private void switchLeft() throws SQLException {
        switchImage(-1);
    }

    private void switchImage(int idx) throws SQLException {
        if (!rooms.isEmpty()) {
            currentIndex = (currentIndex + idx + rooms.size()) % rooms.size();
            Image img = imageCache.computeIfAbsent(currentIndex, key -> sv.fetchImageRoom(rooms.get(currentIndex)));
            imgView.setImage(img);

            Room currentRoom = rooms.get(currentIndex);
            lbNameRoom.setText(currentRoom.getName());
            lbType.setText(currentRoom.getType());
            lbPrice.setText(String.valueOf(currentRoom.getPrice()));
            comments.clear();
            fetchComments(currentRoom.getId());
            setComments();
        }
    }

    private void setComments() {
        int fromIndex = currentPage * COMMENTS_PER_PAGE;
        int toIndex = Math.min(fromIndex + COMMENTS_PER_PAGE, comments.size());
        ObservableList<Comment> setCommentList = FXCollections.observableArrayList(comments.subList(fromIndex, toIndex));

        ObservableList<Label> users = FXCollections.observableArrayList(user1, user2, user3);
        ObservableList<Text> fbTexts = FXCollections.observableArrayList(fbText1, fbText2, fbText3);
        ObservableList<Label> dates = FXCollections.observableArrayList(lbDate1, lbDate2, lbDate3);

        for (int i = 0; i < users.size(); i++) {
            if (i < setCommentList.size()) {
                Comment comment = setCommentList.get(i);
                users.get(i).setText(comment.getName());
                fbTexts.get(i).setText(comment.getComment());
                dates.get(i).setText(new SimpleDateFormat("dd/MM/yyyy").format(comment.getDate()));
            } else {
                users.get(i).setText("#user" + (i + 1));
                fbTexts.get(i).setText("No feedback yet");
                dates.get(i).setText("##/##/####");
            }
        }
    }

    @FXML
    private void booking() {
        Room currentRoom = rooms.get(currentIndex);
        if (roomBooking.contains(currentRoom)) {
            Dialog.showError("Booking", null, "Room " + currentRoom.getName() + " is already in your cart");
            return;
        }
        roomBooking.add(currentRoom);
        Dialog.showInformation("Booking", null, "Room " + currentRoom.getName() + " has been added to your cart");
    }

    ObservableList<Reservation> resAll = FXCollections.observableArrayList();


    @FXML
    private void sendFeedback() throws SQLException {
        String feedback = tfFeedback.getText().trim();
        if (feedback.isEmpty()) {
            Dialog.showError("Feedback", null, "Please enter your feedback");
            return;
        }

        fetchAllReservations();
        boolean hasReservation = resAll.stream().anyMatch(res ->
                res.getUser_id() == guest.getUser_id() && res.getRoom_id() == rooms.get(currentIndex).getId());
        if (!hasReservation) {
            Dialog.showError("Feedback", null, "You must book this room to send feedback");
            return;
        }

        String sql = "INSERT INTO comments (guest_id, room_id, comment, date_submmited) VALUES (" +
                guest.getId() + ", " + rooms.get(currentIndex).getId() + ", '" + feedback + "', now())";
        try (Statement statement = connect.createStatement()) {
            statement.executeUpdate(sql);
        }
        Dialog.showInformation("Feedback", null, "Feedback has been sent successfully");
        tfFeedback.clear();
        switchImage(0);
    }

    private void fetchAllReservations() throws SQLException {
        connect = Database.connectDb();
        String sql = "SELECT * FROM reservations WHERE user_id = " + guest.getUser_id();
        try (Statement statement = connect.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                resAll.add(new Reservation(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("room_id"),
                        rs.getDate("check_in_date"), rs.getDate("check_out_date")));
            }
        }
    }

    private void fetchComments(int roomId) throws SQLException {
        connect = Database.connectDb();
        String sql = "SELECT comments.*, guests.name FROM comments " +
                "INNER JOIN guests ON comments.guest_id = guests.id " +
                "WHERE comments.room_id = " + roomId;
        assert connect != null;
        try (Statement statement = connect.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                comments.add(new Comment(rs.getInt("id"), rs.getString("name"), rs.getString("comment"),
                        rs.getDate("date_submmited"), rs.getInt("guest_id"), rs.getInt("room_id")));
            }
        }
    }

    @FXML
    private void otherComment() {
        currentPage = (currentPage + 1) % (int) Math.ceil((double) comments.size() / COMMENTS_PER_PAGE);
        setComments();
    }
}
