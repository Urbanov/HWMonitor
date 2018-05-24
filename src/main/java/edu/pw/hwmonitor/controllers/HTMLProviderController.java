package edu.pw.hwmonitor.controllers;

import edu.pw.hwmonitor.companies.CompanyRepository;
import edu.pw.hwmonitor.feeders.FeederRepository;
import edu.pw.hwmonitor.measurements.MeasurementRepository;
import edu.pw.hwmonitor.security.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HTMLProviderController {
    
    @Autowired
    HTMLProviderController(SecurityManager securityManager, CompanyRepository companyRepository, FeederRepository feederRepository, MeasurementRepository measurementRepository) {
        this.securityManager=securityManager;
        this.companyRepository=companyRepository;
        this.feederRepository=feederRepository;
        this.measurementRepository=measurementRepository;
    }

    private SecurityManager securityManager;
    private CompanyRepository companyRepository;
    private FeederRepository feederRepository;
    private MeasurementRepository measurementRepository;

    @RequestMapping("/")
    public String root() {
        return "redirect:/index";
    }



    @RequestMapping("/index")
    public String index(Model model) {
        if(securityManager.hasRole("ROLE_COMPANY123"))
                model.addAttribute("msg", ">> Secure message only for users with role COMPANY123<<");
        model.addAttribute("msg2", "You have roles (read in controller method): " + securityManager.getRolesAsString());
        return "index";
    }

    @RequestMapping("/user/index")
    public String userIndex() {
        return "user/index";
    }

    @RequestMapping("/login")
    public String login(Model m) {
        m.addAttribute("username",securityManager.getUsername());
        System.out.println(companyRepository.count());
        System.out.println(feederRepository.count());
        System.out.println(measurementRepository.count());
        System.out.println(measurementRepository.findFirstById((long)1).get().getValue());
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
    @RequestMapping("/accessDenied")
    public String e403() {
        return "403";
    }
}