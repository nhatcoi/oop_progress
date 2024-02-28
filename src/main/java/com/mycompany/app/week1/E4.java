package com.mycompany.app.week1;

public class E4 {
    public void Ex4() {
        for (int i = 2; i <= 1000; i++) {
            boolean isPrime = true;
            for (int j = 2; j < i; j++) { // Check divisibility for numbers less than i
                if (i % j == 0) {
                    isPrime = false; // If i is divisible by j, it's not a prime number
                    break;
                }
            }
            if (isPrime) {
                System.out.println(i);
            }
        }
    }
}
