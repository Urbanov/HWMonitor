package edu.pw.hwmonitor.user;

import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

public interface UserRepository extends Repository<User, String> {

    @RestResource
    User save(User user);
    Optional<User> findByUsernameEquals(String username);
}
