package de.rki.mamodar.rdmo;

import de.rki.mamodar.Project;
import de.rki.mamodar.ProjectRepository;
import de.rki.mamodar.User;
import de.rki.mamodar.UserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

/**
 * This component saves rdmo data in the corresponding databases. Data is received by calls to the rdmo API via {@link
 * de.rki.mamodar.rdmo.RdmoApiConsumer}. This class converts the DTOs to DAO before saving.
 *
 * @author Kyanoush Yahosseini
 */
@Component
public class RdmoConverter {

  private RdmoQuestionRepository rdmoQuestionRepository;
  private RdmoOptionRepository rdmoOptionRepository;
  private RdmoValueRepository rdmoValueRepository;
  private ProjectRepository projectRepository;
  private UserRepository userRepository;
  /**
   * A list of all attribute ids of rdmo which should be included in the value table of the datalinker
   */
  //private final ArrayList<Long> ALLOWED_RDMO_ATTRIBUTES = new ArrayList<Long>(
  //   Arrays.asList(6L, 158L, 206L));
  private final List<Long> ALLOWED_RDMO_ATTRIBUTES = Stream.iterate(1L, n -> n + 1).limit(300)
      .collect(Collectors.toList());
  private RdmoConverter(RdmoOptionRepository rdmoOptionRepository, RdmoQuestionRepository rdmoQuestionRepository,
      RdmoValueRepository rdmoValueRepository, ProjectRepository projectRepository, UserRepository userRepository) {
    this.rdmoOptionRepository = rdmoOptionRepository;
    this.rdmoQuestionRepository = rdmoQuestionRepository;
    this.rdmoValueRepository = rdmoValueRepository;
    this.projectRepository = projectRepository;
    this.userRepository = userRepository;
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
   * Delete and create therdmo options in the database.
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
    rdmoProjectDTOs.forEach(projectDTO -> projectDAOs.add(new Project(projectDTO)));
    return projectDAOs;
  }

  private void updateProject(Project updatedProject) {
    Optional<Project> project = projectRepository.findByRdmoId(updatedProject.getRdmoId());
    if (project.isPresent()) {
      project.get().update(updatedProject);
      updateUsersForPoject(project.get());
      projectRepository.save(project.get());
    } else {
      updateUsersForPoject(updatedProject);
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


