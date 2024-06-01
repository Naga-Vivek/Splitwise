package com.scaler.splitwise.strategies.passwordhashing;

import org.springframework.stereotype.Service;

@Service
public class DummyPasswordHashingStrategy implements PasswordHashingStrategy{
    @Override
    public String hash(String originalPassword) {
        return originalPassword+"dummy";
    }
}
