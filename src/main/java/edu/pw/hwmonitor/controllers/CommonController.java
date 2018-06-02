package edu.pw.hwmonitor.controllers;

import edu.pw.hwmonitor.controllers.requests.PasswordUpdateRequest;
import edu.pw.hwmonitor.security.SecurityManager;
import edu.pw.hwmonitor.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {
    private UserRepository userRepository;
    private SecurityManager securityManager;

    @Autowired
    public CommonController(UserRepository userRepository, SecurityManager securityManager) {
        this.userRepository = userRepository;
        this.securityManager = securityManager;
    }

    @PostMapping("/common/update-password")
    public ResponseEntity<HttpStatus> updatePassword(@RequestBody PasswordUpdateRequest passwordUpdateRequest) {
        String username = securityManager.getUsername();

        // TODO update password

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
