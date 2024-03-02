package com.mycompany.app;

import com.mycompany.app.Debug.Debug;
import com.mycompany.app.week2.Ex.ReUse.Ex1;
import com.mycompany.app.week2.Ex.ReUse.Ex1.First;
import com.mycompany.app.week2.Ex.ReUse.Ex1.Second;

public class App {
    static Debug debug = new Debug();

    public static void main(String[] args) {
        System.out.println("Group 17");

        Ex1 ex = new Ex1();
        Second Sencond = ex.new Second();
        First firstInstance = ex.new Second().gIFirst();
        firstInstance.print();

        Sencond.gIFirst().print();

        // Ex12 ex = new Ex12();
        // Ex12.Tank tank = ex.new Tank();
        // tank.empty();
        // System.out.println("Is tank empty? " + tank.isEmpty());

        // tank = null;
        // System.gc();
        // FirstClass firstObj = new FirstClass();
        // //can access protected data from another class
        // secondMethod(firstObj);

        // // E5 e5 = new E5();
        // // debug.debug(e5.publicField + ""); // can call this because public
        // // e5.publicMethod();

        // // debug.debug(e5.privateField + ""); // cant call this because private
        // // e5.privateMethod();

        // // debug.debug(e5.protectedField + ""); // cant call this because protected
        // // e5.protectedField();

        // // E4.Dog dog = new E4.Dog("Fido", 50);
        // // //Debug.debug(dog.name); cant not call this because use protected
        // // debug.debug(dog.getName()); // can call this because use getter
    }

    // static void secondMethod(FirstClass obj) {
    // obj.protectedData = "Manipulated Protected Data";
    // debug.debug("Protected Data: " + obj.protectedData);
    // }
}

// class FirstClass {
// protected String protectedData;
// }