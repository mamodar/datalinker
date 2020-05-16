package de.rki.mamodar;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The resource repository extending a JPA Repository.
 * @author Kyanoush Yahosseini
 */
public interface ResourceRepository extends JpaRepository<Resource, Long> {

}
