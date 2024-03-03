package com.mycompany.app;

import com.mycompany.app.Debug.Debug;

import com.mycompany.app.week2.Ex.ReUse.Ex9;
import com.mycompany.app.week2.Ex.ReUse.Ex9.Stem;
import com.mycompany.app.week2.interfaces.*;
import com.mycompany.app.week2.interfaces.Ex3.*;


public class App {
    static Debug debug = new Debug();

    public static void main(String[] args) {
        System.out.println("Group 17");

        Stem stem = new Ex9().new Stem();

        // exercise 3 - interfaces
        Derived3 d = new Ex3().new Derived3();
        d.print();
    }
}