package de.rki.mamodar;

public enum Location {
  SAN("WissData"),
  LOCAL("Lokal"),
  OPENBIS("OpenBIS");


  private final String representation;

  Location(String representation) {
    this.representation = representation;
  }

}
