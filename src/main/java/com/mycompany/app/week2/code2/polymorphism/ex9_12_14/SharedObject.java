package com.mycompany.app.week2.code2.polymorphism.ex9_12_14;

public class SharedObject {
    private static int referenceCount = 0;

    public SharedObject() {
        referenceCount++;
        System.out.println("Shared object is created. Reference count: " + referenceCount);
    }

    public void release() {
        referenceCount--;
        System.out.println("Shared object is released. Reference count: " + referenceCount);
        if (referenceCount == 0) {
            System.out.println("No references left. Cleaning up.");
            // Additional cleanup logic can be added here if needed
        }
    }
}
