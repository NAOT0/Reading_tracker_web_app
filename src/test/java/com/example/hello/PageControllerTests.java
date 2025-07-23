package com.example.hello;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

// ↓↓↓↓ この4行を追加または確認してください ↓↓↓↓
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class PageControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testHelloPage() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk()) // HTTP 200 OK
                .andExpect(view().name("hello")) // view名が "hello"
                .andExpect(model().attribute("message", "こんにちは、Spring Boot！")); // modelに正しい値
    }

    @Test
    void testIndexPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk()) // HTTP 200 OK
                .andExpect(view().name("index")); // view名が "index"
    }

    @Test
    void testNextPage() throws Exception {
        mockMvc.perform(get("/next"))
                .andExpect(status().isOk()) // HTTP 200 OK
                .andExpect(view().name("nextPage")); // view名が "nextPage"
    }
}