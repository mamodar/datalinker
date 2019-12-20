package de.rki.mamodar;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ResourceController {

  private static final Logger log = LoggerFactory.getLogger(MamodarApplication.class);
  private final ResourceRepository repository;

  public ResourceController(ResourceRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/resources/")
  List<Resource> all() {
    return repository.findAll();
  }

  @PostMapping("/resources/")
  Resource addResource(@RequestBody Resource resource) {
    resource.setCreationTimestamp(new Date());
    repository.save(resource);
    return resource;
  }

  @GetMapping("/resources/{id}")
  Resource one(@PathVariable Long id) {

    return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("resource", id));

  }

  @PutMapping("/resources/{id}")
  Resource updateOne(@PathVariable Long id, @RequestBody Resource updatedResource) {
    Resource resource = repository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("resource", id));
    resource.setDescription(updatedResource.getDescription());
    resource.setLocation(updatedResource.getLocation());
    resource.setPath(updatedResource.getPath());
    resource.setCreationTimestamp(new Date());
    return resource;

  }


}
