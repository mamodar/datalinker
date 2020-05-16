package de.rki.mamodar;

/**
 * The enum of all possible resource types.
 * @author Kyanoush Yahosseini
 */
public enum ResourceType {
  /**
   * SAN resource type.
   */
  SAN("WissData"),
  /**
   * LOCAL resource type.
   */
  LOCAL("Lokal"),
  /**
   * OPENBIS resource type.
   */
  OPENBIS("OpenBIS"),
  /**
   * GIT resource type.
   */
  GIT("git"),
  /**
   * DOI resource type.
   */
  DOI("doi"),
  /**
   * PRIV resource type.
   */
  PRIV("Personal"),
  /**
   * COUD resource type.
   */
  CLOUD("Cloud");


  private final String representation;

  ResourceType(String representation) {
    this.representation = representation;
  }

}
