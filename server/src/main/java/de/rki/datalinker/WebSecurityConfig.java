package de.rki.datalinker;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * The class configures spring web security.
 * @author Kyanoush Yahosseini
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  /**
   * Configures the {@link org.springframework.security.config.annotation.web.builders.HttpSecurity} class.
   * @param http The autowired HttpSecurity
   * @throws Exception if the configuration is invalid
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic().and().
        authorizeRequests().anyRequest().fullyAuthenticated();
    http.cors();
    http.csrf().disable();
    http.formLogin().disable();
  }

  /**
   * Configures the {@link org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder}
   * @param auth The autowired AuthenticationManagerBuilder
   * @throws Exception if the configuration is invalid
   */
  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .ldapAuthentication()
        .userDnPatterns("uid={0},ou=people")
        .groupSearchBase("ou=groups")
        .contextSource()
        // TODO why do we need to set the values here and in applicationproperties?
        .url("ldap://localhost:8389/dc=springframework,dc=org")
        .and()
        .passwordCompare()
        .passwordAttribute("userPassword");
  }
}
