package de.rki.mamodar.database.search;

/**
 * This class specifies one search criteria of the form key, operation, value.
 */
public class SearchCriteria {

  private String key;
  private String operation;
  private String value;

  /**
   * Instantiates a new Search criteria.
   *
   * @param key       the key
   * @param operation the operation
   * @param value     the value
   */
  public SearchCriteria(String key, String operation, String value) {
    this.key = key;
    this.operation = operation;
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
   * Gets operation.
   *
   * @return the operation
   */
  public String getOperation() {
    return operation;
  }

  /**
   * Gets value.
   *
   * @return the value
   */
  public String getValue() {
    return value;
  }
}
