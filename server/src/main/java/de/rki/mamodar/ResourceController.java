package de.rki.mamodar;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class ResourceController {

  private static final Logger log = LoggerFactory.getLogger(MamodarApplication.class);
  private final ResourceRepository repository;

  public ResourceController(ResourceRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/resources/")
  List<Resource> all() {

    log.info("GET: /resources/");
    return repository.findAll();
  }



  @GetMapping("/resources/{id}")
  Resource one(@PathVariable Long id) {
    log.info("GET: /resources/id");
    return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("resource", id));

  }


  @PostMapping("/resources/")
  Resource addResource() {
    log.info("POST: /resources/");
    Resource newResource = new Resource();
    newResource.setCreationTimestamp(new Date());
    repository.save(newResource);
    return newResource;
  }
  @PutMapping("/resources/{id}")
  Resource updateOne(@PathVariable Long id, @RequestBody @NotNull Resource updatedResource) {
    log.info("PUT: /resources/id");
    Resource resource = repository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("resource", id));
    resource.setDescription(updatedResource.getDescription());
    resource.setLocation(updatedResource.getLocation());
    resource.setPath(updatedResource.getPath());
    resource.setCreationTimestamp(new Date());
    repository.save(resource);
    return resource;

  }


  @DeleteMapping("/resources/{id}")
  void removeLink(@PathVariable Long id) {
    log.info("DELETE: /resources/id");
    Resource resource = repository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("resource", id));
    repository.delete(resource);
  }
}
