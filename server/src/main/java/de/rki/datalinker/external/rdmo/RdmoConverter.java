package de.rki.datalinker.external.rdmo;

import de.rki.datalinker.database.HaystackRepository;
import de.rki.datalinker.database.Project;
import de.rki.datalinker.database.ProjectRepository;
import de.rki.datalinker.database.ResourceRepository;
import de.rki.datalinker.database.User;
import de.rki.datalinker.database.UserRepository;
import de.rki.datalinker.database.ValueRepository;
import de.rki.datalinker.dto.RdmoOptionDTO;
import de.rki.datalinker.dto.RdmoProjectDTO;
import de.rki.datalinker.dto.RdmoQuestionDTO;
import de.rki.datalinker.dto.RdmoValueDTO;
import de.rki.datalinker.external.rdmo.database.RdmoOption;
import de.rki.datalinker.external.rdmo.database.RdmoOptionRepository;
import de.rki.datalinker.external.rdmo.database.RdmoQuestion;
import de.rki.datalinker.external.rdmo.database.RdmoQuestionRepository;
import de.rki.datalinker.external.rdmo.database.RdmoValue;
import de.rki.datalinker.external.rdmo.database.RdmoValueRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * This component saves rdmo data in the corresponding databases. Data is received by calls to the rdmo API via {@link
 * RdmoApiConsumer}*. This class converts the DTOs to DAO before saving.
 *
 * @author Kyanoush Yahosseini
 */
@Component
public class RdmoConverter {

  private ValueRepository valueRepository;
  private RdmoQuestionRepository rdmoQuestionRepository;
  private RdmoOptionRepository rdmoOptionRepository;
  private RdmoValueRepository rdmoValueRepository;
  private ProjectRepository projectRepository;
  private UserRepository userRepository;
  private ResourceRepository resourceRepository;
  private HaystackRepository haystackRepository;
  /**
   * A list of all attribute ids of rdmo which should be included in the value table of the datalinker
   */
  private final ArrayList<Long> ALLOWED_RDMO_ATTRIBUTES = new ArrayList<>(
      Arrays.asList(
          9L, // Principal investigator
          12L, // Organizational unit
          138L, // License/ Terms of use
          152L, // Storage location
          173L, // Funding partner
          221L, // Cooperation partner (internal)
          250L, // Keyword
          269L, // Acronym
          270L,// Project type
          274L,// Cooperation partner (external)
          292L, // contact person
          311L));// Title

  private RdmoConverter(
      RdmoOptionRepository rdmoOptionRepository,
      RdmoQuestionRepository rdmoQuestionRepository,
      RdmoValueRepository rdmoValueRepository,
      ProjectRepository projectRepository,
      UserRepository userRepository,
      ValueRepository valueRepository,
      ResourceRepository resourceRepository,
      HaystackRepository haystackRepository) {
    this.rdmoOptionRepository = rdmoOptionRepository;
    this.rdmoQuestionRepository = rdmoQuestionRepository;
    this.rdmoValueRepository = rdmoValueRepository;
    this.projectRepository = projectRepository;
    this.userRepository = userRepository;
    this.valueRepository = valueRepository;
    this.resourceRepository = resourceRepository;
    this.haystackRepository = haystackRepository;
  }

  /**
   * Delete and create the rdmo questions in the database.
   *
   * @param rdmoQuestions the array of rdmo questions
   */
  public void addRdmoQuestions(RdmoQuestionDTO[] rdmoQuestions) {
    this.rdmoQuestionRepository.deleteAll();
    this.rdmoQuestionRepository.saveAll(this.questionsToDAO(rdmoQuestions));
  }

  /**
   * Delete and create the rdmo options in the database.
   *
   * @param rdmoOptions the array of rdmo options
   */
  public void addRdmoOptions(RdmoOptionDTO[] rdmoOptions) {
    this.rdmoOptionRepository.deleteAll();
    this.rdmoOptionRepository.saveAll(this.optionsToDAO(rdmoOptions));
  }

  /**
   * Delete and create the rdmo values in the database. Only values which pass the {@code ALLOWED_RDMO_ATTRIBUTES}
   * filter are saved.
   *
   * @param rdmoValues the array of rdmo values
   */
  public void addRdmoValues(RdmoValueDTO[] rdmoValues) {
    this.rdmoValueRepository.deleteAll();
    this.rdmoValueRepository.saveAll(this.valuesToDAO(rdmoValues));
  }

