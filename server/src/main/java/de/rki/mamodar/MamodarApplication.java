package de.rki.mamodar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * This class provides the main entry point, it starts our spring boot application.
 * @author Kyanoush Yahosseini
 */
@SpringBootApplication
public class MamodarApplication {

  private static final Logger log = LoggerFactory.getLogger(MamodarApplication.class);

  /**
   * The main function and entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(MamodarApplication.class, args);
  }

  /**
   * Show all available projects at startup.
   *
   * @param projectRepository the project repository
   * @return the command line runner

  @Bean
  public CommandLineRunner demoProjects(ProjectRepository projectRepository) {
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
   */
}
