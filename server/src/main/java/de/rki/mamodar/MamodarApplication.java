package de.rki.mamodar;


import de.rki.mamodar.rdmo.RdmoApiConsumer;
import de.rki.mamodar.rdmo.RdmoConverter;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestClientException;

/**
 * This class provides the main entry point, it starts our spring boot application.
 *
 * @author Kyanoush Yahosseini
 */
@SpringBootApplication
@EnableScheduling
public class MamodarApplication {

  private static final Logger log = LoggerFactory.getLogger(MamodarApplication.class);

  /**
   * The autowired Rdmo api consumer.
   */
  @Autowired
  RdmoApiConsumer rdmoApiConsumer;
  /**
   * The autowired Rdmo converter.
   */
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
   * This bean preloads all relevant rdmo data into the database when starting the application.
   */
  @Bean
  public void preloadRdmoData() {
    try {
      log.info("preloading rdmo data:" + new Date().toInstant().toString());
      rdmoConverter.addRdmoOptions(rdmoApiConsumer.requestRdmoOptions());
      rdmoConverter.addRdmoQuestions(rdmoApiConsumer.requestRdmoQuestions());
      rdmoConverter.addRdmoValues(rdmoApiConsumer.requestRdmoValues());
      rdmoConverter.updateRdmoProjects(rdmoApiConsumer.requestRdmoProjects());
      log.info("preloaded rdmo data:" + new Date().toInstant().toString());
    } catch (RestClientException clientException) {
      log.warn("Failed to reach rdmo:" + clientException.toString());
    }
  }

  /**
   * This scheduled bean periodically updates the database by reading from rdmo. This is ensures that changes in rdmo
   * are refleced in the database of the datalinker.
   */
  @Scheduled(fixedDelay = 60000)  //one minute 60000
  public void updateData() {
    try {
      log.info(new Date().toInstant().toString() + " refreshing data");
      rdmoConverter.addRdmoValues(rdmoApiConsumer.requestRdmoValues());
      rdmoConverter.updateRdmoProjects(rdmoApiConsumer.requestRdmoProjects());
      log.info(new Date().toInstant().toString() + " refreshed data");
    } catch (RestClientException clientException) {
      log.warn("Failed to reach rdmo:" + clientException.toString());
    }
  }

}
