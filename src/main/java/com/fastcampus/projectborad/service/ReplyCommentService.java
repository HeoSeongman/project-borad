package com.fastcampus.projectborad.service;

import com.fastcampus.projectborad.domain.ArticleComment;
import com.fastcampus.projectborad.domain.ReplyComment;
import com.fastcampus.projectborad.domain.UserAccount;
import com.fastcampus.projectborad.dto.ReplyCommentDto;
import com.fastcampus.projectborad.repository.ArticleCommentRepository;
import com.fastcampus.projectborad.repository.ReplyCommentRepository;
import com.fastcampus.projectborad.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReplyCommentService {

    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;
    private final ReplyCommentRepository replyCommentRepository;

    public void saveReplyComment(ReplyCommentDto replyCommentDto) {

        ArticleComment articleComment = articleCommentRepository.getReferenceById(replyCommentDto.articleCommentId());
        UserAccount userAccount = userAccountRepository.getReferenceById(replyCommentDto.userAccount().userId());

        ReplyComment replyComment = replyCommentDto.toEntity(articleComment, userAccount);

        replyCommentRepository.save(replyComment);
    }

    public void deleteReplyComment(Long replyCommentId) {

        ReplyComment replyComment = replyCommentRepository.findById(replyCommentId).orElseThrow();
        replyComment.setDeleted(true);

        replyCommentRepository.save(replyComment);
    }
}
