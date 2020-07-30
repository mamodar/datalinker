package de.rki.datalinker;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.stereotype.Component;

/**
 * This component provides a facade for the authentication of a user.
 * @author Kyanoush Yahosseini
 */
@Component
public class AuthenticationFacade  {

  /**
   * Gets the current authentication.
   *
   * @return the authentication
   */
  public Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  /**
   * Gets the current ldap user.
   *
   * @return the ldap user
   */
  public LdapUserDetailsImpl getLdapUser() {
    return (LdapUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}
