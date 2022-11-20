package com.fastcampus.projectborad.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table
@Entity
public class UserAccount extends AuditingFields {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    @Setter
    private String userId;

    @Column(nullable = false)
    @Setter
    private String userPassword;

    @Column(nullable = false)
    @Setter
    private String email;

    @Setter
    private String nickname;

    @Setter
    private String introduce;

    private UserAccount() {}

    private UserAccount(String userId, String userPassword, String email, String nickname, String introduce) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.nickname = nickname;
        this.introduce = introduce;
    }

    public static UserAccount of(String userId, String userPassword, String email, String nickname, String introduce) {
        return new UserAccount(userId, userPassword, email, nickname, introduce);
    }


}
