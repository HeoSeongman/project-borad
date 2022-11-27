package com.fastcampus.projectborad.service;

import com.fastcampus.projectborad.domain.UserAccount;
import com.fastcampus.projectborad.dto.UserAccountDto;
import com.fastcampus.projectborad.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    public void createAccount(UserAccountDto userAccountDto) {
        System.out.println("----------------------------------------Before UserAccount.of()----------------------------------------");

        UserAccount account = UserAccount.of(
                userAccountDto.userId(),
                "{noop}" + userAccountDto.userPassword(),
                userAccountDto.email(),
                userAccountDto.nickname(),
                userAccountDto.introduce()
        );

        System.out.println("----------------------------------------After UserAccount.of()----------------------------------------");

        userAccountRepository.save(account);

        System.out.println("----------------------------------------After save(account)----------------------------------------");
    }
}
