package com.fastcampus.projectborad.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString(callSuper = true)
@Table
@Entity
public class UserAccount extends AuditingFields {

    @Id
    private String userId;

    @Column(nullable = false)
    @Setter
    private String userPassword;

    @Column(nullable = false, unique = true)
    @Setter
    private String email;

    @Setter
    private String nickname;

    @Setter
    private String introduce;

    protected UserAccount() {}

    private UserAccount(String userId, String userPassword, String email, String nickname, String introduce, String createdBy) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.nickname = nickname;
        this.introduce = introduce;
        this.createdBy = createdBy;
        this.modifiedBy = createdBy;
    }

    public static UserAccount of(String userId, String userPassword, String email, String nickname, String introduce) {
        return new UserAccount(userId, userPassword, email, nickname, introduce, null);
    }

    public static UserAccount of(String userId, String userPassword, String email, String nickname, String introduce, String createdBy) {
        return new UserAccount(userId, userPassword, email, nickname, introduce, createdBy);
    }


}
