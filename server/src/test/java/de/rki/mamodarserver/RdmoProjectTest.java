package de.rki.mamodarserver;

import de.rki.mamodar.RdmoOwner;
import de.rki.mamodar.RdmoProject;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RdmoProjectTest {
@Test
  void testTrueIsTrue(){
  assertEquals(true,true);
}

@Test
  void testEmptyToProject(){
  RdmoProject emptyRdmoProject = new RdmoProject();
  assertNull(emptyRdmoProject.getId());
  assertEquals(emptyRdmoProject.getOwners().size(),0);
  assertNull(emptyRdmoProject.getTitle());
  assertNull(emptyRdmoProject.toProject().getDescription());
  assertNull(emptyRdmoProject.toProject().getProjectName());
  assertNull(emptyRdmoProject.toProject().getOwner());
  assertNotNull(emptyRdmoProject.toProject().getCreationTimestamp());
  assertNotNull(emptyRdmoProject.toProject().getUpdatedTimestamp());
}

  @Test
  void testToProject(){
    RdmoProject rdmoProject = new RdmoProject();
    RdmoOwner owner = new RdmoOwner();
    owner.setUsername("username");
    ArrayList<RdmoOwner> owners = new ArrayList<>();
    owners.add(owner);

    rdmoProject.setDescription("description");
    rdmoProject.setId(1l);
    rdmoProject.setTitle("title");
    rdmoProject.setOwners(owners);

    assertEquals(rdmoProject.getOwners().size(),1);
    assertEquals(rdmoProject.getOwners().get(0),"username");
    assertEquals(rdmoProject.getTitle(),"title");
    assertEquals(rdmoProject.getDescription(),"description");

    assertEquals(rdmoProject.toProject().getOwner(),"username");
    assertEquals(rdmoProject.toProject().getProjectName(),"title");
    assertEquals(rdmoProject.toProject().getDescription(),"description");
    assertEquals(rdmoProject.toProject().getRdmoId(),1l);


  }
}
