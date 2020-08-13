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

/**
 * This low-level component implements the API consumer for zendodo.
 *
 * @author Kyanoush Yahosseini
 */
@Component
public class ZenodoApiConsumer {

  private static final Logger log = LoggerFactory.getLogger(DataLinkerApplication.class);
  private final Environment env;

  /**
   * Instantiates a new Zenodo api consumer.
   *
   * @param env the env
   */
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

  /**
   * Creates a POST request to create an item. The provided metadata is connected to the item.
   *
   * @return the the api response
   */
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


  /**
   * Creates POST requests to add a bitstreams (files) to an item. Item is identified by its id (a uuid).
   *
   * @param uuid  the uuid
   * @param files the files array
   * @return the the api response
   * @throws IOException the io exception
   */
  public ResponseEntity<String> addFiles(String uuid, MultipartFile[] files) throws IOException {
    ArrayList<MultipartFile> filesList = new ArrayList<>(Arrays.asList(files));
    ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    for (MultipartFile multipartFile : filesList) {
      response = addFile(uuid, multipartFile);
    }
    return response;
  }

  /**
   * Creates a POST request to add a bitstream (a file) to an item. Item is identified by its id (a uuid).
   *
   * @param uuid the uuid
   * @param file the file
   * @return the the api response
   * @throws IOException the io exception
   */
  public ResponseEntity<String> addFile(String uuid, MultipartFile file) throws IOException {
    HttpHeaders headers = createBasicHeaders();
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    HttpEntity<byte[]> request = new HttpEntity<>(file.getBytes(), headers);
    ResponseEntity<String> response = new RestTemplate()
        .exchange(createBasicUri("/files/" + uuid + "/" + file.getOriginalFilename()).toUriString(), HttpMethod.PUT,
            request, String.class);
    return response;
  }

  /**
   * Creates a POST request to publish an item. Item is identified by its id (a uuid).
   *
   * @param uuid the uuid
   * @return the the api response
   */
  public ResponseEntity<String> publishItem(Long uuid) {
    HttpHeaders headers = createBasicHeaders();
    HttpEntity<String> request = new HttpEntity<>("{}", headers);
    ResponseEntity<String> response = new RestTemplate()
        .exchange(createBasicUri("/deposit/depositions/" + uuid + "/actions/publish").toUriString(), HttpMethod.POST,
            request, String.class);
    return response;
  }


  /**
   * Gets item.
   *
   * @param id the id
   * @return the item
   */
  public ResponseEntity<String> getItem(Long id) {
    HttpHeaders headers = createBasicHeaders();
    HttpEntity<String> request = new HttpEntity<>(headers);
    ResponseEntity<String> response = new RestTemplate()
        .exchange(createBasicUri("/deposit/depositions/" + id.toString()).toUriString(), HttpMethod.GET, request,
            String.class);
    return response;
  }

  /**
   * Gets bucket uuid.
   *
   * @param response the response
   * @return the bucket uuid
   */
  public String getBucketUuid(ResponseEntity<String> response) {
    Pattern pattern = Pattern.compile("bucket\":\"([\\w-:/.]*)");
    Matcher matcher = pattern.matcher(response.getBody());
    matcher.find();
    String[] url = matcher.group(1).split("/");
    return url[url.length - 1];
  }

  /**
   * Gets id.
   *
   * @param response the response
   * @return the id
   */
  public Long getId(ResponseEntity<String> response) {
    Pattern pattern = Pattern.compile("\"id\":([0-9]*)");
    Matcher matcher = pattern.matcher(response.getBody());
    matcher.find();
    return Long.parseLong(matcher.group(1));
  }
}
