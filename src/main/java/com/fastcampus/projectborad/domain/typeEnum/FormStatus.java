package com.fastcampus.projectborad.domain.typeEnum;

import lombok.Getter;

public enum FormStatus {
    CREATE("생성"),
    UPDATE("수정"),
    DELETE("삭제");

    @Getter
    private final String description;

    FormStatus(String description) {
        this.description = description;
    }
}
