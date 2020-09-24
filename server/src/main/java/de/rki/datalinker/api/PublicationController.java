package de.rki.datalinker.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.rki.datalinker.DataLinkerApplication;
import de.rki.datalinker.database.ResourceRepository;
import de.rki.datalinker.dto.PublishedDTO;
import de.rki.datalinker.external.dspace.DspaceApiConsumer;
import de.rki.datalinker.external.dspace.MetadataDTO;
import de.rki.datalinker.external.zenodo.ZenodoApiConsumer;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
  private final Environment env;
  /**
   * The autowired {@link DspaceApiConsumer}.
   */
  @Autowired
  DspaceApiConsumer dspaceApiConsumer;

  /**
   * The Zenodo api consumer.
   */
  @Autowired
  ZenodoApiConsumer zenodoApiConsumer;

  /**
   * Instantiates a new Publication controller.
   *
   * @param env the env
   */
  ResourceRepository resourceRepository;

  public PublicationController(Environment env, ResourceRepository resourceRepository) {
    this.env = env;
    this.resourceRepository = resourceRepository;
  }

  /**
   * Create a item on a dspace instance. Authorize with the dspace server. Attach provided metadata as {@link
   * MetadataDTO}*. Creates two API calls to the dspace server one to create the item and one to provide the metadata.
   *
   * @param metadata the metadata
   * @return the response entity with the handle of the created item
   * @throws JsonProcessingException the json processing exception
   */
  @PostMapping(value = "publications/items/dspace")
  ResponseEntity<String> createDspacePublication(@RequestBody MetadataDTO metadata) throws JsonProcessingException {
    log.info("dspace/publications/items" + metadata.toString());
    try {
      this.dspaceApiConsumer.auth();
      ResponseEntity<String> response = this.dspaceApiConsumer.createItem();
      String itemUUID = this.dspaceApiConsumer.getUuidFromResponse(response);
      this.dspaceApiConsumer.addMetadata(itemUUID, metadata.toDspaceMetadataList());
      return new ResponseEntity<>(new PublishedDTO(this.dspaceApiConsumer.getHandle(response)).toJsonString(),
          HttpStatus.OK);

    } catch (HttpStatusCodeException e) {
      log.warn("dspace error " + e.getStatusCode() + " " + e.getResponseBodyAsString() + " " + e.getResponseHeaders()
          .toString());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  /**
   * Create bitstreams (files) for an item on a dspace instance. Authorize with the dspace server. Calls the API of the
   * dspace server once for each file in the array.
   *
   * @param handle the handle of the item
   * @param files  an array of files
   * @return the response entity with the handle of the item
   * @throws IOException if the bitstream can not be read
   */
  @PostMapping(value = "publications/bitstreams/dspace",
      consumes = {"multipart/form-data"})
  ResponseEntity<String> createDspaceBitstream(
      @RequestParam(value = "item") String handle,
      @RequestPart(value = "file") MultipartFile[] files) throws IOException {
    log.info("dspace/publications/bitstreams");
    try {
      this.dspaceApiConsumer.auth();
      ResponseEntity response = this.dspaceApiConsumer.getItemFromHandle(handle);
      String uuid = this.dspaceApiConsumer.getUuidFromResponse(response);
      this.dspaceApiConsumer.addBitstreams(uuid, files);
      return new ResponseEntity<>(
          new PublishedDTO(env.getProperty("dspace.baseurl") + "/", handle).toJsonString(),
          HttpStatus.OK);
    } catch (HttpStatusCodeException e) {
      log.warn("dspace error " + e.getStatusCode() + " " + e.getResponseBodyAsString() + " " + e.getResponseHeaders()
          .toString());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Create a item on zenodo. Authorize with the server. Attach provided metadata as {@link MetadataDTO}*. Creates one
   * API calls to the zenodo server to create the item and to provide the metadata.
   *
   * @param metadata the metadata
   * @return the response entity with the id of the created item
   * @throws JsonProcessingException the json processing exception
   */
  @PostMapping(value = "publications/items/zenodo")
  ResponseEntity<String> createZenodoPublication(@RequestBody MetadataDTO metadata) throws JsonProcessingException {
    log.info("zenodo/publications/items" + metadata.toString());
    try {
      ResponseEntity<String> response = this.zenodoApiConsumer.createItem(metadata);
      Long itemId = this.zenodoApiConsumer.getId(response);
      return new ResponseEntity<>(new PublishedDTO(itemId.toString()).toJsonString(), HttpStatus.OK);
    } catch (HttpStatusCodeException e) {
      log.warn("zenodo error " + e.getStatusCode() + " " + e.getResponseBodyAsString() + " " + e.getResponseHeaders()
          .toString());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Create bitstreams (files) for an item on zendodo. Calls the API of the zenodo once for each file in the array
   *
   * @param itemId the id of the item
   * @param files  an array of files
   * @return the response entity with the id of the created item
   * @throws IOException if the bitstream can not be read
   */
  @PostMapping(value = "publications/bitstreams/zenodo",
      consumes = {"multipart/form-data"})
  ResponseEntity<String> addZenodoBitstream(@RequestParam(value = "item") String itemId,
      @RequestPart(value = "file") MultipartFile[] files) throws IOException {
    log.info("zenodo/publications/bitstreams");
    try {
      String uuid = zenodoApiConsumer.getBucketUuid(this.zenodoApiConsumer.getItem(Long.parseLong(itemId)));
      this.zenodoApiConsumer.addFiles(uuid, files);
      String doi = this.zenodoApiConsumer.getDoi(this.zenodoApiConsumer.publishItem(Long.parseLong(itemId)));
      return new ResponseEntity<>(
          new PublishedDTO(env.getProperty("zenodo.baseurl") + "/record/", itemId, doi).toJsonString(),
          HttpStatus.OK);
    } catch (HttpStatusCodeException e) {
      log.warn("zenodo error " + e.getStatusCode() + " " + e.getResponseBodyAsString() + " " + e.getResponseHeaders()
          .toString());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
