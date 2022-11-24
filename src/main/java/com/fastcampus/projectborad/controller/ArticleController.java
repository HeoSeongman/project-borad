package com.fastcampus.projectborad.controller;

import com.fastcampus.projectborad.domain.typeEnum.FormStatus;
import com.fastcampus.projectborad.domain.typeEnum.SearchType;
import com.fastcampus.projectborad.dto.ArticleAndCommentsDtoResponse;
import com.fastcampus.projectborad.dto.ArticleDtoResponse;
import com.fastcampus.projectborad.dto.UserAccountDto;
import com.fastcampus.projectborad.dto.request.ArticleRequest;
import com.fastcampus.projectborad.dto.security.BoardPrincipal;
import com.fastcampus.projectborad.service.ArticleService;
import com.fastcampus.projectborad.service.PaginationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
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

    @GetMapping("/form")
    public String formArticle(ModelMap map) {
        map.addAttribute("formStatus", FormStatus.CREATE);

        return "articles/form";
    }

    @PostMapping("/form")
    public String createArticle(ArticleRequest articleRequest) {
//        articleService.saveArticle(articleRequest.toDto(
//                UserAccountDto.of(1L, "tjdaks0804", "TJDaks!@06", "Duri", "tjdaks0804@naver.com", "Hi, I''m Duri.")));

        return "redirect:/articles";
    }

    @PostMapping("/{articleId}/form")
    public String createArticle(@PathVariable Long articleId, ArticleRequest articleRequest, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
        articleService.saveArticle(articleRequest.toDto(boardPrincipal.toDto()));

        return "redirect:/articles";
    }

    @PostMapping("/{articleId}/delete")
    public String deleteArticle(@PathVariable Long articleId, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
        articleService.deleteArticle(articleId, boardPrincipal.getUsername());
        System.out.println("delete 호출됨 : " + articleId);

        return "redirect:/articles";
    }

}
