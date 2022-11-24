package com.fastcampus.projectborad.controller;

import com.fastcampus.projectborad.domain.typeEnum.FormStatus;
import com.fastcampus.projectborad.domain.typeEnum.SearchType;
import com.fastcampus.projectborad.dto.ArticleAndCommentsDtoResponse;
import com.fastcampus.projectborad.dto.ArticleDtoResponse;
import com.fastcampus.projectborad.dto.UserAccountDto;
import com.fastcampus.projectborad.dto.request.ArticleCommentRequest;
import com.fastcampus.projectborad.dto.request.ArticleRequest;
import com.fastcampus.projectborad.dto.security.BoardPrincipal;
import com.fastcampus.projectborad.service.ArticleCommentService;
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
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @PostMapping("/create")
    public String createArticle(ArticleCommentRequest articleCommentRequest, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {

        articleCommentService.saveArticleComment(articleCommentRequest.toDto(boardPrincipal.toDto()));

        return "redirect:/articles/" + articleCommentRequest.articleId();
    }

    @PostMapping("/{commentId}/delete")
    public String deleteArticleComment(@PathVariable Long commentId, Long articleId, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
        articleCommentService.deleteArticleComment(commentId, boardPrincipal.getUsername());

        return "redirect:/articles/" + articleId;
    }

}
