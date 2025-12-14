package com.mono.fruit_box_service.auth.security;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OAuthStateService {

    private final Set<String> states = ConcurrentHashMap.newKeySet();

    public String generate() {
        String state = UUID.randomUUID().toString();
        states.add(state);
        return state;
    }

    public void validate(String state) {
        if (!states.remove(state)) {
            throw new RuntimeException("Invalid OAuth state");
        }
    }
}

