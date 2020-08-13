package de.rki.datalinker.external.zenodo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;


/**
 * The type Zenodo metdatata dto.
 */
public class ZenodoMetdatataDTO {

  private String title;
  private String description;
  private ArrayList<Author> creators = new ArrayList<>();
  private ArrayList<String> keywords = new ArrayList<>();
  @JsonProperty("publication_date")
  private String publicationDate;
  @JsonProperty("access_right")
  private String accessRight = "open";
  @JsonProperty("upload_type")
  private String uploadType = "dataset";
  private String license = "cc-by";


  /**
   * Gets title.
   *
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets title.
   *
   * @param title the title
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Gets description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets description.
   *
   * @param description the description
   */
  public void setDescription(String description) {
    this.description = description;
  }


  /**
   * Gets keywords.
   *
   * @return the keywords
   */
  public ArrayList<String> getKeywords() {
    return keywords;
  }

  /**
   * Sets keywords.
   *
   * @param keywords the keywords
   */
  public void setKeywords(ArrayList<String> keywords) {
    this.keywords = keywords;
  }

  /**
   * Gets publication date.
   *
   * @return the publication date
   */
  @JsonProperty("publication_date")
  public String getPublicationDate() {
    return publicationDate;
  }

  /**
   * Sets publication date.
   *
   * @param publicationDate the publication date
   */
  public void setPublicationDate(String publicationDate) {
    this.publicationDate = publicationDate;
  }

  /**
   * Gets license.
   *
   * @return the license
   */
  public String getLicense() {
    return license;
  }

  /**
   * Sets license.
   *
   * @param license the license
   */
  public void setLicense(String license) {
    this.license = license;
  }

  /**
   * Gets access right.
   *
   * @return the access right
   */
  @JsonProperty("access_right")
  public String getAccessRight() {
    return accessRight;
  }

  /**
   * Sets access right.
   *
   * @param accessRight the access right
   */
  public void setAccessRight(String accessRight) {
    this.accessRight = accessRight;
  }

  /**
   * Gets upload type.
   *
   * @return the upload type
   */
  @JsonProperty("upload_type")
  public String getUploadType() {
    return uploadType;
  }

  /**
   * Sets upload type.
   *
   * @param uploadType the upload type
   */
  public void setUploadType(String uploadType) {
    this.uploadType = uploadType;
  }

  /**
   * Gets creators.
   *
   * @return the creators
   */
  public ArrayList<Author> getCreators() {
    return creators;
  }

  /**
   * Add creator.
   *
   * @param author      the author
   * @param affiliation the affiliation
   */
  public void addCreator(String author, String affiliation) {
    creators.add(new Author(author, affiliation));
  }


  private class Author {

    /**
     * The Name.
     */
    String name;
    /**
     * The Affiliation.
     */
    String affiliation;

    /**
     * Instantiates a new Author.
     *
     * @param name        the name
     * @param affiliation the affiliation
     */
    public Author(String name, String affiliation) {
      this.name = name;
      this.affiliation = affiliation;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
      return name;
    }

    /**
     * Gets affiliation.
     *
     * @return the affiliation
     */
    public String getAffiliation() {
      return affiliation;
    }
  }
}
