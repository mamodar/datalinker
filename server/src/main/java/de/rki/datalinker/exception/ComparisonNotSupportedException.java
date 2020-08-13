package de.rki.datalinker.exception;

import de.rki.datalinker.DataLinkerApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The exception if a comparison is not supported
 */
public class ComparisonNotSupportedException extends RuntimeException {

  private static final Logger log = LoggerFactory.getLogger(DataLinkerApplication.class);

  /**
   * Instantiates a new comparison not supported exception.
   *
   * @param type which is not supported
   */
  public ComparisonNotSupportedException(String type) {
    super("Comparison type " + type + " not supported.");
    log.warn("Comparison type " + type + " not supported.");
  }

}
