package com.mono.fruit_box_service.auth.dto;

public record GoogleIdPayload(
        String sub,
        String email,
        boolean email_verified,
        String aud,
        String iss,
        long exp
) {}

