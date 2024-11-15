package cleanie.repatch.user.controller;

import cleanie.repatch.user.model.request.OAuthLoginRequest;
import cleanie.repatch.user.model.response.AuthResponse;
import cleanie.repatch.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
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
}
