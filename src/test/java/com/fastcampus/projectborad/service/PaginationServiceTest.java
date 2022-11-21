package com.fastcampus.projectborad.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("페이지 서비스 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = PaginationService.class)
class PaginationServiceTest {

    private final PaginationService paginationService;

    PaginationServiceTest(@Autowired PaginationService paginationService) {
        this.paginationService = paginationService;
    }

    @DisplayName("현재 페이지 번호와 전체 페이지 수량을 전달하면, 페이징 바 리스트를 반환")
    @MethodSource
    @ParameterizedTest
    void test01(int currentPageNumber, int totalPageLength, List<Integer> expected) {

        List<Integer> actual = paginationService.getPaginationBarNumber(currentPageNumber, totalPageLength);

        assertEquals(expected, actual);
    }

    static Stream<Arguments> test01() {
        return Stream.of(
                arguments(0, 13, List.of(0, 1, 2, 3, 4)),
                arguments(1, 13, List.of(0, 1, 2, 3, 4)),
                arguments(2, 13, List.of(0, 1, 2, 3, 4)),
                arguments(3, 13, List.of(1, 2, 3, 4, 5)),
                arguments(4, 13, List.of(2, 3, 4, 5, 6)),
                arguments(5, 13, List.of(3, 4, 5, 6, 7)),
                arguments(6, 13, List.of(4, 5, 6, 7, 8)),
                arguments(7, 13, List.of(5, 6, 7, 8, 9)),
                arguments(8, 13, List.of(6, 7, 8, 9, 10)),
                arguments(9, 13, List.of(7, 8, 9, 10, 11)),
                arguments(10, 13, List.of(8, 9, 10, 11, 12)),
                arguments(11, 13, List.of(9, 10, 11, 12)),
                arguments(12, 13, List.of(10, 11, 12))
        );
    }


    @Test
    void test02() {

        int actual = paginationService.currentBarLength();

        assertEquals(5 , actual);
    }
}