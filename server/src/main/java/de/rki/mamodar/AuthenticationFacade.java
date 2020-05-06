package de.rki.mamodar;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade  {

  public Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  public LdapUserDetailsImpl getLdapUser() {
    return (LdapUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}
