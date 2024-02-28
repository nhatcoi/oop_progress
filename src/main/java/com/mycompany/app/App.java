package com.mycompany.app;

<<<<<<< HEAD
public class App {
    public static void main(String[] args) {
        System.out.println("Group 17");
=======
import com.mycompany.app.week1.E1;
import com.mycompany.app.week2.Time;

public class App {
    public static void main(String[] args) {
        System.out.println("Group 17");
        //

        Time time = new Time();
        time.setHour(9);
        time.setMinute(30);
        time.setSecond(56);
>>>>>>> develop

        System.out.println(time.toString());
    }
}
