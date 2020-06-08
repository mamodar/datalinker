package de.rki.mamodar;

import de.rki.mamodar.rdmo.RdmoApiConsumer;
import de.rki.mamodar.rdmo.RdmoConverter;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * This class provides the main entry point, it starts our spring boot application.
 *
 * @author Kyanoush Yahosseini
 */
@SpringBootApplication
@EnableScheduling
public class MamodarApplication {

  private static final Logger log = LoggerFactory.getLogger(MamodarApplication.class);

  @Autowired
  RdmoApiConsumer rdmoApiConsumer;
  @Autowired
  RdmoConverter rdmoConverter;

  /**
   * The main function and entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(MamodarApplication.class, args);
  }

  /**
   * Show version information at startup
   *
   * @return the command line runner
   */
  @Bean
  public CommandLineRunner demoProjects() {
    return (args) -> {
      log.info("202005225588");
    };
  }

  @Bean
  public void preloadRdmoData() {
    try {
      log.warn("preloading rdmo data:" + new Date().toInstant().toString());
      rdmoConverter.addRdmoOptions(rdmoApiConsumer.requestRdmoOptions());
      rdmoConverter.addRdmoQuestions(rdmoApiConsumer.requestRdmoQuestions());
      rdmoConverter.addRdmoValues(rdmoApiConsumer.requestRdmoValues());
      rdmoConverter.updateRdmoProjects(rdmoApiConsumer.requestRdmoProjects());
    } catch (Exception e) {
      log.warn(e.toString());
    }
  }

  @Scheduled(fixedDelay = 60000)  //one minute 60000
  public void updateData() {
    try {
      log.info(new Date().toInstant().toString() + " refresh data");
      rdmoConverter.addRdmoValues(rdmoApiConsumer.requestRdmoValues());
      rdmoConverter.updateRdmoProjects(rdmoApiConsumer.requestRdmoProjects());
    } catch (Exception e) {
      log.warn(e.toString());
    }
  }
}
