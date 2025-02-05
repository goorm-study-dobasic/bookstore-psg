package com.example.bookstore.blacklist.repository;

import com.example.bookstore.blacklist.domain.Blacklist;
import com.example.bookstore.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {
    List<Blacklist> findAllByUnleashedAtIsNotNull();

    Blacklist findByUserAndUnleashedAtIsNull(User user);
}
