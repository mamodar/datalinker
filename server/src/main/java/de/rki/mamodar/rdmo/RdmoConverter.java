package de.rki.mamodar.rdmo;

import de.rki.mamodar.Project;
import de.rki.mamodar.ProjectRepository;
import de.rki.mamodar.User;
import de.rki.mamodar.UserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RdmoConverter {

  @Autowired
  RdmoApiConsumer rdmoApiConsumer;

  RdmoQuestionRepository rdmoQuestionRepository;
  RdmoOptionRepository rdmoOptionRepository;
  RdmoValueRepository rdmoValueRepository;
  ProjectRepository projectRepository;
  UserRepository userRepository;

  private RdmoConverter(RdmoOptionRepository rdmoOptionRepository,
      RdmoQuestionRepository rdmoQuestionRepository,
      RdmoValueRepository rdmoValueRepository, ProjectRepository projectRepository, UserRepository userRepository) {
    this.rdmoOptionRepository = rdmoOptionRepository;
    this.rdmoQuestionRepository = rdmoQuestionRepository;
    this.rdmoValueRepository = rdmoValueRepository;
    this.projectRepository = projectRepository;
    this.userRepository = userRepository;
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
    List<RdmoValueDTO> rdmoValueDTOs = Arrays.asList(rdmoValues);
    List<RdmoValue> rdmoValueDAOs = new ArrayList<>();
    rdmoValueDTOs.forEach(rdmoValueDTO -> rdmoValueDAOs.add(new RdmoValue(rdmoValueDTO)));
    return (rdmoValueDAOs);
  }

  private List<Project> projectsToDAO(RdmoProjectDTO[] rdmoProjects) {
    List<RdmoProjectDTO> rdmoProjectDTOs = Arrays.asList(rdmoProjects);
    List<Project> projectDAOs = new ArrayList<>();
    rdmoProjectDTOs.forEach(projectDTO -> projectDAOs.add(projectDTO.toProject()));
    return projectDAOs;
  }

  public void addRdmoQuestions(RdmoQuestionDTO[] rdmoQuestions) {
    this.rdmoQuestionRepository.deleteAll();
    this.rdmoQuestionRepository.saveAll(this.questionsToDAO(rdmoQuestions));
  }

  public void addRdmoOptions(RdmoOptionDTO[] rdmoOptions) {
    this.rdmoOptionRepository.deleteAll();
    this.rdmoOptionRepository.saveAll(this.optionsToDAO(rdmoOptions));
  }

  public void addRdmoValues(RdmoValueDTO[] rdmoValues) {
    this.rdmoValueRepository.deleteAll();
    this.rdmoValueRepository.saveAll(this.valuesToDAO(rdmoValues));
  }

  public void updateRdmoProjects(RdmoProjectDTO[] rdmoProjects) {
    this.projectsToDAO(rdmoProjects).forEach(project -> this.updateProject(project));
  }


  private void updateProject(Project updatedProject) {
    Optional<Project> project = projectRepository.findByRdmoId(updatedProject.getRdmoId());
    if (project.isPresent()) {
      project.get().setProjectName(updatedProject.getProjectName());
      project.get().setDescription(updatedProject.getDescription());
      updateUsersForPoject(updatedProject);
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


