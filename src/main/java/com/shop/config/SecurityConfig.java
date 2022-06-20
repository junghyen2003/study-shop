package com.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
// WebSecurityConfigurerAdapter 를 상속받는 클래스에 @EnableWebSecurity 어노테이션을 선언하면 Spring Security Filter Chain이 자동으로 포함
// WebSecurityConfigureAdapter 를 상속받아서 메소드 오버라이딩을 통해 보안 설정을 커스터마이징 할 수 있음
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // http 요청에 대한 보안을 설정
    // 페이지 권한 설정, 로그인 페이지 설정, 로그아웃 메소드 등에 대한 설정을 작성
    @Override
    protected void configure(HttpSecurity http) throws Exception {

    }

    // 비밀번호를 데이터베이스에 그대로 저장했을 경우, 데이터베이스가 해킹당하면 고객의 회원 정보가 그대로 노출
    // 이를 해결하기 위해 BCryptPasswordEncoder의 해시 함수를 이용하여 비밀번호를 암호화하여 저장
    // BCryptPasswordEncoder를 빈으로 등록하여 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
