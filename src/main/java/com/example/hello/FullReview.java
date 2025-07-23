package com.example.hello;

import java.util.List;

public class FullReview {
    private final String title;
    private final String author;
    private final String overallReview;
    private final List<CharacterReview> characterReviews;

    public FullReview(String title, String author, String overallReview, List<CharacterReview> characterReviews) {
        this.title = title;
        this.author = author;
        this.overallReview = overallReview;
        this.characterReviews = characterReviews;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getOverallReview() { return overallReview; }
    public List<CharacterReview> getCharacterReviews() { return characterReviews; }
}