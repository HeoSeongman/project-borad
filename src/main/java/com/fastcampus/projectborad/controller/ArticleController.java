package com.fastcampus.projectborad.controller;

import com.fastcampus.projectborad.domain.typeEnum.SearchType;
import com.fastcampus.projectborad.dto.ArticleAndCommentsDto;
import com.fastcampus.projectborad.dto.ArticleAndCommentsDtoResponse;
import com.fastcampus.projectborad.dto.ArticleDtoResponse;
import com.fastcampus.projectborad.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public String articles(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map) {

        map.addAttribute("articles",
                articleService.searchArticles(
                        searchType, searchValue, pageable).map(ArticleDtoResponse::from));

        return "articles/index";
    }

    @GetMapping("/{articleId}")
    public String article(@PathVariable Long articleId, ModelMap map) {
        ArticleAndCommentsDtoResponse articleAndCommentsDtoResponse = ArticleAndCommentsDtoResponse.from(articleService.searchArticleAndCommentDto(articleId));
        map.addAttribute("article", articleAndCommentsDtoResponse);
        map.addAttribute("articleComments", articleAndCommentsDtoResponse.articleCommentsDtoResponse());

        return "articles/detail";
    }

}
