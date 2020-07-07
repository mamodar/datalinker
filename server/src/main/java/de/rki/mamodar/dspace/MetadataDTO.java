package de.rki.mamodar.dspace;


import java.time.LocalDate;
import java.util.ArrayList;


/**
 * This class provides a implementation of metadata for a publication. It converts the metadata to a format which is
 * appropriate for the goal instance (e.g. dspace)
 *
 * @author Kyanoush Yahosseini
 */
public class MetadataDTO {

  private String title;
  private String abstractText;
  private ArrayList<String> authors;
  private String description;
  private String issueDate;


  /**
   * Converts the metadata dto to a list of {@link DspaceMetadataDTO}
   *
   * @return the list of {@link DspaceMetadataDTO}
   */
  public ArrayList<DspaceMetadataDTO> toDspaceMetadataList() {
    ArrayList<DspaceMetadataDTO> dspaceMetadata = new ArrayList<>();
    if (title != null) {
      dspaceMetadata.add(new DspaceMetadataDTO("dc.title", title));
    }
    if (abstractText != null) {
      dspaceMetadata.add(new DspaceMetadataDTO("", abstractText));
    }
    if (authors != null) {
      authors.forEach(author -> dspaceMetadata.add(new DspaceMetadataDTO("dc.contributor.author", author)));
    }
    if (description != null) {
      dspaceMetadata.add(new DspaceMetadataDTO("dc.description", description));
    }
    if (issueDate != null) {
      dspaceMetadata.add(new DspaceMetadataDTO("dc.date.issued", LocalDate.parse(issueDate).toString()));
    }
    return dspaceMetadata;
  }

  /**
   * Gets title.
   *
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Gets the abstract.
   *
   * @return the abstract
   */
  public String getAbstractText() {
    return abstractText;
  }

  /**
   * Gets authors.
   *
   * @return the authors
   */
  public ArrayList<String> getAuthors() {
    return authors;
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
   * Gets issue date.
   *
   * @return the issue date
   */
  public String getIssueDate() {
    return issueDate;
  }
}
