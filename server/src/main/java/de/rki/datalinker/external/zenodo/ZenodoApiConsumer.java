package de.rki.datalinker.external.zenodo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.rki.datalinker.DataLinkerApplication;
import de.rki.datalinker.external.dspace.MetadataDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class ZenodoApiConsumer {

  private static final Logger log = LoggerFactory.getLogger(DataLinkerApplication.class);
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


  public ResponseEntity<String> addFiles(String uuid, MultipartFile[] files) throws IOException {
    ArrayList<MultipartFile> filesList = new ArrayList<>(Arrays.asList(files));
    ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    for (MultipartFile multipartFile : filesList) {
      response = addFile(uuid, multipartFile);
    }
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
        .exchange(createBasicUri("/deposit/depositions/" + uuid + "/actions/publish").toUriString(), HttpMethod.POST,
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

  public String getBucketUuid(ResponseEntity<String> response) {
    Pattern pattern = Pattern.compile("bucket\":\"([\\w-:/.]*)");
    Matcher matcher = pattern.matcher(response.getBody());
    matcher.find();
    String[] url = matcher.group(1).split("/");
    return url[url.length - 1];
  }

  public Long getItemId(ResponseEntity<String> response) {
    Pattern pattern = Pattern.compile("\"id\":([0-9]*)");
    Matcher matcher = pattern.matcher(response.getBody());
    matcher.find();
    return Long.parseLong(matcher.group(1));
  }
}
