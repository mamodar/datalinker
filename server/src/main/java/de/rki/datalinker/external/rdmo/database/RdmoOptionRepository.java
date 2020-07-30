package de.rki.datalinker.external.rdmo.database;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The rdmo option repository extending a JPA Repository.
 *
 * @author Kyanoush Yahosseini
 */
public interface RdmoOptionRepository extends JpaRepository<RdmoOption, Long> {

}
