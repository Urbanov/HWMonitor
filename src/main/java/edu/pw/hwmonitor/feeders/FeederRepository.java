package edu.pw.hwmonitor.feeders;

import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RestResource;

public interface FeederRepository extends Repository<Feeder, Long> {

    @RestResource
    Feeder save(Feeder registration);

    Integer count();
}
