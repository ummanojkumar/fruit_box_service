package com.mono.fruit_box_service.account.service;

import com.mono.fruit_box_service.account.model.Account;
import com.mono.fruit_box_service.account.model.AccountRole;
import com.mono.fruit_box_service.account.model.AccountStatus;
import com.mono.fruit_box_service.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account findOrCreateFromGoogle(String email, String googleId) {

        return accountRepository.findByGoogleId(googleId)
                .map(account -> {
                    assertAccountIsActive(account);
                    account.setLastLogin(Instant.now());
                    return accountRepository.save(account);
                })
                .orElseGet(() -> {
                    Account account = new Account();
                    account.setEmail(email);
                    account.setGoogleId(googleId);
                    account.setStatus(AccountStatus.ACTIVE);
                    account.setRole(AccountRole.USER);
                    account.setCreatedAt(Instant.now());
                    account.setLastLogin(Instant.now());
                    return accountRepository.save(account);
                });
    }

    public void assertAccountIsActive(Account account) {
        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is blocked");
        }
    }
}

