package edu.pw.hwmonitor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class SecurityTestController {

    private SecurityManager securityManager = new SecurityManager();

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