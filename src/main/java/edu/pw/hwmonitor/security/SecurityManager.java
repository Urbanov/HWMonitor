package edu.pw.hwmonitor.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SecurityManager {

    public String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public List<String> getRoles() {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        ArrayList<String> roles = new ArrayList<>();
        for (GrantedAuthority auth : authorities) {
            roles.add(auth.toString());
        }
        return roles;
    }
}
