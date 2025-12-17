package com.mono.fruit_box_service.auth.dto;


public record GoogleAuthRequest(
        String code,
        String state
) {}

