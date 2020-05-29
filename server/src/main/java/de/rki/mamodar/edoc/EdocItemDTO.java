package de.rki.mamodar.edoc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.IOException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

/**
 * This class provides a DTO for receiving and sending a binary file and metadata to an edoc instance.
 *
 * @author Kyanoush Yahosseini
 */
public class EdocItemDTO {

  private String name;
  private String email;
  private String description;
  // we always send/receive the file in a separate part
  @JsonIgnore
  private ByteArrayResource file;


  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets email.
   *
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets email.
   *
   * @param email the email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets file.
   *
   * @return the file
   */
  public ByteArrayResource getFile() {
    return file;
  }


  /**
   * Sets the binary file of the EdocItemDTO by converting a byte array .
   *
   * @param file the file
   */
  public void setFile(byte[] file) {
    this.file = new ByteArrayResource(file);
  }

  /**
   * Sets the binary file of the EdocItemDTO by converting a MultipartFile.
   *
   * @param file the binary file
   */
  public void setFile(MultipartFile file) {
    try {
      this.file = new ByteArrayResource(file.getBytes()) {
        @Override
        public String getFilename() {
          return file.getName(); // Filename has to be returned in order to be able to post.
        }
      };

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Gets description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets description.
   *
   * @param description the description
   */
  public void setDescription(String description) {
    this.description = description;
  }
}
