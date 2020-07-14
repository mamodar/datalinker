package de.rki.mamodar.api;

import com.google.gson.Gson;
import de.rki.mamodar.MamodarApplication;
import de.rki.mamodar.dspace.DspaceApiConsumer;
import de.rki.mamodar.dspace.MetadataDTO;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

  private static final Logger log = LoggerFactory.getLogger(MamodarApplication.class);
  private final Gson gson = new Gson();
  /**
   * The autowired {@link DspaceApiConsumer}.
   */
  @Autowired
  DspaceApiConsumer dspaceApiConsumer;

  /**
   * Create a item on a dspace instance. Authorize with the dspace server. Attach provided metadata as {@link
   * de.rki.mamodar.dspace.DspaceMetadataDTO}. Creates two API calls to the dspace server one to create the item and one
   * to provide the metadata.
   *
   * @param metadata the metadata
   * @return the ID (a uuid) of the created item
   */
  @PostMapping(value = "dspace/publications/items")
  HttpEntity<String> createPublication(@RequestBody MetadataDTO metadata) {
    log.info("dspace/publications/items" + metadata.toString());
    this.dspaceApiConsumer.auth();
    HttpEntity<String> response = this.dspaceApiConsumer.createItem();
    Pattern pattern = Pattern.compile("uuid\":\"([\\w-]*)");
    Matcher matcher = pattern.matcher(response.getBody());
    matcher.find();
    this.dspaceApiConsumer.addMetadata(matcher.group(1), metadata.toDspaceMetadataList());
    return new HttpEntity<>(gson.toJson(matcher.group(1)));
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
  @PostMapping(value = "dspace/publications/bitstreams",
      consumes = {"multipart/form-data"})
  HttpEntity<String> createPublication(
      @RequestParam(value = "item") String uuid,
      @RequestPart(value = "file") MultipartFile file) throws IOException {
    log.info("dspace/publications/bitstreams");
    this.dspaceApiConsumer.auth();
    return this.dspaceApiConsumer.addBitstream(uuid, file);
  }

}
