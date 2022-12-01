package com.fastcampus.projectborad.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Entity
@ToString
@Table
public class ArticleComment extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Article article;

    @ManyToOne(optional = false)
    private UserAccount userAccount;

    @Setter
    @Column(nullable = false, length = 500)
    private String content;

    @Setter
    private boolean isDeleted;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "articleComment")
    private final Set<ReplyComment> replyComments = new LinkedHashSet<>();

    protected ArticleComment() {}

    private ArticleComment(Article article, UserAccount userAccount, String content, boolean isDeleted) {
        this.article = article;
        this.userAccount = userAccount;
        this.content = content;
        this.isDeleted = isDeleted;
    }

    public static ArticleComment of(Article article, UserAccount userAccount, String content, boolean isDeleted) {
        return new ArticleComment(article, userAccount, content, isDeleted);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment that)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
