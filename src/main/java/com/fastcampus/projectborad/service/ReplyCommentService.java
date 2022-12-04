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
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class ReplyCommentService {

    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;
    private final ReplyCommentRepository replyCommentRepository;

    public ReplyCommentDto saveReplyComment(ReplyCommentDto replyCommentDto) {

        ArticleComment articleComment = articleCommentRepository.getReferenceById(replyCommentDto.articleCommentId());
        UserAccount userAccount = userAccountRepository.getReferenceById(replyCommentDto.userAccount().userId());

        ReplyComment replyComment = replyCommentRepository.save(replyCommentDto.toEntity(articleComment, userAccount));

        return ReplyCommentDto.from(replyComment);
    }

    public void updateReplyComment(Long replyId ,ReplyCommentDto replyCommentDto) {
        ReplyComment replyComment = replyCommentRepository.getReferenceById(replyId);

        if (replyCommentDto.content() != null) {
            replyComment.setContent(replyCommentDto.content());
        }
    }

    public void deleteReplyComment(Long replyCommentId) {

        ReplyComment replyComment = replyCommentRepository.findById(replyCommentId).orElseThrow();
        replyComment.setDeleted(true);

        replyCommentRepository.save(replyComment);
    }
}
