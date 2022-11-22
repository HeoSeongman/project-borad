package com.fastcampus.projectborad.controller;

import com.fastcampus.projectborad.domain.typeEnum.SearchType;
import com.fastcampus.projectborad.dto.ArticleAndCommentsDtoResponse;
import com.fastcampus.projectborad.dto.ArticleDtoResponse;
import com.fastcampus.projectborad.service.ArticleService;
import com.fastcampus.projectborad.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final PaginationService paginationService;

    @GetMapping
    public String articles(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map) {

        Page<ArticleDtoResponse> articles = articleService.searchArticles(searchType, searchValue, pageable).map(ArticleDtoResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), articles.getTotalPages());

        map.addAttribute("articles", articles);
        map.addAttribute("pageBarNumber", barNumbers);
        map.addAttribute("searchTypes", SearchType.values());

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
