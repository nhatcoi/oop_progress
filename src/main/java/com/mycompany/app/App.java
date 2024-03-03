package com.mycompany.app;

import com.mycompany.app.Debug.Debug;

import com.mycompany.app.week2.Ex.ReUse.Ex9;
import com.mycompany.app.week2.Ex.ReUse.Ex9.Stem;
import com.mycompany.app.week2.interfaces.Ex3;
import com.mycompany.app.week2.interfaces.Ex3.*;
import com.mycompany.app.week2.interfaces.Ex4.*;
import com.mycompany.app.week2.interfaces.Ex4;
import com.mycompany.app.week2.interfaces.Ex5;


public class App {
    static Debug debug = new Debug();

    static void callMethod(Base4 b) {
        b.method();
    }
    public static void main(String[] args) {
        System.out.println("Group 17");

        Stem stem = new Ex9().new Stem();

        // exercise 3
        Derived3 d = new Ex3().new Derived3();
        d.print();

        // exercise 4
        Derived4 derived4 = new Ex4().new Derived4();
        callMethod(derived4);

        // exercise 5
        Ex5 car = new Ex5();
        car.mirror();
        car.depot();
        car.wheels();



    }

}