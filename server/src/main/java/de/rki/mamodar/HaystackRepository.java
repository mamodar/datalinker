package de.rki.mamodar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface HaystackRepository extends JpaRepository<Haystack, Long> {

 Haystack findByRdmoId(@Param("rdmo_id") Long rdmoId);
}
