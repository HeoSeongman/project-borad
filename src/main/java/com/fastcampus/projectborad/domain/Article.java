package com.fastcampus.projectborad.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
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
@ToString
@Table
@Entity
public class Article extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false)
    private UserAccount userAccount;

    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Column(nullable = false, length = 10000)
    private String content;

    @Setter
    private String hashtag;

    @Setter
    private boolean isDeleted;

//    @ToString.Exclude
//    @OrderBy("id")
//    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
//    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    protected Article() {

    }

    private Article(UserAccount userAccount, String title, String content, String hashtag) {
        this.title = title;
        this.userAccount = userAccount;
        this.content = content;
        this.hashtag = hashtag;
    }
    public static Article of(String title, UserAccount userAccount, String content, String hashtag) {
        return new Article(userAccount, title, content, hashtag);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
