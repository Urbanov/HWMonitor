package edu.pw.hwmonitor.user;

import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RestResource;

public interface UserRepository extends Repository<User, String> {

    @RestResource
    User save(User user);
}
