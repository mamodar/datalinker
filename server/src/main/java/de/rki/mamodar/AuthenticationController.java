package de.rki.mamodar;

import java.security.Principal;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class AuthenticationController {

  private final UserRepository repository;

  public AuthenticationController(UserRepository repository) {
    this.repository = repository;
  }
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
