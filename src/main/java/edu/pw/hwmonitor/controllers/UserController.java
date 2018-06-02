package edu.pw.hwmonitor.controllers;

import com.google.common.collect.ImmutableMap;
import edu.pw.hwmonitor.companies.Company;
import edu.pw.hwmonitor.companies.CompanyRepository;
import edu.pw.hwmonitor.feeders.Feeder;
import edu.pw.hwmonitor.feeders.FeederRepository;
import edu.pw.hwmonitor.measurements.Measurement;
import edu.pw.hwmonitor.measurements.MeasurementRepository;
import edu.pw.hwmonitor.security.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class UserController {
    @Autowired
    public UserController(SecurityManager securityManager, CompanyRepository companyRepository, FeederRepository feederRepository, MeasurementRepository measurementRepository) {
        this.securityManager=securityManager;
        this.companyRepository=companyRepository;
        this.feederRepository=feederRepository;
        this.measurementRepository=measurementRepository;
    }

    private SecurityManager securityManager;
    private CompanyRepository companyRepository;
    private FeederRepository feederRepository;
    private MeasurementRepository measurementRepository;


    @GetMapping("/user/feederMetadata")
    public List<ImmutableMap<String, String>> getfeederMetadata() {
        List<Company> companies = companyAuthorizations();
        if(companies.size()==0) return new ArrayList<ImmutableMap<String, String>>(){};
        List<Feeder> feeders;
        List<ImmutableMap<String, String>> result = new ArrayList<>();
        for (Company c : companies) {
            result.add(ImmutableMap.of(
                    "x", "1",
                    "serial", c.getId().toString(),
                    "company_id",c.getName(),
                    "desc", c.getRole()
                    ));

            feeders = feederRepository.findAllByCompanyIdEquals(c.getId());
            for(Feeder f : feeders) {
                result.add(ImmutableMap.of(
                        "x", "0",
                        "serial", f.getSerial().toString(),
                        "company_id",f.getCompanyId().toString(),
                        "desc", f.getDescription()));
            }
        }
        return result;
    }

    @PostMapping("/user/feederMeasurements")
    public ResponseEntity<List<ImmutableMap<String, String>>> feederMeasurements(@RequestBody DataRequest dataRequest) {
        if(!isFeederAllowed(dataRequest.getCompanyId())) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Optional<Feeder> feederOptional = feederRepository.findTopBySerialEqualsAndCompanyIdEquals(dataRequest.getSerial(),dataRequest.getCompanyId());
        if(feederOptional.isPresent()) {
            Feeder feeder = feederOptional.get();

            List<Measurement> measurements = measurementRepository.
                    findAllByFeederIdEqualsAndTimeIsGreaterThanAndTimeIsLessThanOrderByTimeDesc(feeder.getId(), dataRequest.getTimel(), dataRequest.getTimeh());

            if(measurements.size()==0) return new ResponseEntity<>(HttpStatus.OK);
            Iterator<Measurement> it = measurements.iterator();
            return new ResponseEntity<>(Stream.iterate(it.next(), measurement -> it.next())
                    .limit(measurements.size())
                    .map(measurement -> ImmutableMap.of(
                            "feederSerial", feederRepository.findTopByIdEquals(measurement.getFeederId()).get().getSerial().toString(),
                            "time", measurement.getTime().toString(),
                            "value", measurement.getValue().toString()
                    ))
                    .collect(Collectors.toList()),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/user/create-feeder")
    public ResponseEntity<HttpStatus> createFeeder(@RequestBody Feeder feeder) {
        Company company = companyAuthorizations().get(0);
        feeder.setCompanyId(company.getId());

        if (feederRepository.findTopBySerialEqualsAndCompanyIdEquals(feeder.getSerial(), company.getId()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
        }

        feederRepository.save(feeder);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private List<Company> companyAuthorizations() {
        List<Company> companies = new ArrayList<>();
        List<String> roles = securityManager.getRoles();
        Optional<Company> c;
        for (String role : roles) {
            c = companyRepository.findTopByRoleEquals(role);
            if(c.isPresent()) companies.add(c.get());
        }
        return companies;
    }

    private boolean isFeederAllowed(long companyId) {
        List<Long> ids = new ArrayList<>();
        List<Company> companies = companyAuthorizations();
        for (Company c : companies){
                ids.add(c.getId());
        }
        return ids.contains(companyId);
    }
}
