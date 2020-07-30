package de.rki.datalinker.exception;

import de.rki.datalinker.DataLinkerApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception is used when a object is not found in any controller call.
 *
 * @author Kyanoush Yahosseini
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException {

  private static final Logger log = LoggerFactory.getLogger(DataLinkerApplication.class);

  /**
   * Instantiates a new object not found exception.
   *
   * @param type type of the object not found
   * @param id   the id which is not found
   */
  public ObjectNotFoundException(String type, long id) {
    super("Could not find object of type '" + type + "' with id '" + id + "'.");
    log.warn("Could not find object of type '" + type + "' with id '" + id + "'.");
  }
}
