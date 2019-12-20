package de.rki.mamodar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MamodarApplication {

  private static final Logger log = LoggerFactory.getLogger(MamodarApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(MamodarApplication.class, args);
  }

  @Bean
  public CommandLineRunner demoProjects(ProjectRepository projectRepository,
      ResourceRepository resourceRepository) {
    return (args) -> {
      // fetch all customers
      log.info("Projects found with findAll():");
      log.info("-------------------------------");
      for (Project it : projectRepository.findAll()) {
        log.info(it.toString());
      }
      log.info("");
    };
  }
}
