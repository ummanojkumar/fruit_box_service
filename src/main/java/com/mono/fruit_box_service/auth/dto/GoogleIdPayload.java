package com.mono.fruit_box_service.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GoogleIdPayload(
        String sub,
        String email,
        boolean email_verified,
        String aud,
        String iss,
        long exp
) {}

