package de.rki.mamodar;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ProjectSpecification implements Specification<Project> {

  private SearchCriteria criteria;

  public ProjectSpecification(SearchCriteria searchCriteria) {
    this.criteria = searchCriteria;
  }

  @Override
  public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    if (criteria.getOperation().equalsIgnoreCase(":")) {
      Join<Project, Value> projectValueJoin = root.join("value");
      Predicate builderKey = builder.like(projectValueJoin.get("questionText"), "%" + criteria.getKey() + "%");
      Predicate builderValueAnswer = builder
          .like(builder.lower(projectValueJoin.get("answer")), "%" + criteria.getValue().toLowerCase() + "%");
      Predicate builderValueOption = builder
          .like(builder.lower(projectValueJoin.get("optionText")), "%" + criteria.getValue().toLowerCase() + "%");
      return builder.and(builderKey, builder.or(builderValueAnswer, builderValueOption));
    } else {
      throw new ComparisonNotSupportedException(criteria.getOperation());
    }
  }
}
