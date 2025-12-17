package com.mono.fruit_box_service.auth.controller;

import com.mono.fruit_box_service.auth.dto.GoogleAuthRequest;
import com.mono.fruit_box_service.auth.dto.GoogleIdPayload;
import com.mono.fruit_box_service.auth.security.JwtService;
import com.mono.fruit_box_service.auth.security.OAuthStateService;
import com.mono.fruit_box_service.auth.service.GoogleOAuthService;
import com.mono.fruit_box_service.account.model.Account;
import com.mono.fruit_box_service.account.service.AccountService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Value("${google.client-id}")
    private String clientId;

    @Value("${google.redirect-uri}")
    private String redirectUri;

    private final GoogleOAuthService googleOAuthService;
    private final AccountService accountService;
    private final OAuthStateService oauthStateService;
    private final JwtService jwtService;

    @PostMapping("/google")
    public ResponseEntity<Void> googleLogin(
            @RequestParam String code,
            @RequestParam String state,
            HttpServletResponse response) {

        oauthStateService.validate(state);

        GoogleIdPayload payload = googleOAuthService.exchangeCodeForUser(code);

        Account account = accountService.findOrCreateFromGoogle(
                payload.email(),
                payload.sub()
        );

        String jwt = jwtService.generateToken(account.getId(), account.getRole().name());

        ResponseCookie cookie = ResponseCookie.from("ACCESS_TOKEN", jwt)
                .httpOnly(true)
                .secure(false) //todo false only for local
                .sameSite("Lax")
                .path("/")
                .maxAge(900)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/google/url",  produces = "text/plain")
    public ResponseEntity<String> getGoogleUrl() {

        String state = oauthStateService.generate();

        String url = "https://accounts.google.com/o/oauth2/v2/auth" +
                "?client_id=" + clientId +
                "&response_type=code" +
                "&scope=openid%20email%20profile" +
                "&redirect_uri=" + redirectUri +
                "&state=" + state;

        return ResponseEntity.ok(url);
    }


}
