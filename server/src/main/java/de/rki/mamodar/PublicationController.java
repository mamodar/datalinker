package de.rki.mamodar;

import de.rki.mamodar.edoc.EdocItemDTO;
import java.io.IOException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * This controller provides a REST API to create a publication in one of the auxiliary systems. Publications are only
 * created, never read or updated hence only POST is provided.
 *
 * @author Kyanoush Yahosseini
 */
@RestController
@CrossOrigin(origins = "*")
public class PublicationController {

  @GetMapping("/publication")
  boolean up(){
    return true;
  }
  @PostMapping("/publication/edoc")
  boolean createEdocPublication(
      @RequestParam MultipartFile file,
      @RequestParam ProjectSendDTO project,
      ModelMap modelMap) throws IOException {
    EdocItemDTO edocItem = new EdocItemDTO();
    edocItem.setEmail(project.getOwner().get(0));
    edocItem.setName(project.getProjectName());
    edocItem.setFile(file.getInputStream());
    return true;
  }

}
