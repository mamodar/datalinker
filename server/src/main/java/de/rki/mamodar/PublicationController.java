package de.rki.mamodar;

import de.rki.mamodar.edoc.EdocItemDTO;
import de.rki.mamodar.edoc.EdocRestConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * This controller provides a REST API to create a publication in one of the auxiliary systems. Publications are only
 * created, never read or updated hence only POST is provided.
 *
 * @author Kyanoush Yahosseini
 */
@RestController
@CrossOrigin(origins = "*")
public class PublicationController {

  /**
   * The autowired {@link EdocRestConsumer}.
   */
  @Autowired
  EdocRestConsumer edocRestConsumer;

  /**
   * Create edoc publication.
   *
   * @param item the item
   * @param file the file to publish
   */
  @PostMapping(value = "/publication/edoc", consumes = {"multipart/form-data"})
  void createEdocPublication(
      @RequestPart(value = "item") EdocItemDTO item,
      @RequestPart(value = "file") MultipartFile file) {
    item.setFile(file);
    this.edocRestConsumer.publishToEdoc(item);
  }

}
