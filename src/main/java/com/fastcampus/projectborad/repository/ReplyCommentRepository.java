package com.fastcampus.projectborad.repository;

import com.fastcampus.projectborad.domain.ReplyComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyCommentRepository extends JpaRepository<ReplyComment, Long> {
}
