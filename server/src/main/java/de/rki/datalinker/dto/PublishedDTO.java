package de.rki.datalinker.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class provides a DTO for sending the id of an published project back to the frontend.
 *
 * @author Kyanoush Yahosseini
 */
public class PublishedDTO {

  private String baseUrl;
  private String url;
  private String id;

  /**
   * Instantiates a new Published dto.
   *
   * @param id the id
   */
  public PublishedDTO(String id) {
    this.id = id;
  }

  /**
   * Instantiates a new Published dto.
   *
   * @param baseUrl the base url
   * @param id      the id
   */
  public PublishedDTO(String baseUrl, String id) {
    this.baseUrl = baseUrl;
    this.id = id;
    this.url = this.baseUrl + this.id;
  }

  /**
   * Gets base url.
   *
   * @return the base url
   */
  public String getBaseUrl() {
    return baseUrl;
  }

  /**
   * Gets url.
   *
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * To json string string.
   *
   * @return the string
   * @throws JsonProcessingException the json processing exception
   */
  public String toJsonString() throws JsonProcessingException {
    return new ObjectMapper().writeValueAsString(this);
  }
}
