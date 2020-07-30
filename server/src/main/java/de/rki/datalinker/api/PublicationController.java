package de.rki.datalinker.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.rki.datalinker.DataLinkerApplication;
import de.rki.datalinker.external.dspace.DspaceApiConsumer;
import de.rki.datalinker.external.dspace.DspaceMetadataDTO;
import de.rki.datalinker.external.dspace.MetadataDTO;
import de.rki.datalinker.external.zenodo.ZenodoApiConsumer;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
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

  private static final Logger log = LoggerFactory.getLogger(DataLinkerApplication.class);
  /**
   * The autowired {@link DspaceApiConsumer}.
   */
  @Autowired
  DspaceApiConsumer dspaceApiConsumer;

  @Autowired
  ZenodoApiConsumer zenodoApiConsumer;

  /**
   * Create a item on a dspace instance. Authorize with the dspace server. Attach provided metadata as {@link
   * DspaceMetadataDTO}. Creates two API calls to the dspace server one to create the item and one to provide the
   * metadata.
   *
   * @param metadata the metadata
   * @return the ID (a uuid) of the created item
   */
  @PostMapping(value = "publications/items/dspace")
  ResponseEntity<String> createPublication(@RequestBody MetadataDTO metadata) throws JsonProcessingException {
    log.info("dspace/publications/items" + metadata.toString());
    try {
      this.dspaceApiConsumer.auth();
      ResponseEntity<String> response = this.dspaceApiConsumer.createItem();
      String itemUUID = this.dspaceApiConsumer.getItemId(response);
      this.dspaceApiConsumer.addMetadata(itemUUID, metadata.toDspaceMetadataList());
      return new ResponseEntity<>(new ObjectMapper().writeValueAsString(itemUUID), HttpStatus.OK);

    } catch (HttpStatusCodeException e) {
      log.warn("dspace error " + e.getStatusCode() + " " + e.getResponseBodyAsString() + " " + e.getResponseHeaders()
          .toString());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  /**
   * Create a bitstream (a file) for an item on a dspace instance. Authorize with the dspace server. Calls the API of
   * the dspace server once to upload the file.
   *
   * @param uuid the uuid of the item
   * @param file the file
   * @return the http entity
   * @throws IOException if the bitstream can not be read
   */
  @PostMapping(value = "publications/bitstreams/dspace",
      consumes = {"multipart/form-data"})
  ResponseEntity<String> createPublication(
      @RequestParam(value = "item") String uuid,
      @RequestPart(value = "file") MultipartFile file) throws IOException {
    log.info("dspace/publications/bitstreams");
    try {
      this.dspaceApiConsumer.auth();
      return this.dspaceApiConsumer.addBitstream(uuid, file);
    } catch (HttpStatusCodeException e) {
      log.warn("dspace error " + e.getStatusCode() + " " + e.getResponseBodyAsString() + " " + e.getResponseHeaders()
          .toString());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping(value = "publications/items/zenodo")
  ResponseEntity<String> createZenodoPublication(@RequestBody MetadataDTO metadata) throws JsonProcessingException {
    log.info("zenodo/publications/items" + metadata.toString());
    try {
      ResponseEntity<String> response = this.zenodoApiConsumer.createItem(metadata);
      Long itemId = this.zenodoApiConsumer.getItemId(response);
      return new ResponseEntity<>(itemId.toString(), HttpStatus.OK);
    } catch (HttpStatusCodeException e) {
      log.warn("zenodo error " + e.getStatusCode() + " " + e.getResponseBodyAsString() + " " + e.getResponseHeaders()
          .toString());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping(value = "publications/bitstreams/zenodo",
      consumes = {"multipart/form-data"})
  ResponseEntity<String> addZenodoBitstream(@RequestParam(value = "item") String itemId,
      @RequestPart(value = "file") MultipartFile file) throws IOException {
    log.info("zenodo/publications/bitstreams");
    try {
      String uuid = zenodoApiConsumer.getBucketUuid(this.zenodoApiConsumer.getItem(Long.parseLong(itemId)));
      this.zenodoApiConsumer.addFile(uuid, file);
      this.zenodoApiConsumer.publishItem(Long.parseLong(itemId));
      return new ResponseEntity<>(itemId, HttpStatus.OK);
    } catch (HttpStatusCodeException e) {
      log.warn("zenodo error " + e.getStatusCode() + " " + e.getResponseBodyAsString() + " " + e.getResponseHeaders()
          .toString());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
