package com.mycompany.app;

import com.mycompany.app.Debug.Debug;
import com.mycompany.app.week2.Ex.E4;
public class App {
    public static void main(String[] args) {
        Debug debug= new Debug();
        System.out.println("Group 17");

            E4.Dog dog = new E4.Dog("Fido", 50);
            //Debug.debug(dog.name); cant not call this because use protected
            debug.debug(dog.getName()); // can call this because use getter
    }
}
