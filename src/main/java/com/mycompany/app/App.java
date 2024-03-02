package com.mycompany.app;

import com.mycompany.app.Debug.Debug;

import com.mycompany.app.week2.Ex.ReUse.Ex5;

public class App {
    static Debug debug = new Debug();

    public static void main(String[] args) {
        System.out.println("Group 17");

        Ex5 ex = new Ex5();
        Ex5.C test = ex.new C();
    }
}