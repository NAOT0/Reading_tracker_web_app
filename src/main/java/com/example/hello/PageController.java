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
    /**
     * http://localhost:8080/hello にアクセスされた場合
     */
    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("message", "こんにちは、Spring Boot！");
        return "hello";
    }

    /**
     * http://localhost:8080/ にアクセスされた場合
     */
    @GetMapping("/")
    public String showIndex() {
        return "index";
    }

    /**
     * http://localhost:8080/next にアクセスされた場合
     */
    @GetMapping("/registration")
    public String showNextPage() {
        return "registration";
    }


    @PostMapping("/execute")
    public String execute(@RequestParam("isbn") String isbn, Model model) {
        BookInfoExtractor extractor = new BookInfoExtractor();
        BookInfo book = extractor.fetchAndPrintBookInfo(isbn);

        if (book != null) {
            // 書籍が見つかった場合、BookInfoオブジェクトをモデルに追加
            model.addAttribute("book", book);
            model.addAttribute("isbn", isbn); 
        } else {
            // 見つからなかった場合、エラーメッセージをモデルに追加
            model.addAttribute("error", "書籍が見つかりませんでした。ISBNコードを確認してください。");
        }

        return "registration";
    }

    @PostMapping("/save-review")
    public String saveReview(@RequestParam("title") String title,
                            @RequestParam("author") String author,
                             @RequestParam("overallReview") String overallReview,
                             // required=false にして、感想がなくてもエラーにならないようにする
                             @RequestParam(name = "characterNames", required = false) List<String> characterNames,
                             @RequestParam(name = "characterReviews", required = false) List<String> characterReviews,
                             Model model) {

        // 主人公の感想をまとめるリストを作成
        List<CharacterReview> characterReviewList = new ArrayList<>();
        if (characterNames != null) {
            for (int i = 0; i < characterNames.size(); i++) {
                characterReviewList.add(new CharacterReview(characterNames.get(i), characterReviews.get(i)));
            }
        }

        // すべての感想を一つのオブジェクトにまとめる
        FullReview fullReview = new FullReview(title, author, overallReview, characterReviewList);

        // 擬似データベースに保存
        reviewDatabase.add(fullReview);

        // 保存が完了したら、一覧表示ページに移動する
        return "redirect:/reviews";
    }


    @GetMapping("/reviews")
    public String showReviews(Model model) {
        // 保存されているすべての感想をモデルに追加
        model.addAttribute("reviews", reviewDatabase);
        return "reviews"; // 新しいHTMLファイル "reviews.html" を表示
    }

    
}


