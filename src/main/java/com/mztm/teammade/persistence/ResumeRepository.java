package com.mztm.teammade.persistence;

import com.mztm.teammade.entity.Member;
import com.mztm.teammade.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, String> {
    Resume findByCreator(Member creator);
}