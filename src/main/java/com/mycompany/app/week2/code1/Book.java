package com.mycompany.app.week2.code1;

public class Book {
    private String title;
    private String author;
    private int numPages;

    // constructor no parameters
    public Book() {
        title = "No title";
        author = "No author";
        numPages = 0;
    }

    // create constructor method
    public Book(String title, String author, int numPages) {
        this.title = title;
        this.author = author;
        this.numPages = numPages;
    }

    // getter method for title
    public String getTitle() {
        return title;
    }

    // setter method for title

    public void setTitle(String title) {
        this.title = title;
    }

    // getter method for author
    public String getAuthor() {
        return author;
    }

    // setter method for author
    public void setAuthor(String author) {
        this.author = author;
    }

    // getter method for numPages
    public int getNumPages() {
        return numPages;
    }

    // setter method for numPages
    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    // create a method to print out the book details
    public void printBookDetails() {
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Number of pages: " + numPages);
    }

}
