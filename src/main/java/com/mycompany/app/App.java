package com.mycompany.app;

import com.mycompany.app.Debug.Debug;
import com.mycompany.app.Debug.DebugOff;
import com.mycompany.app.week1.E1;
import com.mycompany.app.week2.*;

public class App {
    public static void main(String[] args) {
        System.out.println("Group 17");
        // call or revoke the work of each task in here
        // for example, revoke class E1
        // E1 e = new E1();
        // e.Ex1();

        // NameNumber [] nn = new NameNumber[2];
        // //To do: bug Name Number
        // //set new value
        // nn[0] = new NameNumber("John Doe", "0919191919");
        // nn[1] = new NameNumber("John Doe", "919");
        // String lastName = nn[0].getLastName();
        // String number = nn[0].getTelNumber();

        // NNCollection nnC = new NNCollection();
        // nnC.insert(nn[0]);
        // nnC.insert(nn[1]);
        // String numFind = nnC.findNumber(lastName);
        Debug d = new DebugOff();
        d.debug("test123");

        Debug d2 = new Debug();
        d2.debug("test123");
    }
}
