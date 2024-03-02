package com.mycompany.app;

import com.mycompany.app.Debug.Debug;
import com.mycompany.app.week2.Ex.CleanUp.Ex8;

public class App {
    static Debug debug = new Debug();

    public static void main(String[] args) {
        System.out.println("Group 17");

        Ex8 ex8 = new Ex8();
        ex8.method1();
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