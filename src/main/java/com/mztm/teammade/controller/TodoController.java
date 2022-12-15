package com.mztm.teammade.controller;

import com.mztm.teammade.dto.ResponseDTO;
import com.mztm.teammade.dto.TodoDto;
import com.mztm.teammade.entity.Project;
import com.mztm.teammade.entity.Todo;
import com.mztm.teammade.persistence.ProjectRepository;
import com.mztm.teammade.service.TodoService;
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
public class TodoController {
    private final TodoService todoService;
    private final ProjectRepository projectRepository;


    @PostMapping("/todos")
    public ResponseEntity<?> createTodo( @RequestBody TodoDto dto) {
        Project temp = projectRepository.findById(7L).get();

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

    @GetMapping("/todos")
    public ResponseEntity<?> retrieveTodoList() {
        Project temp = projectRepository.findById(7L).get();

        List<Todo> entities = todoService.retrieve(temp);
        List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());

        ResponseDTO<TodoDto> response = ResponseDTO.<TodoDto>builder().data(dtos).build();

        return ResponseEntity.ok(response);
    }

}
