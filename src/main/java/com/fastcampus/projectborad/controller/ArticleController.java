package com.fastcampus.projectborad.controller;

import com.fastcampus.projectborad.domain.typeEnum.FormStatus;
import com.fastcampus.projectborad.domain.typeEnum.SearchType;
import com.fastcampus.projectborad.dto.request.ArticleAndCommentsResponse;
import com.fastcampus.projectborad.dto.ArticleDto;
import com.fastcampus.projectborad.dto.ArticleDtoResponse;
import com.fastcampus.projectborad.dto.request.ArticleRequest;
import com.fastcampus.projectborad.dto.request.ArticleResponse;
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
        ArticleAndCommentsResponse articleAndCommentsResponse = ArticleAndCommentsResponse.from(articleService.searchArticleAndCommentDto(articleId));
        map.addAttribute("article", articleAndCommentsResponse);
        map.addAttribute("articleComments", articleAndCommentsResponse.articleCommentsDtoResponse());

        return "articles/detail";
    }

    @GetMapping("/form")
    public String createArticleForm(ModelMap map) {
        map.addAttribute("formStatus", FormStatus.CREATE);

        return "articles/form";
    }

    @GetMapping("/{articleId}/form")
    public String updateArticleForm(ModelMap map, @PathVariable Long articleId, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
        ArticleDto articleDto = articleService.searchArticle(articleId);
        System.out.println(articleDto.userAccountDto().userId());
        System.out.println(boardPrincipal.getUsername());

        if (!articleDto.userAccountDto().userId().equals(boardPrincipal.getUsername())) {
            return "redirect:/articles/" + articleId;
        }

        ArticleResponse articleResponse = ArticleResponse.from(articleDto);

        map.addAttribute("article", articleResponse);
        map.addAttribute("formStatus", FormStatus.UPDATE);

        return "articles/form";
    }

    @PostMapping("/form")
    public String createArticle(ArticleRequest articleRequest, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {

        return "redirect:/articles/" + articleService.saveArticle(articleRequest.toDto(boardPrincipal.toDto()));
    }

    @PostMapping("/{articleId}/form")
    public String updateArticle(@PathVariable Long articleId, ArticleRequest articleRequest, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
        articleService.updateArticle(articleId, articleRequest.toDto(boardPrincipal.toDto()));

        return "redirect:/articles/" + articleId;
    }

    @PostMapping("/{articleId}/delete")
    public String deleteArticle(@PathVariable Long articleId, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
        articleService.deleteArticle(articleId, boardPrincipal.getUsername());
        System.out.println("delete 호출됨 : " + articleId);

        return "redirect:/articles";
    }

}
