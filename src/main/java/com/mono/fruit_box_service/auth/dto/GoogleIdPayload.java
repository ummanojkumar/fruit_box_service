package com.mono.fruit_box_service.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GoogleIdPayload(

        // Issuer
        String iss,

        // Authorized party (client_id that requested token)
        String azp,

        // Audience (your client_id)
        String aud,

        // Google unique user ID (PRIMARY IDENTIFIER)
        String sub,

        // Access token hash (used only for validation)
        @JsonProperty("at_hash")
        String atHash,

        // Hosted domain (Google Workspace only)
        String hd,

        // Email
        String email,

        // Name
        String name,

        // profile picture
        String picture,

        // Email verified
        @JsonProperty("email_verified")
        boolean emailVerified,

        // Issued at (epoch seconds)
        long iat,

        // Expiry (epoch seconds)
        long exp,

        //Nonce (used to prevent replay attacks)
        String nonce

) {}
