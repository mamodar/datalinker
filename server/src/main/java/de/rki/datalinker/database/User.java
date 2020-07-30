package de.rki.datalinker.database;

import de.rki.datalinker.dto.RdmoOwnerDTO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This DAO entity corresponds to the representation of a User in the database. A user is created during the first login
 * if a matching LDAP user is found.
 *
 * @author Kyanoush Yahosseini
 */
@Entity
@Table(name = "users")
public class User {


  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", updatable = false, nullable = false)
  @Id
  private Long id;

  @Column(name = "username")
  private String username;

  @Column(name = "dn")
  private String dn;

  /**
   * Instantiates a new epty User.
   */
  public User() {
  }

  /**
   * Instantiates a new User.
   *
   * @param username the username
   * @param dn       the dn
   */
  public User(String username, String dn) {
    this.username = username;
    this.dn = dn;
  }

  /**
   * Instantiates a new User from a rdmo owner DTO.
   *
   * @param owner the user
   */
  public User(RdmoOwnerDTO owner) {
    this.username = owner.getUsername();
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Gets username.
   *
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Gets dn.
   *
   * @return the dn
   */
  public String getDn() {
    return dn;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(Long id) {
    this.id = id;
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
   * Sets dn.
   *
   * @param dn the dn
   */
  public void setDn(String dn) {
    this.dn = dn;
  }

}
