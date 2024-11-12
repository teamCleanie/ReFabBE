package cleanie.repatch.user.controller;

import cleanie.repatch.user.model.request.OAuthLoginRequest;
import cleanie.repatch.user.model.response.AuthResponse;
import cleanie.repatch.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/oauth/login")
    public ResponseEntity<AuthResponse> oauthLogin(@Valid @RequestBody OAuthLoginRequest request) {
        AuthResponse response = authService.loginOAuth(request);
        return ResponseEntity.ok(response);
    }
}
