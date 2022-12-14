package com.mztm.teammade.service;

import com.mztm.teammade.entity.Member;
import com.mztm.teammade.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    @Override
    public Member create(Member member) {
        if (member == null || member.getEmail() == null)  {
            throw new RuntimeException("Invalid args");

        }
        final String email = member.getEmail();
        if (memberRepository.existsByEmail(email)) {
            log.warn("{} already exists",email);
            throw new RuntimeException("Email already exists");
        }
        return memberRepository.save(member);
    }

    @Override
    public Member getByCredentials(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password);
    }
}
