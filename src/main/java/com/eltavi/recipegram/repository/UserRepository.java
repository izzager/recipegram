package com.eltavi.recipegram.repository;

import com.eltavi.recipegram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllBySubscriptions(User user);
    Optional<User> findUserByUsername(String username);
}
