package de.rki.mamodar;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  private List<Resource> resources;

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

  public List<Resource> getResources() {
    return resources;
  }

  public void setResources(List<Resource> resources) {
    this.resources = resources;
  }
}
