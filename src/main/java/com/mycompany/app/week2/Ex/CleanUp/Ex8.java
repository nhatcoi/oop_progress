package com.mycompany.app.week2.Ex.CleanUp;

import com.mycompany.app.Debug.Debug;

public class Ex8 {
    Debug debug = new Debug();

    public void method1() {
        debug.debug("method1");

        method2();

        this.method2();
    }

    public void method2() {
        debug.debug("method2");
    }
}
