package com.mono.fruit_box_service.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mono.fruit_box_service.auth.dto.GoogleIdPayload;
import com.mono.fruit_box_service.auth.dto.GoogleTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Base64;

@Service
public class GoogleOAuthService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${google.client-id}")
    private String clientId;

    @Value("${google.client-secret}")
    private String clientSecret;

    @Value("${google.redirect-uri}")
    private String redirectUri;

    public GoogleIdPayload exchangeCodeForUser(String code) {

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", redirectUri);
        body.add("grant_type", "authorization_code");

        GoogleTokenResponse tokenResponse =
                restTemplate.postForObject(
                        "https://oauth2.googleapis.com/token",
                        body,
                        GoogleTokenResponse.class
                );

        return decodeAndValidate(tokenResponse.id_token());
    }

    private GoogleIdPayload decodeAndValidate(String idToken) {

        try {
            String payloadJson = new String(
                    Base64.getUrlDecoder().decode(idToken.split("\\.")[1])
            );

            ObjectMapper mapper = new ObjectMapper();
            GoogleIdPayload payload = mapper.readValue(payloadJson, GoogleIdPayload.class);

            if (!payload.aud().equals(clientId))
                throw new RuntimeException("Invalid audience");

            if (payload.exp() < Instant.now().getEpochSecond())
                throw new RuntimeException("Token expired");

            return payload;

        } catch (Exception e) {
            throw new RuntimeException("Invalid Google ID token");
        }
    }
}

