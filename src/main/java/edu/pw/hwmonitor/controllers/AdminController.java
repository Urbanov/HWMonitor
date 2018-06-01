package edu.pw.hwmonitor.controllers;

import edu.pw.hwmonitor.companies.Company;
import edu.pw.hwmonitor.companies.CompanyRepository;
import edu.pw.hwmonitor.companies.role.Role;
import edu.pw.hwmonitor.companies.role.RoleRepository;
import edu.pw.hwmonitor.feeders.Feeder;
import edu.pw.hwmonitor.feeders.FeederRepository;
import edu.pw.hwmonitor.measurements.MeasurementRepository;
import edu.pw.hwmonitor.security.SecurityManager;
import edu.pw.hwmonitor.user.User;
import edu.pw.hwmonitor.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AdminController {

    @Autowired
    public AdminController(SecurityManager securityManager,
                             CompanyRepository companyRepository,
                             FeederRepository feederRepository,
                             MeasurementRepository measurementRepository,
                             UserRepository userRepository,
                             RoleRepository roleRepository) {
        this.securityManager=securityManager;
        this.companyRepository=companyRepository;
        this.feederRepository=feederRepository;
        this.measurementRepository=measurementRepository;
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
    }

    private SecurityManager securityManager;
    private CompanyRepository companyRepository;
    private FeederRepository feederRepository;
    private MeasurementRepository measurementRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;


    @PostMapping("/admin/create-company")
    public ResponseEntity<HttpStatus> createCompany(@RequestBody CompanyCreateRequest companyCreateRequest) {
        if(companyCreateRequest.getName()==null || companyCreateRequest.getName().length()==0 ||
                companyCreateRequest.getUsername()==null || companyCreateRequest.getUsername().length()==0 ||
                companyCreateRequest.getPassword()==null || companyCreateRequest.getPassword().length()==0)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(companyRepository.findByNameEquals(companyCreateRequest.getName()).isPresent()) return new ResponseEntity<>(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);

        Company company = new Company();
        company.setName(companyCreateRequest.getName());
        company.setRole("ROLE_"+companyCreateRequest.getName());
        companyRepository.save(company);

        User user = new User();
        user.setUsername(companyCreateRequest.getUsername());
        user.setPassword(companyCreateRequest.getPassword());
        user.setEnabled(true);
        userRepository.save(user);

        Role role = new Role();
        role.setRole(company.getRole());
        role.setUsername(user.getUsername());
        roleRepository.save(role);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/admin/create-feeder")
    public ResponseEntity<HttpStatus> createFeeder(@RequestBody FeederCreateRequest feederCreateRequest) {
        if(feederCreateRequest.getCompanyName()==null || feederCreateRequest.getCompanyName().length()==0 ||
                feederCreateRequest.getSerial()==null ||
                feederCreateRequest.getSecret()==null || feederCreateRequest.getSecret().length()==0 ||
                feederCreateRequest.getDescription()==null || feederCreateRequest.getDescription().length()==0 )
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<Company> oc = companyRepository.findByNameEquals(feederCreateRequest.getCompanyName());
        if(!oc.isPresent()) return new ResponseEntity<>(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);

        Company company = oc.get();
        Feeder feeder = new Feeder();
        feeder.setSerial(feederCreateRequest.getSerial());
        feeder.setCompanyId(company.getId());
        feeder.setDesc(feederCreateRequest.getDescription());
        feeder.setSecret(feederCreateRequest.getSecret());
        feederRepository.save(feeder);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
