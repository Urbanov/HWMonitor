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
        return "user/data";
    }

    @RequestMapping("/user/settings")
    public String userSettings() {
        return "user/settings";
    }

    @RequestMapping("/admin/panel")
    public String adminPanel() {
        return "admin/panel";
    }

    @RequestMapping("/accessDenied")
    public String e403() {
        return "403";
    }
}