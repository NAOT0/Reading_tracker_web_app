package com.example.hello;

public class BookInfo {

    private final String title;
    private final String author;

    public BookInfo(String title, String author) {
        this.title = title;
        this.author = author;
    }

    // Thymeleafからアクセスするためにgetterが必要です
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}