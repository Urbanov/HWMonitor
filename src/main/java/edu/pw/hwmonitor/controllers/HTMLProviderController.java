package edu.pw.hwmonitor.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HTMLProviderController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "index";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "index";
    }

    @RequestMapping("/user/data")
    public String userData() {
        return "data";
    }

    @RequestMapping("/user/sources")
    public String userSources() {
        return "sources";
    }

    @RequestMapping("/user/settings")
    public String userSettings() {
        return "settings";
    }

    @RequestMapping("/admin/companies")
    public String adminCompanies() {
        return "companies";
    }

    @RequestMapping("/admin/settings")
    public String adminSettings() {
        return "settings";
    }

    @RequestMapping("/access-denied")
    public String error403() {
        return "403";
    }
}