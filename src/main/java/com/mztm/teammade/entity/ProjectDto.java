package com.mztm.teammade.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProjectDto implements Serializable {
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Long id;
    private final String title;
    private final String category_;
    private final String public_;
    private final String location_;
    private final String introduction;
    private final String startdate;
    private final String enddate;
    private final MemberDto host;
    private final List<com.mztm.teammade.entity.MemberDto> members;
    private final List<FileDto> files;
    private final List<TodoDto> todos;

    @Data
    public static class MemberDto implements Serializable {
        private final Long mid;
        private final String nickname;
        private final String email;
        private final String password;
        private final String name;
        private final String address;
        private final String gender;
        private final Role role;
    }
}
