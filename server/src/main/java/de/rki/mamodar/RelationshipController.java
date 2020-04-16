package de.rki.mamodar;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class RelationshipController {

  private static final Logger log = LoggerFactory.getLogger(MamodarApplication.class);
  private final RelationshipRepository repository;

  public RelationshipController(RelationshipRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/relationships/")
  Collection<Relationship> allFilter(@RequestParam(name = "project", required = false) Optional<Long> projectId,
      @RequestParam(name = "resource",required = false) Optional<Long> resourceId) {
    log.info("GET: /relationships/" );
    if(resourceId.isEmpty() && projectId.isPresent()){
    return repository.findRelationshipsByProject_Id(projectId.get());
    }
    if(projectId.isEmpty() && resourceId.isPresent()){
      return repository.findRelationshipsByResource_Id(resourceId.get());
    }
    if(projectId.isPresent() && resourceId.isPresent()){
      return repository.findRelationshipsByResource_IdAndProject_Id(resourceId.get(),projectId.get());
    }
    return repository.findAll();
  }


  @PostMapping("/relationships/")
  Relationship createEmptyProjectResource(){
    log.info("POST: /relationships/");
    Relationship newRelationship = new Relationship();
    newRelationship.setCreationTimestamp(new Date());
    repository.save(newRelationship);
    log.info(String.valueOf(newRelationship.creationTimestamp));
    return(newRelationship);
  }

  @PutMapping("/relationships/{id}")
  Relationship addLink(@PathVariable Long id, @RequestBody @NotNull Relationship updatedRelationship) {
    log.info("PUT: /relationships/");
    Relationship relationship = repository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("relationship", id));
    relationship.setProject(updatedRelationship.getProject());
    relationship.setResource(updatedRelationship.getResource());
    relationship.setCreationTimestamp(new Date());
    repository.save(relationship);
    return relationship;
  }


  @DeleteMapping("/relationships/{id}")
  void removeLink(@PathVariable Long id) {
    log.info("DELETE: /relationships/id");
    Relationship relationship = repository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("relationship", id));
    repository.delete(relationship);
  }

}
