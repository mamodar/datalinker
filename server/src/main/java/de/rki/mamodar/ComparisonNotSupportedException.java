package de.rki.mamodar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComparisonNotSupportedException extends RuntimeException {

  private static final Logger log = LoggerFactory.getLogger(MamodarApplication.class);

  public ComparisonNotSupportedException(String type) {
    super("Comparison type " + type + " not supported.");
    log.warn("Comparison type " + type + " not supported.");
  }

}
