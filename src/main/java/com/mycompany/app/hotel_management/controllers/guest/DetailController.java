package com.mycompany.app.hotel_management.controllers.guest;

import com.mycompany.app.hotel_management.entities.Comment;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.utils.Dialog;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.text.SimpleDateFormat;
import java.util.Collections;

import static com.mycompany.app.hotel_management.controllers.guest.PaymentController.roomBooking;

public class DetailController extends HomeController {

    @FXML
    private Label lbDate1;

    @FXML
    private Label lbDate2;

    @FXML
    private Label lbDate3;

    @FXML
    private Label lbNameRoom;

    @FXML
    private Label user1;

    @FXML
    private Label user2;

    @FXML
    private Text fbText1;

    @FXML
    private Text fbText2;

    @FXML
    private Label lbType;

    @FXML
    private Text fbText3;

    @FXML
    private ImageView imgView;

    @FXML
    private Label lbPrice;

    @FXML
    private TextField tfFeedback;

    @FXML
    private Label user3;

    Connection connect;

    int currentIndex = 0;
    ObservableList<Comment> comments = FXCollections.observableArrayList();

    public void initialize() throws SQLException {
        switchImage(0);
    }
    @FXML
    void switchRight() throws SQLException {
        switchImage(1);
    }

    @FXML
    void switchLeft() throws SQLException {
        switchImage(-1);
    }

    void switchImage(int idx) throws SQLException {
        if (!images.isEmpty()) {
            currentIndex = (currentIndex + idx + images.size()) % images.size();
            imgView.setImage(images.get(currentIndex));
            lbNameRoom.setText(rooms.get(currentIndex).getName());
            lbType.setText(rooms.get(currentIndex).getType());
            lbPrice.setText(String.valueOf(rooms.get(currentIndex).getPrice()));
            comments.clear();

            fetchComment(currentIndex);
            setComments();
        }
    }

    private void setComments() {
        Collections.shuffle(comments);
        // Tạo một list random mới chứa 3 phần tử đầu tiên sau khi đã xáo trộn
        ObservableList<Comment> randomComments = FXCollections.observableArrayList(comments.subList(0, Math.min(comments.size(), 3)));

        // Sử dụng list randomComments để update ui
        ObservableList<Label> users = FXCollections.observableArrayList(user1, user2, user3);
        ObservableList<Text> fbTexts = FXCollections.observableArrayList(fbText1, fbText2, fbText3);
        ObservableList<Label> dates = FXCollections.observableArrayList(lbDate1, lbDate2, lbDate3);

        for(int i = 0; i < users.size(); i++) {
            if (i < randomComments.size()) {
                // Nếu còn comment trong list randomComments
                users.get(i).setText(randomComments.get(i).getName());
                fbTexts.get(i).setText(randomComments.get(i).getComment());
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String dateString = sdf.format(randomComments.get(i).getDate());
                dates.get(i).setText(dateString);
            } else {
                // list < 3
                users.get(i).setText("#user" + (i + 1));
                fbTexts.get(i).setText("No feedback yet");
                dates.get(i).setText("##/##/####");
            }
        }
    }

    @FXML
    void booking() {
        if(roomBooking.contains(rooms.get(currentIndex))) {
            Dialog.showError("Booking", null, "Room " + rooms.get(currentIndex).getName() + " is already in your cart");
            return;
        }
        roomBooking.add(rooms.get(currentIndex));
        Dialog.showInformation("Booking", null, "Room " + rooms.get(currentIndex).getName() + " has been added to your cart");
    }

    @FXML
    void send() throws SQLException {
        String feedback = tfFeedback.getText();

        if(feedback.isEmpty()) {
            Dialog.showError("Feedback", null, "Please enter your feedback");
            return;
        }
        System.out.println(guest.getId());
        String sql = "INSERT INTO comments (guest_id, room_id, comment, date_submmited) VALUES ('" + guest.getId() + "', '" + rooms.get(currentIndex).getId() + "', '" + feedback + "', NOW())";
        assert connect != null;
        Statement statement = connect.createStatement();
        statement.executeUpdate(sql);
        Dialog.showInformation("Feedback", null, "Feedback has been sent successfully");
        tfFeedback.clear();
        switchImage(0);
    }

    private void fetchComment(int currentComment) throws SQLException {
        connect = Database.connectDb();
        String sql = "SELECT comments.*, guests.name FROM comments " +
                "INNER JOIN guests ON comments.guest_id = guests.id " +
                "WHERE comments.room_id = " + rooms.get(currentComment).getId();
        assert connect != null;
        Statement statement = connect.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (!rs.next()) {
            System.out.println("No comment");
        } else {
            do {
                Comment comment = new Comment();
                comment.setId(rs.getInt("id"));
                comment.setName(rs.getString("name"));
                comment.setComment(rs.getString("comment"));
                comment.setDate(rs.getDate("date_submmited"));
                comment.setGuest_id(rs.getInt("guest_id"));
                comment.setRoom_id(rs.getInt("room_id"));
                comments.add(comment);
            } while (rs.next());
        }
    }
}