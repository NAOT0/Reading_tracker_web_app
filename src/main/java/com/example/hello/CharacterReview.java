package com.example.hello;

public class CharacterReview {
    private final String name;
    private final String review;

    public CharacterReview(String name, String review) {
        this.name = name;
        this.review = review;
    }

    // Thymeleafからアクセスするためにgetterが必要
    public String getName() { return name; }
    public String getReview() { return review; }
}