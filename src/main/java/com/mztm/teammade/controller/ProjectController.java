package com.mztm.teammade.controller;

import com.mztm.teammade.dto.FileDto;
import com.mztm.teammade.dto.ProjectDTO;
import com.mztm.teammade.dto.ResponseDTO;
import com.mztm.teammade.dto.TodoDto;
import com.mztm.teammade.entity.Member;
import com.mztm.teammade.entity.Project;
import com.mztm.teammade.entity.Todo;
import com.mztm.teammade.persistence.ProjectRepository;
import com.mztm.teammade.service.FileStorageService;
import com.mztm.teammade.service.MemberService;
import com.mztm.teammade.service.PostService;
import com.mztm.teammade.service.TodoService;
import com.mztm.teammade.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {

    private final PostService postService;
    private final MemberService memberService;
    private final FileStorageService storageService;
    private final TodoService todoService;
    private final ProjectRepository projectRepository;


    @GetMapping
    public ResponseEntity<?> getProjects() {
        List<Project> projectEntities = postService.getProjectList();
        List<ProjectDTO> projectList = projectEntities.stream().map(ProjectDTO::new).collect(Collectors.toList());

        ResponseDTO<ProjectDTO> response = ResponseDTO.<ProjectDTO>builder().data(projectList).build();

        return ResponseEntity.ok().body(response);
    }


    @PostMapping("/create")
    public ResponseEntity createPost(@RequestBody ProjectDTO dto) {
        String email = SecurityUtil.getCurrentMemberEmail();
        Member member = memberService.getMemberByEmail(email);
        try {

            Project project = new Project(dto);
            project.addHost(member);

            Project createProject = postService.createProject(project);
            ProjectDTO createProjectDTO = new ProjectDTO(createProject);

            ResponseDTO<ProjectDTO> response = ResponseDTO.<ProjectDTO>builder().data(createProjectDTO).build();
            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            String err = e.getMessage();
            ResponseDTO<ProjectDTO> response = ResponseDTO.<ProjectDTO>builder().error(err).build();

            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{pid}/files/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("pid") Long pid) {
        String message = "";

        try {
            log.info("uploadFile");
            storageService.store(file, pid);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder().message(message).build());
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseDTO.builder().message(message).build());
        }
    }


    @GetMapping("/{pid}/files")
    public ResponseEntity<List<FileDto>> getListDBFiles(@PathVariable("pid") Long pid) {
        List<FileDto> files = storageService.getAllFiles(pid).map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId())
                    .toUriString();

            return new FileDto(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }


    @PostMapping("/{pid}/todos")
    public ResponseEntity<?> createTodo(@RequestBody TodoDto dto, @PathVariable Long pid) {
        Project temp = projectRepository.findById(pid).get();

        try {

            Todo entity = TodoDto.toEntity(dto);
            temp.addTodo(entity);

            List<Todo> entities = todoService.create(entity);

            List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());

            ResponseDTO<TodoDto> response = ResponseDTO.<TodoDto>builder().data(dtos).build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            String err = e.getMessage();
            ResponseDTO<TodoDto> response = ResponseDTO.<TodoDto>builder().error(err).build();

            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{pid}/todos")
    public ResponseEntity<?> retrieveTodoList(@PathVariable Long pid) {
        Project temp = projectRepository.findById(pid).get();

        List<Todo> entities = todoService.retrieve(temp);
        List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());

        ResponseDTO<TodoDto> response = ResponseDTO.<TodoDto>builder().data(dtos).build();

        return ResponseEntity.ok(response);
    }

}
