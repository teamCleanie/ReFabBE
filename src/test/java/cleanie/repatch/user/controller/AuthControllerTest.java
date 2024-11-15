package cleanie.repatch.user.controller;

import cleanie.repatch.common.security.component.AuthArgumentResolver;
import cleanie.repatch.common.security.component.JwtTokenProvider;
import cleanie.repatch.common.security.config.SecurityConfig;
import cleanie.repatch.common.security.model.JwtProperties;
import cleanie.repatch.setting.config.RestDocsConfiguration;
import cleanie.repatch.setting.config.RestDocsTestSupport;
import cleanie.repatch.user.model.OAuthProvider;
import cleanie.repatch.user.model.request.OAuthLoginRequest;
import cleanie.repatch.user.model.response.AuthResponse;
import cleanie.repatch.user.repository.UserRepository;
import cleanie.repatch.user.service.AuthService;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import({RestDocsConfiguration.class, SecurityConfig.class, JwtTokenProvider.class})
@AutoConfigureRestDocs
public class AuthControllerTest extends RestDocsTestSupport {

    @MockBean
    private AuthService authService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtProperties jwtProperties;

    @MockBean
    private AuthArgumentResolver authArgumentResolver;

    @DisplayName("OAuth 로그인 API")
    @Test
    void oauthLoginTest() throws Exception {
        OAuthLoginRequest request = new OAuthLoginRequest(OAuthProvider.KAKAO, "kakao-access-token");
        AuthResponse response = AuthResponse.success("new-access-token", "new-refresh-token");

        given(authService.loginOAuth(any(OAuthLoginRequest.class)))
                .willReturn(response);

        mockMvc.perform(post("/api/auth/oauth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(document("auth-oauth-login",
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("인증")
                                        .summary("OAuth 로그인")
                                        .description("카카오/네이버 OAuth 액세스 토큰으로 로그인을 처리합니다.")
                                        .requestFields(
                                                fieldWithPath("provider").type(JsonFieldType.STRING)
                                                        .description("OAuth 제공자 (KAKAO, NAVER)"),
                                                fieldWithPath("accessToken").type(JsonFieldType.STRING)
                                                        .description("OAuth 제공자로부터 받은 액세스 토큰")
                                        )
                                        .responseFields(
                                                fieldWithPath("status").type(JsonFieldType.STRING)
                                                        .description("로그인 상태 (SUCCESS, REQUIRES_SIGNUP)"),
                                                fieldWithPath("accessToken").type(JsonFieldType.STRING)
                                                        .description("JWT 액세스 토큰"),
                                                fieldWithPath("refreshToken").type(JsonFieldType.STRING)
                                                        .description("JWT 리프레시 토큰")
                                        )
                                        .responseHeaders(
                                                headerWithName("Authorization")
                                                        .description("Bearer + JWT 액세스 토큰"),
                                                headerWithName("Refresh-Token")
                                                        .description("JWT 리프레시 토큰")
                                        )
                                        .requestSchema(Schema.schema("OAuthLoginRequest"))
                                        .responseSchema(Schema.schema("AuthResponse"))
                                        .build()
                        )));
    }
}
