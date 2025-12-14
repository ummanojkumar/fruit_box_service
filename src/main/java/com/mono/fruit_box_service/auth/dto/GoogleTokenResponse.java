package com.mono.fruit_box_service.auth.dto;

public record GoogleTokenResponse(
        String access_token,
        String id_token,
        int expires_in,
        String token_type
) {}
