package de.rki.datalinker.external.rdmo.database;

import de.rki.datalinker.database.ValueRepository;
import de.rki.datalinker.dto.RdmoOptionDTO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This DAO entity corresponding to the representation of a rdmo option in the database. No getters or setters as the
 * table is only used to created the {@link ValueRepository} view.
 *
 * @author Kyanoush Yahosseini
 */
@Entity
@Table(name = "rdmo_option")
public class RdmoOption {

  @Id
  @Column(name = "id", nullable = false)
  private Long rdmoId;
  @Column
  private Integer optionset;
  @Column
  private String uriPrefix;
  @Column
  private String key;
  @Column(length = 1000)
  private String text;
  @Column
  private Boolean additionalInput;
  @Column(length = 1000)
  private String textEn;
  @Column(length = 1000)
  private String textDe;

  /**
   * Instantiates a new empty rdmo option.
   */
  public RdmoOption() {
  }

  /**
   * Instantiates a new rdmo option from a {@link RdmoOptionDTO}
   *
   * @param rdmoOptionDTO the rdmo option dto
   */
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
