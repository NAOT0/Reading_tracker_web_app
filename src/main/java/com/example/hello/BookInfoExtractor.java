package com.example.hello;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;


public class BookInfoExtractor {

    private static final String NDL_SRU_ENDPOINT = "https://ndlsearch.ndl.go.jp/api/sru";

    /**
     * ISBNコードを使用して書籍の題名と著者名を取得します。
     *
     * @param isbn ISBNコード（ハイフン有無問わず）
     */
    public BookInfo fetchAndPrintBookInfo(String isbn) {
        // ハイフンを除去し、クエリを構築
        String cleanIsbn = isbn.replace("-", "");
        String cqlQuery = String.format("isbn=%s", cleanIsbn);

        try {
            // 1. APIリクエストURLの構築
            String encodedCql = URLEncoder.encode(cqlQuery, StandardCharsets.UTF_8);
            String requestUrl = String.format(
                "%s?operation=searchRetrieve&query=%s&recordPacking=xml&recordSchema=dc",
                NDL_SRU_ENDPOINT, encodedCql
            );

            // 2. APIへのリクエスト送信とレスポンス受信
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                   .uri(URI.create(requestUrl))
                   .build();

            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            // 3. レスポンスのXMLをパース
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse((response.body()));
            XPath xpath = XPathFactory.newInstance().newXPath();   
            NodeList recordNode = (NodeList) xpath.evaluate("//record", doc, XPathConstants.NODE);
            if (recordNode != null) {
                //trim()メソッドを使用して、余分な空白を削除
                String title = xpath.evaluate(".//*[local-name()='title']/text()", recordNode).trim();
                String author = xpath.evaluate(".//*[local-name()='creator']/text()", recordNode).trim();

                // BookInfoオブジェクトを生成して返す
                return new BookInfo(title, author);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    

}