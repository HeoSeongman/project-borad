package com.fastcampus.projectborad.dto.security;

import com.fastcampus.projectborad.domain.UserAccount;
import com.fastcampus.projectborad.dto.UserAccountDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record BoardPrincipal(
        String username,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        String email,
        String nickname,
        String introduce
) implements UserDetails {

    public static BoardPrincipal of(String username, String password, String email, String nickname, String introduce) {
        Set<RoleType> roleTypes = Set.of(RoleType.USER);

        return new BoardPrincipal(
                username,
                password,
                roleTypes.stream()
                        .map(RoleType::getName) // stream 으로 반환된 RoleType 요소들의 getName 문자열들을 stream 으로 반환
                        .map(SimpleGrantedAuthority::new) // stream 으로 반환된 string 요소들을 SimpleGrantedAuthority 로 생성 후 stream 으로 반환
                        .collect(Collectors.toUnmodifiableSet()), // stream 으로 반환된 SimpleGrantedAuthority 요소들을 수정불가한 Set 으로 반환
                email,
                nickname,
                introduce
        );
    }

    public static BoardPrincipal from(UserAccountDto userAccountDto) {
        return BoardPrincipal.of(
                userAccountDto.userId(),
                userAccountDto.userPassword(),
                userAccountDto.email(),
                userAccountDto.nickname(),
                userAccountDto.introduce()
        );
    }

    public UserAccountDto toDto() {
        return UserAccountDto.of(
                username,
                password,
                nickname,
                email,
                introduce
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public enum RoleType {
        USER("ROLE_USER");

        @Getter
        private final String name;

        RoleType(String name) {
            this.name = name;
        }
    }
}
