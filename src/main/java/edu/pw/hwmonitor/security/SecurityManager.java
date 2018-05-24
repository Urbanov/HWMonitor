package edu.pw.hwmonitor.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


public class SecurityManager {

    public boolean isAnonymous() {
        return getUsername().equals("anonymousUser");
    }

    public String getUsername() {
        return org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public ArrayList<String> getRoles() {
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        ArrayList<String> roles = new ArrayList<>();
        Iterator it = authorities.iterator();
        for(int i=0;i<authorities.size();i++) roles.add(it.next().toString());
        return roles;
    }

    public String getRolesAsString()
    {
        String res="";
        ArrayList<String> roles = getRoles();
        for (int i=0;i<roles.size();i++) res+=roles.listIterator(i).next()+", ";
        res = res.substring(0,res.length()-2);
        return res;
    }

    public boolean hasRole(String role) {
        ArrayList<String> roles = getRoles();
        return roles.contains(role);
    }
}
