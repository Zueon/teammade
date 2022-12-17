package com.mztm.teammade.persistence;

import com.mztm.teammade.entity.Project;
import com.mztm.teammade.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByProject(Project project);

}
