package de.rki.mamodar;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByDn(String Dn);
  User getByDn(String Dn);
}
