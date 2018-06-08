package edu.pw.hwmonitor.controllers;

import com.google.common.collect.ImmutableMap;
import edu.pw.hwmonitor.companies.Company;
import edu.pw.hwmonitor.companies.CompanyRepository;
import edu.pw.hwmonitor.controllers.requests.DataRequest;
import edu.pw.hwmonitor.feeders.Feeder;
import edu.pw.hwmonitor.feeders.FeederRepository;
import edu.pw.hwmonitor.measurements.Measurement;
import edu.pw.hwmonitor.measurements.MeasurementRepository;
import edu.pw.hwmonitor.security.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    public UserController(SecurityManager securityManager, CompanyRepository companyRepository, FeederRepository feederRepository, MeasurementRepository measurementRepository) {
        this.securityManager = securityManager;
        this.companyRepository = companyRepository;
        this.feederRepository = feederRepository;
        this.measurementRepository = measurementRepository;
    }

    private SecurityManager securityManager;
    private CompanyRepository companyRepository;
    private FeederRepository feederRepository;
    private MeasurementRepository measurementRepository;

    @GetMapping("/user/feeder-metadata")
    public List<Feeder> getfeederMetadata() {
        Company company = getUserAuthorizations();
        return new ArrayList<>(feederRepository.findAllByCompanyIdEquals(company.getCompanyId()));
    }

    @PostMapping("/user/feeder-measurements")
    public ResponseEntity<List<ImmutableMap<String, String>>> feederMeasurements(@RequestBody DataRequest dataRequest) {
        Company company = getUserAuthorizations();
        Optional<Feeder> optionalFeeder = feederRepository.findTopBySerialEqualsAndCompanyIdEquals(dataRequest.getSerial(), company.getCompanyId());

        if (optionalFeeder.isPresent()) {
            List<Measurement> measurements = measurementRepository.findAllByFeederIdEqualsAndTimeIsGreaterThanAndTimeIsLessThanOrderByTimeAsc(
                optionalFeeder.get().getId(), dataRequest.getBegin(), dataRequest.getEnd()
            );
            return new ResponseEntity<>(measurements.stream()
                .map(measurment -> ImmutableMap.of(
                    "serial", optionalFeeder.get().getSerial().toString(),
                    "timestamp", measurment.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    "value", measurment.getValue().toString()
                ))
                .collect(Collectors.toList()), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/user/create-feeder")
    public ResponseEntity<HttpStatus> createFeeder(@RequestBody Feeder feeder) {
        Company company = getUserAuthorizations();
        feeder.setCompanyId(company.getCompanyId());

        if (feederRepository.findTopBySerialEqualsAndCompanyIdEquals(feeder.getSerial(), company.getCompanyId()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        feederRepository.save(feeder);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Company getUserAuthorizations() {
        List<Company> companies = new ArrayList<>();
        List<String> roles = securityManager.getRoles();
        Optional<Company> company;
        for (String role : roles) {
            company = companyRepository.findTopByRoleEquals(role);
            company.ifPresent(companies::add);
        }
        return companies.get(0);
    }
}
