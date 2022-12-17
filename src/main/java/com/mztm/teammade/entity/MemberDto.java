package com.mztm.teammade.entity;

import com.mztm.teammade.dto.ProjectDTO;
import com.mztm.teammade.dto.ResumeDto;
import com.mztm.teammade.dto.StudyDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MemberDto implements Serializable {
    private  Long mid;
    private  String nickname;
    private  String email;
    private  String password;
    private  String name;
    private  String address;
    private  String gender;
    private  ProjectDTO project;
    private  StudyDto study;
    private  ResumeDto resume;

    public MemberDto(Member member) {
        this.mid = member.getMid();
        this.nickname = member.getNickname();
        this.gender = member.getGender();
        this.address = member.getAddress();
        this.email = member.getEmail();
        this.name = member.getName();
        this.project = ProjectDTO.builder()
                .pid(member.getProject().getId())
                .title(member.getProject().getTitle())
                .build();
    }
}
