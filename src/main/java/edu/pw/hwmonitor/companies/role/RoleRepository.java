package edu.pw.hwmonitor.companies.role;

import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RestResource;

public interface RoleRepository extends Repository<Role, Long> {

    @RestResource
    Role save(Role role);
}
