package com.mztm.teammade.service;

import com.mztm.teammade.entity.File;
import com.mztm.teammade.entity.Project;
import com.mztm.teammade.persistence.FileRepository;
import com.mztm.teammade.persistence.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Log4j2
public class FileStorageServiceImpl implements FileStorageService{
    private final FileRepository fileRepository;
    private final ProjectRepository projectRepository;
    private final Path root = Paths.get("uploads");

    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
    @Override
    public File store(MultipartFile file) throws IOException {
        log.info("store");
        Project temp = projectRepository.findById(1L).get();

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        log.info("filename" + fileName);
        File File = new File(fileName, file.getContentType(), file.getBytes(), temp);
        log.info(File);

        return fileRepository.save(File);
    }

    @Override
    public File getFile(String id) {
        return fileRepository.findById(id).get();
    }


    @Override
    public Stream<File> getAllFiles() {
        Project temp = projectRepository.findById(1L).get();

        return fileRepository.findByProject(temp).stream();
    }
}
