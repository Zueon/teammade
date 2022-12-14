package com.mztm.teammade.dto;

import com.mztm.teammade.entity.Post;
import com.mztm.teammade.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    private Long pid; // 프로젝트 방 번호
    private String title; // 프로젝트 방 제목
    private String category_; // 프로젝트 방 카테고리
    private String public_; // 프로젝트 방 공개여부
    private String location_; // 위치
    private String introduction; // 프로젝트 방 소개글
    private String startdate; // 프로젝트 시작 날짜
    private String enddate; // 프로젝트 종료 날짜

    private Long hostId;

    public ProjectDTO(Project project) {

        this.pid = project.getId();
        this.title = project.getTitle();
        this.category_ = project.getCategory_();
        this.public_ = project.getPublic_();
        this.location_ = project.getLocation_();
        this.introduction = project.getIntroduction();
        this.startdate = project.getStartdate();
        this.enddate = project.getEnddate();


    }

    public static Post toEntity(final ProjectDTO dto) {
        return Project.builder()
                .id(dto.getPid())
                .title(dto.getTitle())
                .category_(dto.getCategory_())
                .enddate(dto.getEnddate())
                .introduction(dto.getIntroduction())
                .location_(dto.getLocation_())
                .public_(dto.getPublic_())
                .startdate(dto.getStartdate())
                .build();
    }
}
