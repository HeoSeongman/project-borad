package com.fastcampus.projectborad.dto.request;

import com.fastcampus.projectborad.domain.UserAccount;
import com.fastcampus.projectborad.dto.UserAccountDto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link UserAccount} entity
 */
public record UserAccountRequest(
        String userId,
        String userPassword,
        String userEmail,
        String userNickname,
        String userIntroduce

) implements Serializable {

    public static UserAccountRequest of(String userId, String userPassword, String userEmail, String userNickname, String userIntroduce) {
        return new UserAccountRequest(userId, userPassword, userEmail, userNickname, userIntroduce);
    }

    public UserAccountDto toDto() {
        return UserAccountDto.of(
                userId,
                userPassword,
                userNickname,
                userEmail,
                userIntroduce
        );
    }

}