package de.rki.mamodar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

}
