package de.rki.mamodar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This entity corresponds to the representation of a User in the database.
 * A user is created during the first login if a matching LDAP user is found.
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

  public User() {
  }
  public User(String username, String dn) {
    this.username = username;
    this.dn = dn;
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getDn() {
    return dn;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setDn(String dn) {
    this.dn = dn;
  }
}
