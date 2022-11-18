package com.fastcampus.projectborad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequestMapping("/articles")
@Controller
public class ArticleController {

    @GetMapping
    public String articles(ModelMap map) {
        map.addAttribute("articles", List.of());
        return "articles/index";
    }

    @GetMapping("/{articleId}")
    public String article(@PathVariable Long articleId, ModelMap map) {
        map.addAttribute("article", null);
        map.addAttribute("articleComments", List.of());
        map.addAttribute("forwardValue", articleId);
        map.addAttribute("writtenDate", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        map.addAttribute("writtenTime", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));

        return "articles/detail";
    }

}
