package com.mztm.teammade;

import com.mztm.teammade.service.FileStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.Resource;

@EnableJpaAuditing
@SpringBootApplication
public class TeamMadeApplication implements CommandLineRunner {
    @Resource
    FileStorageService storageService;

    public static void main(String[] args) {
        SpringApplication.run(TeamMadeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        storageService.init();
    }

}
