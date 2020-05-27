package de.rki.mamodar.edoc;

import java.util.Objects;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * This component access the API of an edoc instance.
 */
@Component
public class EdocRestConsumer {

  private final Environment env;

  /**
   * Instantiates a new edoc rest consumer.
   *
   * @param env the autowired environment
   */
  public EdocRestConsumer(Environment env) {
    this.env = env;
  }

  /**
   * Publish a item description and file to an edoc instance.
   *
   * @param edocItemDTO a edoc item
   * @return a response entity
   */
  public ResponseEntity<String> publishToEdoc(EdocItemDTO edocItemDTO) {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("file", edocItemDTO.getFile());
    body.add("name", edocItemDTO.getName());
    body.add("email", edocItemDTO.getEmail());

    HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
    ResponseEntity<String> response = new RestTemplate().
        exchange(
            Objects.requireNonNull(env.getProperty("edoc.url")),
            HttpMethod.POST,
            request,
            String.class
        );
    return response;
  }
}
