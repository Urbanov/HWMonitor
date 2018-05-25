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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class RequestController {
    @Autowired
    RequestController(SecurityManager securityManager, CompanyRepository companyRepository, FeederRepository feederRepository, MeasurementRepository measurementRepository) {
        this.securityManager=securityManager;
        this.companyRepository=companyRepository;
        this.feederRepository=feederRepository;
        this.measurementRepository=measurementRepository;
    }

    private SecurityManager securityManager;
    private CompanyRepository companyRepository;
    private FeederRepository feederRepository;
    private MeasurementRepository measurementRepository;


    @GetMapping("/allowedCompanies")
    public List<ImmutableMap<String, String>> getAllowedCompanies() {
        List<Company> companies = companyAuthorizations();
        if(companies.size()==0) return new ArrayList<ImmutableMap<String, String>>(){};

        Iterator<Company> it = companies.iterator();
        return Stream.iterate(it.next(),c -> it.next())
                .limit(companies.size())
                .map(c -> ImmutableMap.of(
                        "id", c.getId().toString(),
                        "name",c.getName(),
                        "role", c.getRole()
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/allowedFeeders")
    public List<ImmutableMap<String, String>> getAllowedFeeders() {
        List<Feeder> feeders = allowedFeeders();
        if(feeders.size()==0) return new ArrayList<ImmutableMap<String, String>>(){};
        Iterator<Feeder> it = feeders.iterator();
        return Stream.iterate(it.next(), feeder -> it.next())
                .limit(feeders.size())
                .map(feeder -> ImmutableMap.of(
                        "id", feeder.getId().toString(),
                        "serial", feeder.getSerial().toString(),
                        "company_id",feeder.getCompanyId().toString(),
                        "desc", feeder.getDesc()
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/measurements")
    public List<ImmutableMap<String, String>> getMeasurements() {
        List<Measurement> measurements = measurementsFromAllowedFeeders();
        if(measurements.size()==0) return new ArrayList<ImmutableMap<String, String>>(){};
        Iterator<Measurement> it = measurements.iterator();
        return Stream.iterate(it.next(), measurement -> it.next())
                .limit(measurements.size())
                .map(measurement -> ImmutableMap.of(
                        "feederSerial", feederRepository.findTopByIdEquals(measurement.getFeederId()).get().getSerial().toString(),
                        "time", measurement.getTime().toString(),
                        "value", measurement.getValue().toString()
                ))
                .collect(Collectors.toList());
    }

    private List<Company> companyAuthorizations() {
        List<Company> companies = new ArrayList<>();
        List<String> roles = securityManager.getRoles();
        Iterator<String> it = roles.iterator();
        Optional<Company> c;
        for (int i=0;i<roles.size();i++) {
            c = companyRepository.findTopByRoleEquals(it.next());
            if(c.isPresent()) companies.add(c.get());
        }
        return companies;
    }

    private List<Feeder> allowedFeeders() {
        List<Feeder> feeders = new ArrayList<>();
        List<Company> companies = companyAuthorizations();
        Iterator<Company> itc= companies.iterator();
        for (int i=0;i<companies.size();i++)
            feeders.addAll(feederRepository.findAllByCompanyIdEquals(itc.next().getId()));
        return feeders;
    }

    private List<Measurement> measurementsFromAllowedFeeders() {
        List<Feeder> feeders = allowedFeeders();
        Iterator<Feeder> it = feeders.iterator();
        List<Measurement> m = new ArrayList<>();
        for (int i=0;i<feeders.size();i++)
            m.addAll(measurementRepository.findAllByFeederIdEquals(it.next().getId()));
        return m;
    }
}
