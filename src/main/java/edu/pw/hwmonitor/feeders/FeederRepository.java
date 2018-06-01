package edu.pw.hwmonitor.feeders;

import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

public interface FeederRepository extends Repository<Feeder, Integer> {

    @RestResource
    Feeder save(Feeder feeder);
    Integer count();
    List<Feeder> findAllByCompanyIdEquals(Long companyId);
    Optional<Feeder> findTopByIdEquals(Integer Id);
    Optional<Feeder> findTopBySerialEqualsAndCompanyIdEquals(Integer serial, Long companyId);
}
