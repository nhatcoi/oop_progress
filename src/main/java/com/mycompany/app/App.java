package com.mycompany.app;

import com.mycompany.app.Debug.Debug;

import com.mycompany.app.week2.Ex.ReUse.Ex7;

public class App {
    static Debug debug = new Debug();

    public static void main(String[] args) {
        System.out.println("Group 17");

        Ex7 ex = new Ex7();
        Ex7.C test = ex.new C("123", "Hello");
    }
}