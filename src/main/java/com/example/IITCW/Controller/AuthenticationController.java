package com.example.IITCW.Controller;

import com.example.IITCW.Entities.User;
import com.example.IITCW.Repository.UserRepository;
import com.example.IITCW.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    /*The Response Entity is used so I can set the HTTP status codes and I can customize the responses accordiing
    * to the way I need*/
    public ResponseEntity<String> login(@RequestBody User loginRequest){
        String result = authenticationService.authenticate(loginRequest.getUserName(), loginRequest.getPassword());

        if (result.startsWith("Invalid")) {
            return ResponseEntity.status(401).body(result);
        }

        return ResponseEntity.ok(result);
    }
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userRepository.save(user); //Saves the user to the database
        return ResponseEntity.status(201).body("User registered successfully");
    }

    @GetMapping("/users")
    public List<User>getAllUsers() {
        return userRepository.findAll();
    }


}
