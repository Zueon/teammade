package com.mztm.teammade.service;

import com.mztm.teammade.dto.MemberSignupReqDto;
import com.mztm.teammade.dto.TokenInfo;
import com.mztm.teammade.entity.Member;
import com.mztm.teammade.entity.MemberDto;

public interface MemberService {
    Member create(Member member);
    Member getByCredentials(String email, String password);
    TokenInfo login(String email, String password);
    Member signup(MemberSignupReqDto req);

}
