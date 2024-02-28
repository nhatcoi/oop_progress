package com.mycompany.app.week2;

public class Recursion {
    // recursion method
    public int factorial(int n) {
        if (n == 0) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }
}
