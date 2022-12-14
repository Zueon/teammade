package com.mztm.teammade.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class MemberDto implements Serializable {
    private final Long mid;
    private final String nickname;
    private final String email;
    private final String password;
    private final String name;
    private final String address;
    private final String gender;
    private final String role;
    private final String token;
}
