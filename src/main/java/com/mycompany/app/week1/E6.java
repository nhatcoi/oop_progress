package com.mycompany.app.week1;

public class E6 {
    static int test(int testval, int begin, int end) {
        if (testval >= begin && testval <= end)
            return 0;
        else if (testval < begin)
            return -1;
        else
            return +1; // Match
    }

    public void Ex6() {
        System.out.println(test(10, 5, 15));
        System.out.println(test(5, 10, 15));
        System.out.println(test(5, 5, 5));
    }
}
