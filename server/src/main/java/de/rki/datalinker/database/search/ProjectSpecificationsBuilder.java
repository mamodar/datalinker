package de.rki.datalinker.database.search;

import de.rki.datalinker.database.Project;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.data.jpa.domain.Specification;

/**
 * This class helps to build a {@link ProjectSpecification}
 */
public class ProjectSpecificationsBuilder {

  private final ArrayList<SearchCriteria> params;

  /**
   * Instantiates a new Project specifications builder.
   */
  public ProjectSpecificationsBuilder() {
    params = new ArrayList<>();
  }

  /**
   * Adds a new key, operation, value triplet as a {@link SearchCriteria} to the class.
   *
   * @param key       the key
   * @param operation the operation
   * @param value     the value
   * @return the project specifications builder
   */
  public ProjectSpecificationsBuilder with(String key, String operation, String value) {
    params.add(new SearchCriteria(key, operation, value));
    return this;
  }

  /**
   * Build specification by taking all {@link SearchCriteria} added to the class
   *
   * @return the specification
   */
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

  /**
   * Parses a string representing the filters and creates {@link SearchCriteria}s. A filter is a string of form
   * key0:value0,key1:value1,...
   *
   * @param filter the filter
   */
  public void parse(String filter) {
    // anything except :, is allowed for key and value.
    Pattern pattern = Pattern.compile("([^:,]+?)(:)([^:,]+?),", Pattern.UNICODE_CHARACTER_CLASS);
    Matcher matcher = pattern.matcher(filter + ",");
    while (matcher.find()) {
      this.with(matcher.group(1), matcher.group(2), matcher.group(3));
    }
  }
}
