package com.mono.fruit_box_service.account.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @GetMapping("/users/me")
    public Object me(Authentication authentication) {
        return authentication;
    }
}
