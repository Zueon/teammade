package com.mztm.teammade.service;

import com.mztm.teammade.dto.ResumeDto;
import com.mztm.teammade.entity.File;
import com.mztm.teammade.entity.Member;
import com.mztm.teammade.entity.Resume;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileStorageService {
    public void init();

    public void save(MultipartFile file);

    public Resource load(String filename);

    public void deleteAll();

    public Stream<Path> loadAll();

    public Resume storeResume(MultipartFile file, String email) throws IOException;
    public ResumeDto getResume(Member member);
    public File store(MultipartFile file, Long projectId) throws IOException;
    public File getFile(String id);
    public Stream<File> getAllFiles(Long projectId);

}