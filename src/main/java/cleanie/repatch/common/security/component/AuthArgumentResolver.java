package cleanie.repatch.common.security.component;

import cleanie.repatch.common.security.annotation.Auth;
import cleanie.repatch.common.security.domain.Accessor;
import cleanie.repatch.user.repository.UserRepository;
import io.micrometer.common.lang.NonNullApi;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Auth.class) &&
                parameter.getParameterType().equals(Accessor.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = extractToken(webRequest);
        if (!isValidToken(token)) {
            return Accessor.guest();
        }
        Long userId = jwtTokenProvider.getUserIdFromToken(token);

        return userRepository.findById(userId)
                .map(user -> Accessor.member(user.getId(), user.getUserType()))
                .orElse(Accessor.guest());
    }

    private String extractToken(NativeWebRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return "";
    }

    private boolean isValidToken(String token) {
        return !token.isBlank() || jwtTokenProvider.validateToken(token);
    }

}
