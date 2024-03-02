package com.mycompany.app;

import com.mycompany.app.Debug.Debug;

import com.mycompany.app.week2.Ex.ReUse.Ex2;

public class App {
    static Debug debug = new Debug();

    public static void main(String[] args) {
        System.out.println("Group 17");

        Ex2 ex2 = new Ex2();
        Ex2.newDetergent newDetergent = ex2.new newDetergent();
        newDetergent.scrub();
    }
}