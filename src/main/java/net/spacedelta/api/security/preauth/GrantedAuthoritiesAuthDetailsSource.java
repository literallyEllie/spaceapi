package net.spacedelta.api.security.preauth;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GrantedAuthoritiesAuthDetailsSource implements
        AuthenticationDetailsSource<HttpServletRequest, PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails> {

    @Override
    public PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails buildDetails(HttpServletRequest context) {
        final String userRoles = context.getHeader("user_roles");
        final List<GrantedAuthority> authorities = StringUtils.isBlank(userRoles)
                ? Lists.newArrayList()
                : getAuthorities(userRoles);

        return new PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails(context, authorities);
    }

    private List<GrantedAuthority> getAuthorities(String roles) {
        return Set.of(roles.split(","))
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
