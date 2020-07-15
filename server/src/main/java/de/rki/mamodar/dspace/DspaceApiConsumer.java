package de.rki.mamodar.dspace;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;


/**
 * This low-level component implements the API consumer for a dspace instance.
 *
 * @author Kyanoush Yahosseini
 */

@Component
public class DspaceApiConsumer {


  private final Gson gson = new Gson();
  private final Environment env;


  private String sessionId = "";


  /**
   * Instantiates a new dspace rest consumer.
   *
   * @param env the autowired environment
   */
  public DspaceApiConsumer(Environment env) {
    this.env = env;
  }

  private HttpHeaders createBasicHeaders() {
    HttpHeaders headers = new HttpHeaders();
    ArrayList accept = new ArrayList();
    accept.add(MediaType.APPLICATION_JSON);
    headers.setAccept(accept);
    headers.setContentType(MediaType.APPLICATION_JSON);
    return headers;
  }

  /**
   * Authenticate with the dspace instance. Save the sessionId.
   *
   * @return the response entity
   */
  public ResponseEntity<String> auth() {
    HttpHeaders headers = createBasicHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("email", env.getProperty("dspace.user"));
    body.add("password", env.getProperty("dspace.password"));
    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
    ResponseEntity<String> response = new RestTemplate().
        exchange(env.getProperty("dspace.url") + "/login", HttpMethod.POST, request, String.class);
    this.sessionId = response.getHeaders().get("Set-Cookie").get(0);
    return response;
  }


  /**
   * Creates a POST request to create an item in a collection (provided by the environment). The item is empty.
   *
   * @return the http entity
   */
  public HttpEntity<String> createItem() {
    HttpHeaders headers = createBasicHeaders();
    headers.add("Cookie", this.sessionId);

    HttpEntity<String> request = new HttpEntity<>("{}", headers);
    ResponseEntity<String> response = new RestTemplate().
        exchange(env.getProperty("dspace.url") + "/collections/" + env.getProperty("dspace.collection") + "/items",
            HttpMethod.POST, request, String.class);

    return response;
  }

  /**
   * Creates a POST request to add metadata to an item. Item is identified by its id (a uuid).
   *
   * @param uuid     the uuid
   * @param metadata the metadata
   * @return the response entity
   */
  public ResponseEntity<String> addMetadata(String uuid, ArrayList<DspaceMetadataDTO> metadata) {

    HttpHeaders headers = createBasicHeaders();
    headers.add("Cookie", this.sessionId);

    HttpEntity<String> request = new HttpEntity<>(gson.toJson(metadata), headers);

    ResponseEntity<String> response = new RestTemplate().
        exchange(env.getProperty("dspace.url") + "/items/" + uuid + "/metadata",
            HttpMethod.PUT, request, String.class);

    return response;

  }

  /**
   * Creates a POST request to add a bitstream (a file) to an item. Item is identified by its id (a uuid).
   *
   * @param uuid the uuid
   * @param file the file
   * @return the response entity
   * @throws IOException the io exception
   */
  public ResponseEntity<String> addBitstream(String uuid, MultipartFile file) throws IOException {
    HttpHeaders headers = createBasicHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.add("Cookie", this.sessionId);
    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("file", file.getBytes());
    HttpEntity<byte[]> request = new HttpEntity<>(file.getBytes(), headers);
    ResponseEntity<String> response = new RestTemplate().
        exchange(env.getProperty("dspace.url") + "/items/" + uuid + "/bitstreams?name=" + file.getOriginalFilename(),
            HttpMethod.POST, request, String.class);

    return response;

  }
}
