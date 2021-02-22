package net.spacedelta.api.security.preauth;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesUserDetailsService;

import javax.servlet.Filter;

// @EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(preAuthenticatedAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(createRequestHeadersPreAuthFilter(),
                        AbstractPreAuthenticatedProcessingFilter.class)
                .authorizeRequests()
                // ensure all require auth
                .anyRequest().authenticated()
                .and()
                // csrf token gen
                .csrf().disable()
                // disable basic
                .httpBasic().disable()
                .sessionManagement().disable()
                .logout().disable()
                .anonymous().disable();
    }

    private Filter createRequestHeadersPreAuthFilter() throws Exception {
        // extract ids and roles
        final RequestHeadersPreAuthFilter preAuthenticationFilter = new RequestHeadersPreAuthFilter();
        preAuthenticationFilter.setAuthenticationDetailsSource(new GrantedAuthoritiesAuthDetailsSource());
        preAuthenticationFilter.setAuthenticationManager(authenticationManager());
        preAuthenticationFilter.setContinueFilterChainOnUnsuccessfulAuthentication(false);

        return preAuthenticationFilter;
    }

    private PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider() {
        final PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        // inject
        provider.setPreAuthenticatedUserDetailsService(
                new PreAuthenticatedGrantedAuthoritiesUserDetailsService()
        );
        return provider;
    }

}
