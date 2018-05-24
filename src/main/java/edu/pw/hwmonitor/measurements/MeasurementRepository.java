package edu.pw.hwmonitor.measurements;

import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MeasurementRepository extends Repository<Measurement, Long> {

    @RestResource
    Measurement save(Measurement registration);

    Integer count();

    Optional<Measurement> findFirstById(Long id);
}
