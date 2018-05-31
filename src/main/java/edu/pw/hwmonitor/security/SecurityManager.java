package edu.pw.hwmonitor.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class SecurityManager {

    public String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public List<String> getRoles() {
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        ArrayList<String> roles = new ArrayList<>();
        for(SimpleGrantedAuthority a : authorities) roles.add(a.toString());
        return roles;
    }
}
