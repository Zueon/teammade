package com.mztm.teammade.service;

import com.mztm.teammade.dto.MemberSignupReqDto;
import com.mztm.teammade.dto.TokenInfo;
import com.mztm.teammade.entity.Member;

public interface MemberService {

    TokenInfo login(String email, String password);
    Member signup(MemberSignupReqDto req);
    Member getMemberByEmail(String email);

}
