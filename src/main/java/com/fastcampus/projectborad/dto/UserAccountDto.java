package com.fastcampus.projectborad.dto;

import com.fastcampus.projectborad.domain.UserAccount;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.fastcampus.projectborad.domain.UserAccount} entity
 */
public record UserAccountDto(
        String userId,
        String userPassword,
        String nickname,
        String email,
        String introduce,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy

) implements Serializable {

    public static UserAccountDto of(String userId, String userPassword, String nickname, String email, String introduce, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new UserAccountDto(userId, userPassword, nickname, email, introduce, createdAt, createdBy, modifiedAt, modifiedBy);
    }
    public static UserAccountDto of(String userId, String userPassword, String nickname, String email, String introduce) {
        return new UserAccountDto(userId, userPassword, nickname, email, introduce, null, null, null, null);
    }

    public static UserAccountDto from(UserAccount userAccount) {
        return new UserAccountDto(
                userAccount.getUserId(),
                userAccount.getUserPassword(),
                userAccount.getNickname(),
                userAccount.getEmail(),
                userAccount.getIntroduce(),
                userAccount.getCreatedAt(),
                userAccount.getCreatedBy(),
                userAccount.getModifiedAt(),
                userAccount.getModifiedBy()
        );
    }


    public UserAccount toEntity() {
        return UserAccount.of(
                userId,
                userPassword,
                email,
                nickname,
                introduce
        );
    }

}