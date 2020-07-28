package de.rki.mamodar.dspace;


import de.rki.mamodar.zenodo.ZenodoMetdatataDTO;
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
  private ArrayList<String> keywords;
  private String description;
  private String issueDate;
  private String license;

  private String uploadType;
  private String accessRight;

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
    if (keywords != null) {
      keywords.forEach(keyword -> dspaceMetadata.add(new DspaceMetadataDTO("dc.subject", keyword)));
    }
    if (description != null) {
      dspaceMetadata.add(new DspaceMetadataDTO("dc.description", description));
    }
    if (issueDate != null) {
      dspaceMetadata.add(new DspaceMetadataDTO("dc.date.issued", LocalDate.parse(issueDate).toString()));
    }
    if (license != null) {
      dspaceMetadata.add(new DspaceMetadataDTO("dc.rights", license));
    }
    return dspaceMetadata;
  }

  public ZenodoMetdatataDTO toZenodoMetadataList() {
    ZenodoMetdatataDTO zenodoMetdatata = new ZenodoMetdatataDTO();
    ArrayList<ZenodoMetdatataDTO> metadata = new ArrayList<>();
    if (title != null) {
      zenodoMetdatata.setTitle(title);
    }
    if (abstractText != null) {
      zenodoMetdatata.setDescription(abstractText);
    }
    if (authors != null) {
      authors.forEach(author -> zenodoMetdatata.addCreator(author));
    }
    if (keywords != null) {
      keywords.forEach(keyword -> zenodoMetdatata.getKeywords().add(keyword));
    }
    if (description != null) {
      zenodoMetdatata.setDescription(description);
    }
    if (issueDate != null) {
      zenodoMetdatata.setPublicationDate(LocalDate.parse(issueDate).toString());
    }
    //TODO KY wire correct license
    metadata.add(zenodoMetdatata);
    return zenodoMetdatata;

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

  public ArrayList<String> getKeywords() {
    return keywords;
  }

  public String getLicense() {
    return license;
  }
}
