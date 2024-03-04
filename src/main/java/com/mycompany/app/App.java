package com.mycompany.app;

import com.mycompany.app.Debug.Debug;

import com.mycompany.app.week2.code2.reuse.Ex9;
import com.mycompany.app.week2.code2.reuse.Ex9.Stem;
import com.mycompany.app.week2.code2.interfaces.Ex3;
import com.mycompany.app.week2.code2.interfaces.Ex3.*;
import com.mycompany.app.week2.code3.Classes;
import static com.mycompany.app.week2.code3.Methods.myMethod;


public class App {
    static Debug debug = new Debug();

    public static void main(String[] args) {
        System.out.println("Group 17");

        Stem stem = new Ex9().new Stem();

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
    }
}