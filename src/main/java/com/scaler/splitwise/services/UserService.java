package com.scaler.splitwise.services;

import com.scaler.splitwise.models.User;
import com.scaler.splitwise.repositories.UserRepository;
import com.scaler.splitwise.strategies.passwordhashing.PasswordHashingStrategy;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordHashingStrategy passwordHashingStrategy;

    public UserService(UserRepository userRepository, PasswordHashingStrategy passwordHashingStrategy) {
        this.userRepository = userRepository;
        this.passwordHashingStrategy = passwordHashingStrategy;
    }

    public User registerUser(String name , String phoneNumber , String password){
        String hashedPassword = passwordHashingStrategy.hash(password);
        User user = new User();
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setHashedPassword(hashedPassword);

        User savedUser = userRepository.save(user);
        return  savedUser;
    }
}
