package com.scaler.splitwise.strategies.passwordhashing;

import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Primary
public class BCryptPasswordHashingStrategy implements PasswordHashingStrategy{
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public BCryptPasswordHashingStrategy(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public String hash(String originalPassword) {
        return bCryptPasswordEncoder.encode(originalPassword);
    }
}
