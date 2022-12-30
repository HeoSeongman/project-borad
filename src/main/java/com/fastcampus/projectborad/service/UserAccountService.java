package com.fastcampus.projectborad.service;

import com.fastcampus.projectborad.domain.UserAccount;
import com.fastcampus.projectborad.dto.UserAccountDto;
import com.fastcampus.projectborad.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final PasswordEncoder passwordEncoder;

    private final UserAccountRepository userAccountRepository;

    public UserAccountDto createAccount(UserAccountDto userAccountDto) {
        UserAccount account = UserAccount.of(
                userAccountDto.userId(),
                passwordEncoder.encode(userAccountDto.userPassword()),
                userAccountDto.email(),
                userAccountDto.nickname(),
                userAccountDto.introduce(),
                userAccountDto.userId()
        );
        UserAccount savedUserAccount = userAccountRepository.save(account);
        return UserAccountDto.from(savedUserAccount);
    }

    @Transactional(readOnly = true)
    public Optional<UserAccountDto> searchUser(String username) {
        return userAccountRepository.findById(username)
                .map(UserAccountDto::from);
    }

}
