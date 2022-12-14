package com.mztm.teammade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TeamMadeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamMadeApplication.class, args);
    }

}
