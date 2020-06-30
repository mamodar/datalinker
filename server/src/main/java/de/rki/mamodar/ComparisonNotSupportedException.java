package de.rki.mamodar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The exception if a comparison is not supported
 */
public class ComparisonNotSupportedException extends RuntimeException {

  private static final Logger log = LoggerFactory.getLogger(MamodarApplication.class);

  /**
   * Instantiates a new comparison not supported exception.
   *
   * @param the type which is not supported
   */
  public ComparisonNotSupportedException(String type) {
    super("Comparison type " + type + " not supported.");
    log.warn("Comparison type " + type + " not supported.");
  }

}
