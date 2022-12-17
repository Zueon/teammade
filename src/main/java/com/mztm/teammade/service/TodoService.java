package com.mztm.teammade.service;

import com.mztm.teammade.entity.Project;
import com.mztm.teammade.entity.Todo;

import java.util.List;

public interface TodoService {
    List<Todo> create(final Todo todo);
    List<Todo> retrieve(final Project project);
    List<Todo> update(final Todo todo);
    List<Todo> delete(final Todo todo);
}
