package de.rki.mamodar.zenodo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;


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


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public ArrayList<String> getKeywords() {
    return keywords;
  }

  public void setKeywords(ArrayList<String> keywords) {
    this.keywords = keywords;
  }

  @JsonProperty("publication_date")
  public String getPublicationDate() {
    return publicationDate;
  }

  public void setPublicationDate(String publicationDate) {
    this.publicationDate = publicationDate;
  }

  public String getLicense() {
    return license;
  }

  public void setLicense(String license) {
    this.license = license;
  }

  @JsonProperty("access_right")
  public String getAccessRight() {
    return accessRight;
  }

  public void setAccessRight(String accessRight) {
    this.accessRight = accessRight;
  }

  @JsonProperty("upload_type")
  public String getUploadType() {
    return uploadType;
  }

  public void setUploadType(String uploadType) {
    this.uploadType = uploadType;
  }

  public ArrayList<Author> getCreators() {
    return creators;
  }

  public void addCreator(String author, String affiliation) {
    creators.add(new Author(author, affiliation));
  }


  private class Author {

    String name;
    String affiliation;

    public Author(String name, String affiliation) {
      this.name = name;
      this.affiliation = affiliation;
    }

    public String getName() {
      return name;
    }

    public String getAffiliation() {
      return affiliation;
    }
  }
}
