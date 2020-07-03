package de.rki.mamodar.database.rdmo;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The rdmo value repository extending a JPA Repository.
 *
 * @author Kyanoush Yahosseini
 */
public interface RdmoValueRepository extends JpaRepository<RdmoValue, Long> {


}
