package com.mycompany.app;

import com.mycompany.app.week2.Book;

public class App {
    public static void main(String[] args) {
        System.out.println("Group 17");

        Book book = new Book("The Alchemist", "Paulo Coelho", 1988);
        book.printBookDetails();

    }
}
