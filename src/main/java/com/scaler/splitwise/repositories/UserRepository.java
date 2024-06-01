package com.scaler.splitwise.repositories;

import com.scaler.splitwise.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User save(User user);

    @Override
    Optional<User> findById(Long userId);
    User findByName(String name);
}
