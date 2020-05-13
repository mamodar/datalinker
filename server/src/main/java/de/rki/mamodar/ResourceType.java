package de.rki.mamodar;

public enum ResourceType {
  SAN("WissData"),
  LOCAL("Lokal"),
  OPENBIS("OpenBIS"),
  GIT("git"),
  DOI("doi"),
  PRIV("Personal"),
  CLOUD("Cloud");


  private final String representation;

  ResourceType(String representation) {
    this.representation = representation;
  }

}
