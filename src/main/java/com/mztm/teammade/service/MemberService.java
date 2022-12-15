package com.mztm.teammade.service;

import com.mztm.teammade.entity.Member;

public interface MemberService {
    Member create(Member member);
    Member getByCredentials(String email, String password);

}
