package com.mztm.teammade.service;

import com.mztm.teammade.entity.Post;
import com.mztm.teammade.entity.Project;
import com.mztm.teammade.entity.Study;
import com.mztm.teammade.persistence.ProjectRepository;
import com.mztm.teammade.persistence.StudyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final ProjectRepository projectRepository;
    private final StudyRepository studyRepository;

    @Override
    public Project createProject(Project project) {
        validate(project);
        Project saveProject = projectRepository.save(project);
        log.info("Entity ID : {} is saved", project.getId());
        return saveProject;
    }

    @Override
    public List<Project> getProjectList() {
        return projectRepository.findAll();
    }

    @Override
    public Project updateProject(Project project) {
        return null;
    }

    @Override
    public List<Project> deleteProject(Project project) {
        return null;
    }

    private void validate(Post post){
        if (post == null) {
            log.warn("Post Entity cannot be null");
            throw new RuntimeException("Entity cannot be null.");
        }


    }

    @Override
    public Study createStudy(Study study) {
        validate(study);
        Study saveStudy = studyRepository.save(study);
        log.info("Entity ID : {} is saved", study.getId());
        return saveStudy;

    }

    @Override
    public List<Study> getStudyList() {

        return studyRepository.findAll();
    }

    @Override
    public Study updateStudy(Study study) {
        return null;
    }

    @Override
    public List<Study> deleteStudy(Study study) {
        return null;
    }
}
