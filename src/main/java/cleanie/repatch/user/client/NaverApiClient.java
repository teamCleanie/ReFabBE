package cleanie.repatch.user.client;

import cleanie.repatch.common.exception.InvalidTokenException;
import cleanie.repatch.common.exception.OAuthApiException;
import cleanie.repatch.user.model.OAuthProvider;
import cleanie.repatch.user.model.OAuthUserInfo;
import cleanie.repatch.user.model.request.OAuthLoginRequest;
import cleanie.repatch.user.model.response.NaverUserResponse;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static cleanie.repatch.common.exception.model.ExceptionCode.INVALID_OAUTH_TOKEN;
import static cleanie.repatch.common.exception.model.ExceptionCode.OAUTH_API_ERROR;

@Component
public class NaverApiClient implements OAuthApiClient{

    private static final String BEARER_TOKEN_PREFIX = "Bearer ";
    private final WebClient naverWebClient;


    public NaverApiClient(@Qualifier("naverWebClient") WebClient naverWebClient) {
        this.naverWebClient = naverWebClient;
    }

    @Override
    public OAuthProvider getProvider() {
        return OAuthProvider.NAVER;
    }

    @Override
    public OAuthUserInfo getUserInfo(OAuthLoginRequest request) {
        String accessToken = request.getAccessToken();

        try {
            NaverUserResponse response = naverWebClient.get()
                    .uri("/v1/nid/me")
                    .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN_PREFIX + accessToken)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, this::handleClientError)
                    .onStatus(HttpStatusCode::is5xxServerError, this::handleServerError)
                    .bodyToMono(NaverUserResponse.class)
                    .block();

            return convertToOAuthUserInfo(response);
        } catch (Exception e) {
            throw new OAuthApiException(OAUTH_API_ERROR, getProvider());
        }
    }

    private Mono<? extends Throwable> handleClientError(ClientResponse response) {
        if (response.statusCode() == HttpStatus.UNAUTHORIZED) {
            return Mono.error(new InvalidTokenException(INVALID_OAUTH_TOKEN));
        }
        return response.bodyToMono(String.class)
                .flatMap(error -> Mono.error(new OAuthApiException(OAUTH_API_ERROR, getProvider(), error)));
    }

    private Mono<? extends Throwable> handleServerError(ClientResponse response) {
        return response.bodyToMono(String.class)
                .flatMap(error -> Mono.error(new OAuthApiException(OAUTH_API_ERROR, getProvider(), error)));
    }

    private OAuthUserInfo convertToOAuthUserInfo(NaverUserResponse response) {
        return OAuthUserInfo.builder()
                .id(response.getId())
                .name(response.getName())
                .profileImageUrl(response.getProfileImage())
                .provider(OAuthProvider.NAVER)
                .build();
    }
}
