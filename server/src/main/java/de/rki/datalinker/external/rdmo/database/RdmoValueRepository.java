package de.rki.datalinker.external.rdmo.database;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The rdmo value repository extending a JPA Repository.
 *
 * @author Kyanoush Yahosseini
 */
public interface RdmoValueRepository extends JpaRepository<RdmoValue, Long> {


}
