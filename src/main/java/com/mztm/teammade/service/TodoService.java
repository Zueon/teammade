package com.mztm.teammade.service;

import com.mztm.teammade.entity.Todo;

import java.util.List;

public interface TodoService {
    List<Todo> create(final Todo todo);
    List<Todo> retrieve(final String userId);
}
