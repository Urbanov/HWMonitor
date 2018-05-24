package edu.pw.hwmonitor.companies;

import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RestResource;

public interface CompanyRepository extends Repository<Company, Long> {

    @RestResource
    Company save(Company registration);

    Integer count();
}
