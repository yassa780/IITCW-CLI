package com.example.IITCW.Service;

import com.example.IITCW.Entities.User;
import com.example.IITCW.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    public String authenticate(String username, String password) {
        User user = userRepository.findByUserName(username);

        if (user == null || !user.getPassword().equals(password)) {
            return "Invalid username or password";
        }

        return "Login sccessful as " + user.getRole();
    }

}
