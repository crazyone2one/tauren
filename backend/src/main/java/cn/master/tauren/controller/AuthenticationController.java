package cn.master.tauren.controller;

import cn.master.tauren.payload.request.AuthenticationRequest;
import cn.master.tauren.payload.request.RefreshTokenRequest;
import cn.master.tauren.payload.response.AuthenticationResponse;
import cn.master.tauren.security.JwtTokenProvider;
import cn.master.tauren.service.AuthenticationService;
import cn.master.tauren.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Created by 11's papa on 12/02/2024
 **/
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        if (jwtTokenProvider.isTokenExpired(refreshToken)) {
            return ResponseEntity.status(401).body("Refresh token expired");
        }
        return ResponseEntity.ok(refreshTokenService.generateNewToken(request));
    }
}
