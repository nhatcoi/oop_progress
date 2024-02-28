package com.mycompany.app.week1;

import java.util.Scanner;

public class E9 {
    public void Ex9() {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            printFibonacci(n);
        }
    }

    public void printFibonacci(int n) {
        long t1 = 1, t2 = 1;
        for (int i = 1; i <= n; ++i) {
            System.out.print(t1 + ", ");
            long sum = t1 + t2;
            t1 = t2;
            t2 = sum;
        }
    }
}
