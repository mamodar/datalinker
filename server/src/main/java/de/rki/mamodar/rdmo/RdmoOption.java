package de.rki.mamodar.rdmo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rdmo_option")
public class RdmoOption {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", updatable = false, nullable = false)
  Long id;

  private Integer rdmoId;
  private Integer optionset;
  private String uriPrefix;
  private String key;
  private String text;
  private Boolean additionalInput;
  private String textEn;
  private String textDe;

  public RdmoOption() {
  }

  public RdmoOption(RdmoOptionDTO rdmoOptionDTO) {
    this.rdmoId = rdmoOptionDTO.getRdmoId();
    this.optionset = rdmoOptionDTO.getOptionset();
    this.uriPrefix = rdmoOptionDTO.getUriPrefix();
    this.key = rdmoOptionDTO.getKey();
    this.text = rdmoOptionDTO.getText();
    this.additionalInput = rdmoOptionDTO.getAdditionalInput();
    this.textEn = rdmoOptionDTO.getTextEn();
    this.textDe = rdmoOptionDTO.getTextDe();

  }
}
