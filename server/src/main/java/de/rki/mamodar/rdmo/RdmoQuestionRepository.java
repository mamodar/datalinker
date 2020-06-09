package de.rki.mamodar.rdmo;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The rdmo question repository extending a JPA Repository.
 *
 * @author Kyanoush Yahosseini
 */
public interface RdmoQuestionRepository extends JpaRepository<RdmoQuestion, Long> {

}
