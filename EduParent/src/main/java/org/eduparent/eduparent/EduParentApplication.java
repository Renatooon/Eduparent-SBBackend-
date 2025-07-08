package org.eduparent.eduparent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class EduParentApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduParentApplication.class, args);
    }
}