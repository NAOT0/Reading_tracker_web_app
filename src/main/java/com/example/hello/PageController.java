package com.example.hello;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class PageController {

    private static final List<FullReview> reviewDatabase = new ArrayList<>();

    public String hello(Model model) {
        model.addAttribute("message", "こんにちは、Spring Boot！");
        return "hello";
    }


    @GetMapping("/")
    public String showIndex() {
        return "index";
    }


    @GetMapping("/registration")
    public String showNextPage() {
        return "registration";
    }


    @PostMapping("/execute")
    public String execute(@RequestParam("isbn") String isbn, Model model) {
        BookInfoExtractor extractor = new BookInfoExtractor();
        BookInfo book = extractor.fetchAndPrintBookInfo(isbn);

        if (book != null) {
            model.addAttribute("book", book);
            model.addAttribute("isbn", isbn); 
        } else {

            model.addAttribute("error", "書籍が見つかりませんでした。ISBNコードを確認してください。");
        }

        return "registration";
    }

    @PostMapping("/save-review")
    public String saveReview(@RequestParam("title") String title,
                            @RequestParam("author") String author,
                             @RequestParam("overallReview") String overallReview,
                             @RequestParam(name = "characterNames", required = false) List<String> characterNames,
                             @RequestParam(name = "characterReviews", required = false) List<String> characterReviews,
                             Model model) {

        
        List<CharacterReview> characterReviewList = new ArrayList<>();
        if (characterNames != null) {
            for (int i = 0; i < characterNames.size(); i++) {
                characterReviewList.add(new CharacterReview(characterNames.get(i), characterReviews.get(i)));
            }
        }

        FullReview fullReview = new FullReview(title, author, overallReview, characterReviewList);


        reviewDatabase.add(fullReview);


        return "redirect:/reviews";
    }


    @GetMapping("/reviews")
    public String showReviews(Model model) {

        model.addAttribute("reviews", reviewDatabase);
        return "reviews"; 
    }

    
}


