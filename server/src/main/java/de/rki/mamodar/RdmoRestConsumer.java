package de.rki.mamodar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RdmoRestConsumer {

  private final Environment env;

  public RdmoRestConsumer(Environment env) {
    this.env = env;
  }

  public ArrayList<Project> getProjectsFromRdmo() {
    return rdmoProjectArrayToProjectList(requestRdmoProjects());
  }

  private RdmoProject[] requestRdmoProjects() {
    String rdmoToken = env.getProperty("rdmo.token");
    String rdmoUrl = env.getProperty("rdmo.url");
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.set("Authorization", "Token " + rdmoToken);
    headers.set("Access-Control-Allow-Origin", "*");

    HttpEntity request = new HttpEntity(headers);

    ResponseEntity<RdmoProject[]> response = new RestTemplate().exchange(
        rdmoUrl,
        HttpMethod.GET,
        request,
        RdmoProject[].class
    );
    return response.getBody();
  }


  private ArrayList<Project> rdmoProjectArrayToProjectList(RdmoProject[] rdmoProjects) {
    ArrayList<Project> projects = new ArrayList<>();
    ArrayList<RdmoProject> responseContent = new ArrayList<>(Arrays.asList(rdmoProjects));
    responseContent.forEach(p -> projects.add(p.toProject()));
    return projects;
  }
}
