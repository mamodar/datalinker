package de.rki.mamodar.zenodo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.rki.mamodar.MamodarApplication;
import de.rki.mamodar.dspace.MetadataDTO;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class ZenodoApiConsumer {

  private static final Logger log = LoggerFactory.getLogger(MamodarApplication.class);
  private final Environment env;

  public ZenodoApiConsumer(Environment env) {
    this.env = env;
  }

  private HttpHeaders createBasicHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return headers;
  }

  private UriComponentsBuilder createBasicUri(String uriPath) {
   return UriComponentsBuilder.fromHttpUrl(env.getProperty("zenodo.url") + uriPath)
       .queryParam("access_token", env.getProperty("zenodo.access_token"));
  }

  public ResponseEntity<String> createItem(MetadataDTO metadata) throws JsonProcessingException {
    HttpHeaders headers = createBasicHeaders();
    HttpEntity<String> request = new HttpEntity<>(
        "{\"metadata\":" + new ObjectMapper().writeValueAsString(metadata.toZenodoMetadataList()) + "}",
        headers);
    log.warn(request.getBody());
    ResponseEntity<String> response = new RestTemplate()
        .exchange(createBasicUri("/deposit/depositions").toUriString(), HttpMethod.POST, request, String.class);
    return response;
  }

  public ResponseEntity<String> addFile(String uuid, MultipartFile file) throws IOException {
    HttpHeaders headers = createBasicHeaders();
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    HttpEntity<byte[]> request = new HttpEntity<>(file.getBytes(), headers);
    ResponseEntity<String> response = new RestTemplate()
        .exchange(createBasicUri("/files/" + uuid + "/" + file.getOriginalFilename()).toUriString(), HttpMethod.PUT,
            request, String.class);
    return response;
  }

  public ResponseEntity<String> publishItem(Long id) {
    HttpHeaders headers = createBasicHeaders();
    HttpEntity<String> request = new HttpEntity<>("{}", headers);
    ResponseEntity<String> response = new RestTemplate()
        .exchange(createBasicUri("/deposit/depositions/" + id + "/actions/publish").toUriString(), HttpMethod.POST,
            request, String.class);
    return response;
  }


  public ResponseEntity<String> getItem(Long id) {
    HttpHeaders headers = createBasicHeaders();
    HttpEntity<String> request = new HttpEntity<>(headers);
    ResponseEntity<String> response = new RestTemplate()
        .exchange(createBasicUri("/deposit/depositions/" + id.toString()).toUriString(), HttpMethod.GET, request,
            String.class);
    return response;
  }
}
