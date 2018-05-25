package edu.pw.hwmonitor.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public class SecurityManager {

    public String getUsername() {
        return org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public List<String> getRoles() {
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        ArrayList<String> roles = new ArrayList<>();
        Iterator it = authorities.iterator();
        for(int i=0;i<authorities.size();i++) roles.add(it.next().toString());
        return roles;
    }

    public String getRolesAsString()
    {
        String res="";
        List<String> roles = getRoles();
        for (int i=0;i<roles.size();i++) res+=roles.listIterator(i).next()+", ";
        res = res.substring(0,res.length()-2);
        return res;
    }

    public boolean hasRole(String role) {
        List<String> roles = getRoles();
        return roles.contains(role);
    }
}
