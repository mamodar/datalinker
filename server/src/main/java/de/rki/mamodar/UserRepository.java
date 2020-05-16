package de.rki.mamodar;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The user repository extending a JPA Repository.
 * @author Kyanoush Yahosseini
 */
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * Checks if a user exists by dn.
   *
   * @param Dn the dn representation of a user
   * @return Does the user exists
   */
  boolean existsByDn(String Dn);

  /**
   * Gets a user by dn
   *
   * @param Dn the dn representation of a user
   * @return A user
   */
  User getByDn(String Dn);
}
