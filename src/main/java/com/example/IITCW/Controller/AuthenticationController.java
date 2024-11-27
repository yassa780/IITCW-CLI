package com.example.IITCW.Controller;

import com.example.IITCW.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    /*The Response Entity is used so I can set the HTTP status codes and I can sutmize the responses accordiing
    * to the way I need*/
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password){
        String result = authenticationService.authenticate(username, password);

        if (result.startsWith("Invalid")) {
            return ResponseEntity.status(401).body(result);
        }

        return ResponseEntity.ok(result);
    }


}
