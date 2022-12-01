package com.fastcampus.projectborad.controller;

import com.fastcampus.projectborad.dto.request.ArticleCommentRequest;
import com.fastcampus.projectborad.dto.request.ReplyCommentRequest;
import com.fastcampus.projectborad.dto.security.BoardPrincipal;
import com.fastcampus.projectborad.service.ArticleCommentService;
import com.fastcampus.projectborad.service.ReplyCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;
    private final ReplyCommentService replyCommentService;

    @PostMapping("/create")
    public String createArticle(ArticleCommentRequest articleCommentRequest, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {

        articleCommentService.saveArticleComment(articleCommentRequest.toDto(boardPrincipal.toDto()));

        return "redirect:/articles/" + articleCommentRequest.articleId();
    }

    @PostMapping("/{commentId}/delete")
    public String deleteArticleComment(@PathVariable Long commentId, Long articleId, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {

        articleCommentService.deleteArticleComment(commentId);

        return "redirect:/articles/" + articleId;
    }

    @PostMapping("/replyCreate")
    public String createReplyComment(ReplyCommentRequest replyCommentRequest, @AuthenticationPrincipal BoardPrincipal boardPrincipal) {

        replyCommentService.saveReplyComment(replyCommentRequest.toDto(boardPrincipal.toDto()));

        return "redirect:/articles/" + replyCommentRequest.articleId();
    }

    @PostMapping("/replyDelete")
    public String deleteReplyComment(Long replyCommentId, Long articleId) {
        System.out.println("replyCommentId : " + replyCommentId);

//        replyCommentService.deleteReplyComment(replyCommentId);

        return "redirect:/articles/" + articleId;
    }

}
