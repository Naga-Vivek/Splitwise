package com.scaler.splitwise.strategies.passwordhashing;

public interface PasswordHashingStrategy {
    String hash(String originalPassword);
}
