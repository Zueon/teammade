package com.mztm.teammade.service;

import com.mztm.teammade.entity.Project;
import com.mztm.teammade.entity.Todo;
import com.mztm.teammade.persistence.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{
    private final TodoRepository todoRepository;

    private void validate(final Todo entity) {
        if (entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }

        if (entity.getProject() == null) {
            log.warn("Unknown Project.");
            throw new RuntimeException("Unknown Project.");
        }
    }

    @Override
    public List<Todo> create(Todo todo) {
        validate(todo);
        Todo saved = todoRepository.save(todo);
        log.info("Entity ID : {} is saved", saved.getTid());


        return todoRepository.findByProject(saved.getProject());
    }

    @Override
    public List<Todo> retrieve(Project project) {
        return todoRepository.findByProject(project);
    }
}
