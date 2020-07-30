package de.rki.datalinker.external.rdmo;

import de.rki.datalinker.DataLinkerApplication;
import de.rki.datalinker.dto.RdmoOptionDTO;
import de.rki.datalinker.dto.RdmoProjectDTO;
import de.rki.datalinker.dto.RdmoQuestionDTO;
import de.rki.datalinker.dto.RdmoValueDTO;
import de.rki.datalinker.external.rdmo.database.RdmoQuestion;
import de.rki.datalinker.external.rdmo.database.RdmoValue;
import java.util.Collections;
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
 * This low-level component implements the API consumer for a RDMO instance.
 *
 * @author Kyanoush Yahosseini
 */
@Component
public class RdmoApiConsumer {

  private static final Logger log = LoggerFactory.getLogger(DataLinkerApplication.class);
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

  /**
   * Requests all rdmo projects into {@link RdmoProjectDTO}.
   *
   * @return the raw rdmo projects as an array
   */
  public RdmoProjectDTO[] requestRdmoProjects() {
    log.info(env.getProperty("rdmo.url") + "/projects/projects/");
    String rdmoUrl = env.getProperty("rdmo.url") + "/projects/projects/";
    ResponseEntity<RdmoProjectDTO[]> response = new RestTemplate().exchange(
        rdmoUrl,
        HttpMethod.GET,
        getRdmoCompatibleHttpEntity(),
        RdmoProjectDTO[].class
    );
    return response.getBody();
  }

  /**
   * Requests all rdmo values into {@link RdmoValue}.
   *
   * @return the raw rdmo values as an array
   */
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

  /**
   * Requests all rdmo options into {@link RdmoOptionDTO}.
   *
   * @return the raw rdmo options as an array
   */
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


  /**
   * Requests all rdmo questions into {@link RdmoQuestion}.
   *
   * @return the raw rdmo questions as an array
   */
  public RdmoQuestionDTO[] requestRdmoQuestions() {
    log.info(env.getProperty("rdmo.url") + "/questions/questions/");
    String rdmoUrl = env.getProperty("rdmo.url") + "/questions/questions/";
    ResponseEntity<RdmoQuestionDTO[]> response = new RestTemplate().exchange(
        rdmoUrl,
        HttpMethod.GET,
        getRdmoCompatibleHttpEntity(),
        RdmoQuestionDTO[].class
    );
    return response.getBody();
  }
}
