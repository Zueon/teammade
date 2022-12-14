package com.mztm.teammade.controller;

import com.mztm.teammade.dto.ProjectDTO;
import com.mztm.teammade.dto.ResponseDTO;
import com.mztm.teammade.entity.Project;
import com.mztm.teammade.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;


    public ResponseEntity createPost(@RequestBody ProjectDTO dto) {
        try {
            Long tempHostId = 1l;

            Project project = (Project) ProjectDTO.toEntity(dto);

//            project.setHostId(tempHostId);

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
}
