package de.rki.mamodar;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.data.jpa.domain.Specification;

public class ProjectSpecificationsBuilder {

  private final ArrayList<SearchCriteria> params;

  public ProjectSpecificationsBuilder() {
    params = new ArrayList<>();
  }

  public ProjectSpecificationsBuilder with(String key, String operation, String value) {
    params.add(new SearchCriteria(key, operation, value));
    return this;
  }

  public Specification<Project> build() {
    if (params.size() == 0) {
      return null;
    }
    Specification<Project> result;
    result = null;
    for (SearchCriteria searchCriteria : params) {
      if (result == null) {
        result = Specification.where((new ProjectSpecification(searchCriteria)));
      } else {
        result = result.and(new ProjectSpecification(searchCriteria));
      }
    }
    return result;
  }

  public void parse(String filter) {

    Pattern pattern = Pattern.compile("([^:,]+?)(:)([^:,]+?),", Pattern.UNICODE_CHARACTER_CLASS);
    Matcher matcher = pattern.matcher(filter + ",");
    while (matcher.find()) {
      this.with(matcher.group(1), matcher.group(2), matcher.group(3));
    }
  }
}
