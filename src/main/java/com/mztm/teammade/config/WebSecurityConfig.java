package com.mztm.teammade.config;


import lombok.RequiredArgsConstructor;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        security.cors() // WebMvcConfig에서 이미 설정했으므로 기본 cors 설정.
                .and()
                .csrf()// csrf는 현재 사용하지 않으므로 disable
                .disable()
                .httpBasic()// token을 사용하므로 basic 인증 disable
                .disable()
                .sessionManagement()  // session 기반이 아님을 선언
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests() // /와 /auth/** 경로는 인증 안해도 됨.
                .antMatchers("/auth/**", "/**").permitAll()
                .anyRequest() // /와 /auth/**이외의 모든 경로는 인증 해야됨.
                .authenticated();


        return security.build();

    }

}
