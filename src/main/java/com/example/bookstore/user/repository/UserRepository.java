package com.example.bookstore.user.repository;

import com.example.bookstore.user.domain.User;
import com.example.bookstore.user.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    List<User> findAllByRole(UserRole role);
}
