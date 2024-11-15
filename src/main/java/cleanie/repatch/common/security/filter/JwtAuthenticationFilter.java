package cleanie.repatch.common.security.filter;

import cleanie.repatch.common.security.component.JwtTokenProvider;
import cleanie.repatch.user.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@NonNullApi
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String REFRESH_TOKEN_HEADER = "Refresh-Token";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String accessToken = resolveToken(request);

        try {
            if (StringUtils.hasText(accessToken)) {
                if (jwtTokenProvider.isValidToken(accessToken)) {
                    processValidToken(accessToken);
                } else {
                    processTokenRenewal(request, response);
                }
            }
        } catch (Exception e) {
            handleTokenException(e);
        }

        filterChain.doFilter(request, response);
    }

    private void processValidToken(String accessToken) {
        Authentication auth = jwtTokenProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private void processTokenRenewal(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = request.getHeader(REFRESH_TOKEN_HEADER);
        if (!StringUtils.hasText(refreshToken)) {
            return;
        }

        TokenRenewalResult renewalResult = renewTokens(refreshToken);
        response.setHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + renewalResult.accessToken());
        response.setHeader(REFRESH_TOKEN_HEADER, renewalResult.refreshToken());

        Authentication auth = jwtTokenProvider.getAuthentication(renewalResult.accessToken());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private void handleTokenException(Exception e) {
        SecurityContextHolder.clearContext();
        if (e instanceof ExpiredJwtException) {
            log.debug("Token has expired", e);
        } else {
            log.error("Could not process JWT token", e);
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return "";
    }

    private record TokenRenewalResult(String accessToken, String refreshToken) {}

    private TokenRenewalResult renewTokens(String refreshToken) {
        if (!jwtTokenProvider.isValidToken(refreshToken)) {
            return null;
        }

        return Objects.requireNonNull(userRepository.findByRefreshToken(refreshToken)
                .map(user -> {
                    String newAccessToken = jwtTokenProvider.createAccessToken(
                            user.getId(),
                            user.getUserType()
                    );

                    String newRefreshToken = refreshToken;
                    if (jwtTokenProvider.shouldRenewRefreshToken(refreshToken)) {
                        newRefreshToken = jwtTokenProvider.createRefreshToken();
                        user.updateRefreshToken(newRefreshToken);
                        userRepository.save(user);
                    }

                    return new TokenRenewalResult(newAccessToken, newRefreshToken);
                })
                .orElse(null));
    }
}
