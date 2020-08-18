package de.rki.datalinker.api;

import de.rki.datalinker.database.ValueRepository;
import de.rki.datalinker.dto.ValueDTO;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller provides a REST API to read values.
 *
 * @author Kyanoush Yahosseini
 */
@RestController
@CrossOrigin(origins = "*")
public class ValueController {

  private final ValueRepository repository;

  /**
   * Instantiates a new Value controller.
   *
   * @param repository the repository
   */
  public ValueController(ValueRepository repository) {
    this.repository = repository;
  }

  /**
   * Get all values as an array list and converts them to DTOs.
   *
   * @return the array list
   */
  @GetMapping("/values")
  public ArrayList<ValueDTO> getAllValues() {
    ArrayList<ValueDTO> valueDTOs = new ArrayList<>();
    repository.findAll().forEach(value -> {
      if (value != null) {
        valueDTOs.add(new ValueDTO(value));
      }
    });
    valueDTOs.removeIf(valueDTO -> valueDTO.getAnswerText().isBlank());
    valueDTOs.removeIf(valueDTO -> valueDTO.getQuestionText() == null);
    return valueDTOs;
  }

}
