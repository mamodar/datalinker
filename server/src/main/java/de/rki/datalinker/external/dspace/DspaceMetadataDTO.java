package de.rki.datalinker.external.dspace;

/**
 * This class provides a DTO for sending metadata to an dspace instance.
 *
 * @author Kyanoush Yahosseini
 */
public class DspaceMetadataDTO {

  private String key;
  private String value;
  private String language;
  private String qualifier;
  private String schema;
  private String date;

  /**
   * Instantiates a new Dspace metadata dto.
   */
  public DspaceMetadataDTO() {
  }

  /**
   * Instantiates a new Dspace metadata dto.
   *
   * @param key   the key
   * @param value the value
   */
  public DspaceMetadataDTO(String key, String value) {
    this.key = key;
    this.value = value;
  }

  /**
   * Gets key.
   *
   * @return the key
   */
  public String getKey() {
    return key;
  }

  /**
   * Sets key.
   *
   * @param key the key
   */
  public void setKey(String key) {
    this.key = key;
  }

  /**
   * Gets value.
   *
   * @return the value
   */
  public String getValue() {
    return value;
  }

  /**
   * Sets value.
   *
   * @param value the value
   */
  public void setValue(String value) {
    this.value = value;
  }


}
