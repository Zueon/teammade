package com.mztm.teammade.service;

import com.mztm.teammade.dto.MemberSignupReqDto;
import com.mztm.teammade.dto.TokenInfo;
import com.mztm.teammade.entity.Member;
import com.mztm.teammade.persistence.MemberRepository;
import com.mztm.teammade.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public TokenInfo login(String email, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }

    @Override
    @Transactional
    public Member signup(MemberSignupReqDto req) {
        req.setPassword(passwordEncoder.encode(req.getPassword()));
        if (memberRepository.findByEmail(req.getEmail()).orElse(null)==null){
            return memberRepository.save(req.toEntity());
        }
        return memberRepository.save(req.toEntity());
    }

    @Override
    public Member getMemberByEmail(String email) {

        return memberRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다!"));
    }
}
