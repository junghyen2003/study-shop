package com.shop.config;

import com.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
// WebSecurityConfigurerAdapter 를 상속받는 클래스에 @EnableWebSecurity 어노테이션을 선언하면 Spring Security Filter Chain이 자동으로 포함
// WebSecurityConfigurerAdapter 를 상속받아서 메소드 오버라이딩을 통해 보안 설정을 커스터마이징 할 수 있음
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberService memberService;

    // http 요청에 대한 보안을 설정
    // 페이지 권한 설정, 로그인 페이지 설정, 로그아웃 메소드 등에 대한 설정을 작성
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/members/login") // 로그인 페이지 URL을 설정
                .defaultSuccessUrl("/") // 로그인 성공 시 이동할 URL을 설정
                .usernameParameter("email") // 로그인 시 사용할 파라미터 이름으로 email을 지정
                .failureUrl("/members/login/error") // 로그인 실패 시 이동할 URL을 설정
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) // 로그아웃 성공 시 이동할 URL을 설정
                .logoutSuccessUrl("/"); // 로그아웃 성공 시 이동할 URL을 설정

        http.authorizeRequests() // Security 처리에 HttpServletRequest를 이용
                .mvcMatchers("/", "/members/**", "/item/**", "/images/**").permitAll() // 해당 경로에는 모든 사용자가 인증 없이 접근 가능
                .mvcMatchers("/admin/**").hasRole("ADMIN") // admin으로 시작하는 경로는 해당 계정이 ADMIN Role 일 경우에만 접근 가능하도록 설정
                .anyRequest().authenticated(); // 위 경로를 제외한 나머지 경로는 모두 인증을 요구 하도록 설정

        http.exceptionHandling() // 인증되지 않은 사용자가 리소스에 접근 하였을 때 수행되는 핸들러 등록
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());
    }

    // 비밀번호를 데이터베이스에 그대로 저장했을 경우, 데이터베이스가 해킹당하면 고객의 회원 정보가 그대로 노출
    // 이를 해결하기 위해 BCryptPasswordEncoder의 해시 함수를 이용하여 비밀번호를 암호화하여 저장
    // BCryptPasswordEncoder를 빈으로 등록하여 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Spring Security에서 인증은 AuthenticationManager를 통해 이루어지며 AuthenticationManagerBuilder가 AuthenticationManager를 생성

        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
        // userDetailService를 구현하고 있는 객체로 memberService를 지정. 비밀번호 암호화를 위해 passwordEncoder를 지정
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
        // static 디렉터리의 하위 파일은 인증을 무시하도록 설정
        // WebSecurityConfigurerAdapter 인터페이스 configure(WebSecurity webSecurity) 메소드 참고
    }
}
