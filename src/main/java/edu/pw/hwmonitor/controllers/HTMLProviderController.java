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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
        model.addAttribute("msg", ">> Message for all users<<");
        model.addAttribute("msg2", "You have roles (read in controller method): " + securityManager.getRolesAsString());
        return "index";
    }

    @RequestMapping("/user/index")
    public String userIndex(Model model) {
        model.addAttribute("content", "123");
        return "user/index";
    }

    @RequestMapping("/login")
    public String login(Model m) {
        m.addAttribute("username",securityManager.getUsername());
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