package com.mycompany.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import com.mycompany.app.Debug.Debug;

import com.mycompany.app.week2.code2.interfaces.Ex3;
import com.mycompany.app.week2.code2.interfaces.Ex3.*;
import com.mycompany.app.week2.code2.polymorphism.ex1_5_17.Bicycle;
import com.mycompany.app.week2.code2.polymorphism.ex1_5_17.Cycle;
import com.mycompany.app.week2.code2.polymorphism.ex1_5_17.Tricycle;
import com.mycompany.app.week2.code2.polymorphism.ex1_5_17.Unicycle;
import com.mycompany.app.week2.code3.Classes;
import com.mycompany.app.week3.code1.DisruptLecture;
import com.mycompany.app.week3.code1.GlyphTest;
import com.mycompany.app.week3.code1.TestArithmetic;
import com.mycompany.app.week3.code1.Transmogrify;
import com.mycompany.app.week3.code2.InterfacesExercise21.InnerInterfacesExercise21;
import com.mycompany.app.week3.code2.InterfacesExercise21.InnerClass;
import com.mycompany.app.week3.code3.Abstraction;
import com.mycompany.app.week3.code3.Polymorphism;
import com.mycompany.app.week3.code3.Polymorphism.*;

import static com.mycompany.app.week2.code3.Methods.myMethod;

import com.mycompany.app.week3.code1.DisruptLecture.*;
import com.mycompany.app.week3.code1.TestArithmetic.*;
import com.mycompany.app.week3.code1.Transmogrify.*;

public class App extends Application {
    static Debug debug = new Debug();

    @Override
    public void start(Stage primaryStage) {
        // Tạo một nút
        Button btn = new Button();
        btn.setText("Click me!");

        // Xử lý sự kiện khi nút được nhấn
        btn.setOnAction(e -> System.out.println("Hello, JavaFX!"));

        // Tạo một StackPane (layout container) và thêm nút vào đó
        StackPane root = new StackPane();
        root.getChildren().add(btn);

        // Tạo một Scene với StackPane làm nội dung, cài đặt kích thước
        Scene scene = new Scene(root, 300, 250);

        // Đặt tiêu đề của cửa sổ
        primaryStage.setTitle("Hello JavaFX!");

        // Đặt Scene vào cửa sổ chính
        primaryStage.setScene(scene);

        // Hiển thị cửa sổ
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
        System.out.println("Group 17");

        Abstraction abstraction = new Abstraction();
        Abstraction.Pig myPigA = abstraction.new Pig();
        myPigA.animalSound();// Create a Pig object

        Polymorphism polymorphism = new Polymorphism();
        Animal myAnimal = polymorphism.new Animal(); // Create a Animal object
        Animal myPig = polymorphism.new Pig(); // Create a Pig object
        Animal myDog = polymorphism.new Dog(); // Create a Dog object
        myAnimal.animalSound();
        myPig.animalSound();
        myDog.animalSound();

        InnerClass innerClass = new InnerClass();
        InnerInterfacesExercise21.NestedClass.Call(innerClass);

        // exercise 17 - polymorphism
        Unicycle unicycle = new Unicycle();
        Bicycle bicycle = new Bicycle();
        Tricycle tricycle = new Tricycle();

        Cycle[] cycles = {unicycle, bicycle, tricycle};

        for (Cycle cycle : cycles) {
            if (cycle instanceof Unicycle) {
                ((Unicycle) cycle).balance();
            } else if (cycle instanceof Bicycle) {
                ((Bicycle) cycle).balance();
            }
        }

        // exercise 3 - interfaces
        Derived3 d = new Ex3().new Derived3();
        d.print();

        // test methods code 3 w2
        myMethod();
        myMethod();
        myMethod();

        // test class code 3 w2
        Classes c = new Classes();
        System.out.println(c.x);

        // test code 1

        // DisruptLecture.java
        System.out.println("DisruptLecture.java");
        CellPhone noiseMaker = new DisruptLecture().new CellPhone();
        ObnoxiousTune ot = new DisruptLecture().new ObnoxiousTune();
        noiseMaker.ring(ot); // ot works though Tune called for

        // TestArithmetic.java
        System.out.println("TestArithmetic.java");
        Node n = new TestArithmetic().new Plus(new TestArithmetic().new Plus(
                new TestArithmetic().new Const(1.1), new TestArithmetic().new Const(2.2)),
                new TestArithmetic().new Const(3.3));
        System.out.println("" + n.eval());

        // GlyphTest.java
        System.out.println("GlyphTest.java");
        new GlyphTest().new RoundGlyph(5);

        // Transmogrify.java
        System.out.println("Transmogrify.java");
        Transmogrify.Stage s = new Transmogrify().new Stage();
        s.go(); //happy actor
        s.change();
        s.go(); // sad actor
    }

}