package de.rki.mamodar.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.rki.mamodar.rdmo.RdmoApiConsumer;
import java.util.ArrayList;

/**
 * The representation of a RDMO project. Used by {@link RdmoApiConsumer} to handle RDMO projects.
 *
 * @author Kyanoush Yahosseini
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RdmoProjectDTO {

  private Long id;
  private String title;
  private String description;
  private boolean read_only;
  private ArrayList<RdmoOwnerDTO> owners = new ArrayList<>();

  /**
   * Gets the  title.
   *
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the title.
   *
   * @param title the title
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Gets the description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description.
   *
   * @param description the description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets the read only flag
   *
   * @return the boolean
   */
  public boolean isRead_only() {
    return read_only;
  }

  /**
   * Sets the read only flag.
   *
   * @param read_only the read only
   */
  public void setRead_only(boolean read_only) {
    this.read_only = read_only;
  }

  /**
   * Gets the  rdmo owners.
   *
   * @return the rdmo owner array
   */
  public ArrayList<RdmoOwnerDTO> getOwners() {
    return owners;
  }

  /**
   * Sets the rmdo owners.
   *
   * @param owners the owners
   */
  public void setOwners(ArrayList<RdmoOwnerDTO> owners) {
    this.owners = owners;
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(Long id) {
    this.id = id;
  }

}
