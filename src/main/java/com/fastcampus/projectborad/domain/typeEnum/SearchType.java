package com.fastcampus.projectborad.domain.typeEnum;

import lombok.Getter;

public enum SearchType {
    Title("제목"),
    Content("내용"),
    ID("아이디"),
    NickName("닉네임"),
    Hashtag("해시태그");

    @Getter
    private final String description;

    SearchType(String description) {
        this.description = description;
    }
}
