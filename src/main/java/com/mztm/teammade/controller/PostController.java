package com.mztm.teammade.controller;

import com.mztm.teammade.dto.ProjectDTO;
import com.mztm.teammade.dto.ResponseDTO;
import com.mztm.teammade.dto.StudyDto;
import com.mztm.teammade.entity.Project;
import com.mztm.teammade.entity.Study;
import com.mztm.teammade.persistence.MemberRepository;
import com.mztm.teammade.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final MemberRepository memberRepository;

    @GetMapping("/project")
    public ResponseEntity<?> getProjects(){
        List<Project> projectEntities =  postService.getProjectList();
        List<ProjectDTO> projectList = projectEntities.stream().map(ProjectDTO::new).collect(Collectors.toList());

        ResponseDTO<ProjectDTO> response = ResponseDTO.<ProjectDTO>builder().data(projectList).build();

        return ResponseEntity.ok().body(response);
    }


    @PostMapping("project/create")
    public ResponseEntity createPost(@RequestBody ProjectDTO dto) {
        try {
            Long tempHostId = 4l;

            Project project = new Project(dto);
            project.addHost(memberRepository.findById(tempHostId).get());

            Project createProject = postService.createProject(project);
            ProjectDTO createProjectDTO = new ProjectDTO(createProject);

            ResponseDTO<ProjectDTO> response = ResponseDTO.<ProjectDTO>builder().data(createProjectDTO).build();
            return ResponseEntity.ok().body(response);

        } catch (Exception e){
            String err = e.getMessage();
            ResponseDTO<ProjectDTO> response = ResponseDTO.<ProjectDTO>builder().error(err).build();

            return ResponseEntity.badRequest().body(response);
        }
    }


    @GetMapping("/study")
    public ResponseEntity<?> getStudies(){
        List<Study> studyEntities =  postService.getStudyList();
        List<StudyDto> studyList = studyEntities.stream().map(StudyDto::new).collect(Collectors.toList());

        ResponseDTO<StudyDto> response = ResponseDTO.<StudyDto>builder().data(studyList).build();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("study/create")
    public ResponseEntity createStudy(@RequestBody StudyDto dto) {
        try {
            Long tempHostId = 22l;

            Study study = new Study(dto);


            study.addHost(memberRepository.findById(tempHostId).get());

            Study createStudy = postService.createStudy(study);
            StudyDto createStudyDto = new StudyDto(createStudy);

            ResponseDTO<StudyDto> response = ResponseDTO.<StudyDto>builder().data(createStudyDto).build();
            return ResponseEntity.ok().body(response);

        } catch (Exception e){
            String err = e.getMessage();
            ResponseDTO<StudyDto> response = ResponseDTO.<StudyDto>builder().error(err).build();

            return ResponseEntity.badRequest().body(response);
        }
    }
}
