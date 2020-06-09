package de.rki.mamodar.rdmo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.rki.mamodar.User;

/**
 * This class provides the representation of a RDMO project owner. This should be replaced by a correct LDAP
 * implementation.
 *
 * @author Kyanoush Yahosseini
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RdmoOwnerDTO {

  private String username;
  private String email;

  /**
   * Gets username.
   *
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets username.
   *
   * @param username the username
   */
  public void setUsername(String username) {
    this.username = username;
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
   * To user user.
   *
   * @return the user
   */
  public User toUser() {
    User user = new User();
    user.setUsername(this.getUsername());

    return user;
  }
}
