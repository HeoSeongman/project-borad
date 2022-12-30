package com.fastcampus.projectborad.dto.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DTO - 카카오 인증 응답 테스트")
class KakaoOAuth2ResponseTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @DisplayName("인증 결과를 json 으로 받으면 KakaoOAuth2Response 객체로 변환")
    @Test
    void kakaoOAuth2ResponseObject() throws Exception {
        String serializedResponse = """
                {
                    "id" : 1234567890,
                    "connected_at" : "2022-12-29T00:12:34Z",
                    "properties" : {
                        "nickname" : "두리"
                    },
                    "kakao_account" : {
                        "profile_nickname_needs_agreement" : false,
                        "profile" : {
                            "nickname" : "두리"
                        },
                        "has_email" : true,
                        "email_needs_agreement" : false,
                        "is_email_valid" : true,
                        "is_email_verified" : true,
                        "email" : "cyd080706@naver.com"
                    }
                }
                """;

        Map<String, Object> attribute = mapper.readValue(serializedResponse, new TypeReference<>() {});

        KakaoOAuth2Response auth2Response = KakaoOAuth2Response.from(attribute);

        assertThat(auth2Response)
                .hasFieldOrPropertyWithValue("id", 1234567890L)
                .hasFieldOrPropertyWithValue("connectedAt",
                        ZonedDateTime.of(2022, 12, 29, 0, 12, 34, 0, ZoneOffset.UTC)
                                .withZoneSameInstant(ZoneId.systemDefault())
                                .toLocalDateTime()
                )
                .hasFieldOrPropertyWithValue("kakaoAccount.profile.nickname", "두리")
                .hasFieldOrPropertyWithValue("kakaoAccount.email", "cyd080706@naver.com");

    }
}