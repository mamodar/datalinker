package de.rki.mamodar.edoc;

import java.io.InputStream;

public class EdocItemDTO {
private String name;
private String email;
private InputStream file;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public InputStream getFile() {
    return file;
  }

  public void setFile(InputStream file) {
    this.file = file;
  }
}
