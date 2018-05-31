package edu.pw.hwmonitor.controllers;

import edu.pw.hwmonitor.security.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class HTMLProviderController {

    @Autowired
    HTMLProviderController(SecurityManager securityManager) {
        this.securityManager=securityManager;
    }

    private SecurityManager securityManager;

    @RequestMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index() {
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