package com.mztm.teammade.service;

import com.mztm.teammade.entity.Project;
import com.mztm.teammade.entity.Study;

import java.util.List;

public interface PostService {
    Project getProject(Long pid);
    Project createProject(Project project);
    List<Project> getProjectList();
    Project updateProject(Project project);
    List<Project> deleteProject(Project project);


    Study createStudy(Study study);
    List<Study> getStudyList();
    Study updateStudy(Study study);
    List<Study> deleteStudy(Study study);
}
