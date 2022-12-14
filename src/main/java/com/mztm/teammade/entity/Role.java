package com.mztm.teammade.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    ROLE_ADMIN,
    ROLE_HOST,
    ROLE_USER;
//
//    @JsonCreator
//    public static Role from(String s) {
//        return Role.valueOf(s.toUpperCase());
//    }
}
