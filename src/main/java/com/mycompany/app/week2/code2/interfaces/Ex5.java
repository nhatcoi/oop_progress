package com.mycompany.app.week2.code2.interfaces;

import com.mycompany.app.week2.code2.interfaces.interfaces.Car;

public class Ex5 implements Car {
    @Override
    public void wheels() {
        System.out.println("wheels: 4.png");
    }

    @Override
    public void mirror() {
        System.out.println("mirror: Transparent");
    }

    @Override
    public void depot() {
        System.out.println("depot: scream");
    }
}
