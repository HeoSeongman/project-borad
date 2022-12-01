package com.fastcampus.projectborad.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Entity
@Table
@ToString
public class ReplyComment extends AuditingFields {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(optional = false)
    private ArticleComment articleComment;

    @ManyToOne(optional = false)
    private UserAccount userAccount;

    @Setter
    @Column(nullable = false, length = 500)
    private String content;

    @Setter
    private boolean isDeleted = false;

    protected ReplyComment() {}

    private ReplyComment(ArticleComment articleComment, UserAccount userAccount, String content) {
        this.articleComment = articleComment;
        this.userAccount = userAccount;
        this.content = content;
    }

    public static ReplyComment of(ArticleComment articleComment, UserAccount userAccount, String content) {
        return new ReplyComment(articleComment, userAccount, content);
    }

}
