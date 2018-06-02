package edu.pw.hwmonitor.controllers;

import edu.pw.hwmonitor.companies.Company;
import edu.pw.hwmonitor.companies.CompanyRepository;
import edu.pw.hwmonitor.companies.role.Role;
import edu.pw.hwmonitor.companies.role.RoleRepository;
import edu.pw.hwmonitor.controllers.requests.CompanyCreateRequest;
import edu.pw.hwmonitor.user.User;
import edu.pw.hwmonitor.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    private CompanyRepository companyRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public AdminController(CompanyRepository companyRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/admin/create-company")
    public ResponseEntity<HttpStatus> createCompany(@RequestBody CompanyCreateRequest companyCreateRequest) {
        if (companyRepository.findByNameEquals(companyCreateRequest.getName()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        if (userRepository.findByUsernameEquals(companyCreateRequest.getUsername()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        Company company = new Company();
        company.setName(companyCreateRequest.getName());
        company.setRole("ROLE_" + companyCreateRequest.getName());
        companyRepository.save(company);

        User user = new User();
        user.setUsername(companyCreateRequest.getUsername());
        user.setPassword(companyCreateRequest.getPassword());
        user.setEnabled(true);
        userRepository.save(user);

        Role companyRole = new Role();
        companyRole.setRole(company.getRole());
        companyRole.setUsername(user.getUsername());
        roleRepository.save(companyRole);

        Role userRole = new Role();
        userRole.setRole("ROLE_USER");
        userRole.setUsername(user.getUsername());
        roleRepository.save(userRole);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
