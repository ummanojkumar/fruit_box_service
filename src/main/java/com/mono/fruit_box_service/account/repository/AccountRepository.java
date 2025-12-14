package com.mono.fruit_box_service.account.repository;

import com.mono.fruit_box_service.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findByGoogleId(String googleId);

    Optional<Account> findByEmail(String email);
}


