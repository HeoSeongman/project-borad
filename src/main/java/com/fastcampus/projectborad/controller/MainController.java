package com.fastcampus.projectborad.controller;

import com.fastcampus.projectborad.domain.UserAccount;
import com.fastcampus.projectborad.dto.UserAccountDto;
import com.fastcampus.projectborad.dto.request.UserAccountRequest;
import com.fastcampus.projectborad.repository.UserAccountRepository;
import com.fastcampus.projectborad.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final UserAccountService userAccountService;

    private final UserAccountRepository userAccountRepository;

    @GetMapping("/")
    public String returnRoot() {
        return "redirect:/articles";
    }

    @GetMapping("/memberJoin")
    public String memberJoinPage() {
        return "/memberJoin";
    }

    @PostMapping("/memberJoin")
    public String memberJoining(UserAccountRequest userAccountRequest) {
        System.out.println("----------------------------------------Call memberJoining()----------------------------------------");
//        System.out.println("USER nickname : " + userAccountRequest.userEmail());
        userAccountService.createAccount(userAccountRequest.toDto());

        return "redirect:/";
    }
}
