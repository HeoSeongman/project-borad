package com.fastcampus.projectborad.config;

import com.fastcampus.projectborad.dto.security.BoardPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditiorAware() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext()) // SecurityContextHolder 에서 시큐리티가 인증한 내용이 있는 SecurityContext 를 반환
                .map(SecurityContext::getAuthentication) // SecurityContext 에서 인증객체를 반환
                .filter(Authentication::isAuthenticated) // filter 로 인증객체가 실제로 인증이 됐는지 검증
                .map(Authentication::getPrincipal) // SecurityContext 에서 Principal(본인) 객체를 반환
                .map(BoardPrincipal.class::cast) // Principal 객체를 BoardPrincipal 로 형변환
                .map(BoardPrincipal::getUsername); // BoardPrincipal 객체에서 Username 반환
    }
}
