package cleanie.repatch.user.controller;

import cleanie.repatch.common.security.annotation.Auth;
import cleanie.repatch.common.security.annotation.MemberOnly;
import cleanie.repatch.common.security.domain.Accessor;
import cleanie.repatch.user.model.request.OAuthLoginRequest;
import cleanie.repatch.user.model.response.AuthResponse;
import cleanie.repatch.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/oauth/login")
    public ResponseEntity<AuthResponse> oauthLogin(@Valid @RequestBody OAuthLoginRequest request) {
        AuthResponse response = authService.loginOAuth(request);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + response.getAccessToken())
                .header("Refresh-Token", response.getRefreshToken())
                .body(response);
    }

    @GetMapping("/test")
    @MemberOnly
    public String test(@Auth Accessor accessor) {
        return "test";
    }
}