  /**
   * Update or create the rdmo projects in the database.
   *
   * @param rdmoProjects the array of rdmo projects
   */
  public void updateRdmoProjects(RdmoProjectDTO[] rdmoProjects) {
    this.projectsToDAO(rdmoProjects).forEach(project -> this.updateProject(project));
  }

  private List<RdmoQuestion> questionsToDAO(RdmoQuestionDTO[] rdmoQuestions) {
    List<RdmoQuestionDTO> rdmoQuestionDTOs = Arrays.asList(rdmoQuestions);
    List<RdmoQuestion> rdmoQuestionDAOs = new ArrayList<>();
    rdmoQuestionDTOs.forEach(rdmoQuestionDTO -> rdmoQuestionDAOs.add(new RdmoQuestion(rdmoQuestionDTO)));
    // remove multiple questions with the same attribute id
    HashSet<Long> seen = new HashSet<>();
    rdmoQuestionDAOs.removeIf(rdmoQuestion -> !seen.add(rdmoQuestion.getAttribute()));
    return (rdmoQuestionDAOs);
  }


  private List<RdmoOption> optionsToDAO(RdmoOptionDTO[] rdmoOptions) {
    List<RdmoOptionDTO> rdmoOptionDTOs = Arrays.asList(rdmoOptions);
    List<RdmoOption> rdmoOptionDAOs = new ArrayList<>();
    rdmoOptionDTOs.forEach(rdmoOptionDTO -> rdmoOptionDAOs.add(new RdmoOption(rdmoOptionDTO)));
    return (rdmoOptionDAOs);
  }

  private List<RdmoValue> valuesToDAO(RdmoValueDTO[] rdmoValues) {

    ArrayList<RdmoValueDTO> rdmoValueDTOs = new ArrayList<>(Arrays.asList(rdmoValues));
    ArrayList<RdmoValue> rdmoValueDAOs = new ArrayList<>();
    rdmoValueDTOs.removeIf(rdmoValueDTO -> !ALLOWED_RDMO_ATTRIBUTES.contains(rdmoValueDTO.getAttribute()));
    rdmoValueDTOs.forEach(rdmoValueDTO -> rdmoValueDAOs.add(new RdmoValue(rdmoValueDTO)));
    return (rdmoValueDAOs);
  }

  private List<Project> projectsToDAO(RdmoProjectDTO[] rdmoProjects) {
    List<RdmoProjectDTO> rdmoProjectDTOs = Arrays.asList(rdmoProjects);
    List<Project> projectDAOs = new ArrayList<>();
    rdmoProjectDTOs.forEach(projectDTO -> projectDAOs.add(new Project(projectDTO, valueRepository, resourceRepository,
        haystackRepository)));
    return projectDAOs;
  }

  private void updateProject(Project updatedProject) {
    Optional<Project> project = projectRepository.findByRdmoId(updatedProject.getRdmoId());
    if (project.isPresent()) {
      project.get().update(updatedProject);
      updateUsersForPoject(project.get());
      project.get().setValue(valueRepository.getByProjectRdmoId(project.get().getRdmoId()));
      project.get().setHaystack(haystackRepository.findByRdmoId(project.get().getRdmoId()));
      projectRepository.save(project.get());
    } else {
      updateUsersForPoject(updatedProject);
      updatedProject.setValue(valueRepository.getByProjectRdmoId(updatedProject.getRdmoId()));
      updatedProject.setHaystack(haystackRepository.findByRdmoId(updatedProject.getRdmoId()));
      projectRepository.save(updatedProject);
    }
  }

  private void updateUsersForPoject(Project updatedProject) {
    ArrayList<User> toRemoveUsers = new ArrayList<>();
    ArrayList<User> toAddUsers = new ArrayList<>();

    for (User updatedUser : updatedProject.getOwner()) {
      if (!userRepository.existsByUsername(updatedUser.getUsername())) {
        userRepository.save(updatedUser);
      } else {
        User replaceUpdatedByExistingUser = userRepository.getByUsername(updatedUser.getUsername());
        toRemoveUsers.addAll(updatedProject.findDuplicatesByUsername(replaceUpdatedByExistingUser));
        toAddUsers.add(replaceUpdatedByExistingUser);
        userRepository.save(replaceUpdatedByExistingUser);
      }
    }
    updatedProject.getOwner().removeAll(toRemoveUsers);
    updatedProject.getOwner().addAll(toAddUsers);
    userRepository.flush();
  }
}


