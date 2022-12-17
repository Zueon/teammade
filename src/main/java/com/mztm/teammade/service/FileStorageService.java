package com.mztm.teammade.service;

import com.mztm.teammade.dto.ResumeDto;
import com.mztm.teammade.entity.File;
import com.mztm.teammade.entity.Member;
import com.mztm.teammade.entity.Project;
import com.mztm.teammade.entity.Resume;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileStorageService {
     void init();

     void save(MultipartFile file);

     Resource load(String filename);

     void deleteAll();

     Stream<Path> loadAll();

     Resume storeResume(MultipartFile file, String email) throws IOException;
     ResumeDto getResume(Member member);

     File store(MultipartFile file, Long projectId) throws IOException;
     File getFile(String id);
     Stream<File> getAllFiles(Project project);
     Stream<File> deleteFile(String fileId);

}