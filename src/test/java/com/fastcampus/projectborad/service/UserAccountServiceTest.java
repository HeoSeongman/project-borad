package com.fastcampus.projectborad.service;

import com.fastcampus.projectborad.config.SecurityConfig;
import com.fastcampus.projectborad.domain.UserAccount;
import com.fastcampus.projectborad.dto.UserAccountDto;
import com.fastcampus.projectborad.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.when;

@DisplayName("회원 비즈니스 로직")
@ExtendWith(MockitoExtension.class)
class UserAccountServiceTest {

    @Mock
    private UserAccountRepository userAccountRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserAccountService userAccountService;

    @DisplayName("존재하는 ID 을 검색하고 Optional 로 반환한다.")
    @Test
    void searchUserAndReturnOptionalUser() {
        String username = "tjdaks0804";

        given(userAccountRepository.findById(username)).willReturn(Optional.of(createUserAccount(username, null)));

        Optional<UserAccountDto> userAccountDto = userAccountService.searchUser(username);

        assertThat(userAccountDto).isPresent();
        then(userAccountRepository).should().findById(username);
    }

    @DisplayName("존재하지 않는 ID 를 검색하면 비어있는 Optional 를 반환한다.")
    @Test
    void searchUserAndReturnOptionalEmpty() {
        String username = "tjdaks0804";

        given(userAccountRepository.findById(username)).willReturn(Optional.empty());

        Optional<UserAccountDto> userAccountDto = userAccountService.searchUser(username);

        assertThat(userAccountDto).isEmpty();
        then(userAccountRepository).should().findById(username);
    }

    @DisplayName("회원 정보를 저장하고 반환")
    @Test
    void saveUserAndReturnUser() {
        UserAccount paramUser = createUserAccount("Heo", null);
        UserAccount savedUser = createUserAccount("Heo", "Heo");

        given(userAccountRepository.save(any(UserAccount.class))).willReturn(savedUser);

        UserAccountDto savedUserAccountDto = userAccountService.createAccount(
                UserAccountDto.of(
                        paramUser.getUserId(),
                        paramUser.getUserPassword(),
                        paramUser.getNickname(),
                        paramUser.getEmail(),
                        paramUser.getIntroduce()
                )
        );


        assertThat(savedUserAccountDto)
                .hasFieldOrPropertyWithValue("userId", paramUser.getUserId())
                .hasFieldOrPropertyWithValue("userPassword", paramUser.getUserPassword())
                .hasFieldOrPropertyWithValue("email", paramUser.getEmail())
                .hasFieldOrPropertyWithValue("nickname", paramUser.getNickname())
                .hasFieldOrPropertyWithValue("introduce", paramUser.getIntroduce())
                .hasFieldOrPropertyWithValue("createdBy", paramUser.getUserId())
                .hasFieldOrPropertyWithValue("modifiedBy", paramUser.getUserId());

        then(userAccountRepository).should().save(any());
    }

    private UserAccount createUserAccount(String username, String createdBy) {
        return UserAccount.of(
                username,
                "Password",
                "tjdaks0804@naver.com",
                "nickname",
                "i'm tjdaks.",
                createdBy
        );
    }
}