package com.mztm.teammade.persistence;

import com.mztm.teammade.entity.File;
import com.mztm.teammade.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, String> {
    List<File> findByProject(Project project);
}