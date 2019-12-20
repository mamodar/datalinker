package de.rki.mamodar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class ObjectNotFoundException extends RuntimeException {

  private static final Logger log = LoggerFactory.getLogger(MamodarApplication.class);

  public ObjectNotFoundException(String type, long id) {
    super("Could not find object of type '" + type + "' with id '" + id + "'.");
    log.warn("Could not find object of type '" + type + "' with id '" + id + "'.");
  }
}
