package cleanie.repatch.common.docs.example;

import cleanie.repatch.setting.annotation.IntegrationTest;
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloController.class)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
class HelloControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @IntegrationTest
    @Test
    void getHello() throws Exception {
        mockMvc.perform(get("/api/hello")
                        .param("name", "Test")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("hello-get",
                        resource(ResourceSnippetParameters.builder()
                                .tag("Hello API")
                                .summary("Hello API - GET")
                                .description("이름을 받아 인사말을 반환하는 API")
                                .queryParameters(
                                        parameterWithName("name").description("인사할 대상의 이름").optional()
                                )
                                .responseFields(
                                        fieldWithPath("message").description("인사말 메시지")
                                )
                                .responseSchema(Schema.schema("HelloResponse"))
                                .build())));
    }

    @Test
    void postHello() throws Exception {
        mockMvc.perform(post("/api/hello")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("hello-post",
                        resource(ResourceSnippetParameters.builder()
                                .tag("Hello API")
                                .summary("Hello API - POST")
                                .description("이름을 받아 인사말을 반환하는 API")
                                .requestFields(
                                        fieldWithPath("name").description("인사할 대상의 이름")
                                )
                                .requestSchema(Schema.schema("HelloRequest"))
                                .responseFields(
                                        fieldWithPath("message").description("인사말 메시지")
                                )
                                .responseSchema(Schema.schema("HelloResponse"))
                                .build())));
    }
}
