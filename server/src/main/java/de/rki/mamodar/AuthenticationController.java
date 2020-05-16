package de.rki.mamodar;

import java.security.Principal;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller provides a REST API to check if a provided username and password match.
 *
 * @author Kyanoush Yahosseini
 */
@RestController
@CrossOrigin(origins = "*")
public class AuthenticationController {

  private final UserRepository repository;

  /**
   * Instantiates a new Authentication controller.
   *
   * @param repository the user repository
   */
  public AuthenticationController(UserRepository repository) {
    this.repository = repository;
  }

  /**
   * Gets the authentication principal for the current user as idendified by API call (see {@link de.rki.mamodar.WebSecurityConfig})
   *
   * @param user the principal user
   * @return the principal
   */
  @GetMapping("/user")
  Principal auth(Principal user) {
    LdapUserDetailsImpl ldapUser =
        ((LdapUserDetailsImpl)((UsernamePasswordAuthenticationToken) user).getPrincipal());

    if(!repository.existsByDn(ldapUser.getDn())) {
      repository.save(new User(ldapUser.getUsername(), ldapUser.getDn()));
    }
    return user;
  }

}
