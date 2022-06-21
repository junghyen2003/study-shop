package com.shop.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

// AuditorAware 현재 감시자에 대한 구성 요소를 반환하는 인터페이스를 구현
public class AuditorAwareImpl implements AuditorAware<String> {

    // 현재 감시자(Auditor)를 반환
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // SecurityContextHolder의 Context에서 현재 인증 정보를 꺼냄

        String userId = "";
        if (authentication != null) {
            userId = authentication.getName();
            // 현재 로그인 한 사용자의 정보를 조회하여 사용자의 이름을 등록자와 수정자로 지정
        }
        return Optional.of(userId);
    }

}
