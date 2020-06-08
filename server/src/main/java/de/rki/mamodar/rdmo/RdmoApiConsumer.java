package de.rki.mamodar.rdmo;

import de.rki.mamodar.MamodarApplication;
import java.util.Collections;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * This component accesses the API of a RDMO instance. Converts {link de.rki.mamodar.rdmo.RdmoProject}s to {link
 * de.rki.mamodar.Project}s
 *
 * @author Kyanoush Yahosseini
 */
@Component
public class RdmoApiConsumer {

  private static final Logger log = LoggerFactory.getLogger(MamodarApplication.class);
  private final Environment env;

  /**
   * Instantiates a new RDMO rest consumer.
   *
   * @param env the autowired environment
   */
  public RdmoApiConsumer(Environment env) {
    this.env = env;
  }


  private HttpEntity getRdmoCompatibleHttpEntity() {
    String rdmoToken = env.getProperty("rdmo.token");
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.set("Authorization", "Token " + rdmoToken);
    headers.set("Access-Control-Allow-Origin", "*");
    HttpEntity request = new HttpEntity(headers);
    return request;
  }

  public RdmoProjectDTO[] requestRdmoProjects() {
    log.info(env.getProperty("rdmo.url") + "/projects/projects/");
    String rdmoUrl = env.getProperty("rdmo.url") + "/projects/projects/";
    ResponseEntity<RdmoProjectDTO[]> response = new RestTemplate().exchange(
        rdmoUrl,
        HttpMethod.GET,
        getRdmoCompatibleHttpEntity(),
        RdmoProjectDTO[].class
    );
    log.warn(new Date().toInstant().toString());
    return response.getBody();
  }

  public RdmoValueDTO[] requestRdmoValues() {
    log.info(env.getProperty("rdmo.url") + "/projects/values/");
    String rdmoUrl = env.getProperty("rdmo.url") + "/projects/values/";

    ResponseEntity<RdmoValueDTO[]> response = new RestTemplate().exchange(
        rdmoUrl,
        HttpMethod.GET,
        getRdmoCompatibleHttpEntity(),
        RdmoValueDTO[].class
    );
    return response.getBody();
  }

  public RdmoOptionDTO[] requestRdmoOptions() {
    log.info(env.getProperty("rdmo.url") + "/options/options/");
    String rdmoUrl = env.getProperty("rdmo.url") + "/options/options/";
    ResponseEntity<RdmoOptionDTO[]> response = new RestTemplate().exchange(
        rdmoUrl,
        HttpMethod.GET,
        getRdmoCompatibleHttpEntity(),
        RdmoOptionDTO[].class
    );
    return response.getBody();
  }


  public RdmoQuestionDTO[] requestRdmoQuestions() {
    log.info(env.getProperty("rdmo.url") + "/questions/questions/");
    String rdmoUrl = env.getProperty("rdmo.url") + "/questions/questions/";
    ResponseEntity<RdmoQuestionDTO[]> response = new RestTemplate().exchange(
        rdmoUrl,
        HttpMethod.GET,
        getRdmoCompatibleHttpEntity(),
        RdmoQuestionDTO[].class
    );
    log.warn(new Date().toInstant().toString());
    return response.getBody();
  }
}
