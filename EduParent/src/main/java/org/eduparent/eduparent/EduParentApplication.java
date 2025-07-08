package org.eduparent.eduparent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import io.github.cdimascio.dotenv.Dotenv;

@EnableAsync  // ⬅ Activar @Async
@SpringBootApplication
public class EduParentApplication {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load(); // carga .env
        System.setProperty("SPRING_APPLICATION_NAME", dotenv.get("SPRING_APPLICATION_NAME"));
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("DB_DRIVER_CLASS", dotenv.get("DB_DRIVER_CLASS"));
        System.setProperty("JPA_SHOW_SQL", dotenv.get("JPA_SHOW_SQL"));
        System.setProperty("JPA_HIBERNATE_DDL_AUTO", dotenv.get("JPA_HIBERNATE_DDL_AUTO"));
        System.setProperty("SERVER_PORT", dotenv.get("SERVER_PORT"));
        System.setProperty("MAIL_HOST", dotenv.get("MAIL_HOST"));
        System.setProperty("MAIL_PORT", dotenv.get("MAIL_PORT"));
        System.setProperty("MAIL_USERNAME", dotenv.get("MAIL_USERNAME"));
        System.setProperty("MAIL_PASSWORD", dotenv.get("MAIL_PASSWORD"));
        System.setProperty("MAIL_SMTP_AUTH", dotenv.get("MAIL_SMTP_AUTH"));
        System.setProperty("MAIL_SMTP_STARTTLS_ENABLE", dotenv.get("MAIL_SMTP_STARTTLS_ENABLE"));
        System.setProperty("MAIL_SMTP_STARTTLS_REQUIRED", dotenv.get("MAIL_SMTP_STARTTLS_REQUIRED"));
        System.setProperty("OPENAI_API_KEY", dotenv.get("OPENAI_API_KEY"));

        SpringApplication.run(EduParentApplication.class, args);
    }
}