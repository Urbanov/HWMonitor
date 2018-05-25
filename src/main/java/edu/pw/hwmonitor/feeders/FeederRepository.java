package edu.pw.hwmonitor.feeders;

import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

public interface FeederRepository extends Repository<Feeder, Long> {

    @RestResource
    Feeder save(Feeder registration);

    Integer count();

    List<Feeder> findAllByCompanyIdEquals(Long companyId);
    Optional<Feeder> findTopByIdEquals(Long Id);
}
