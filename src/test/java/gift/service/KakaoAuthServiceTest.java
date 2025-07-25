package gift.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.config.kakao.KakaoProperties;
import gift.dto.kakao.KakaoTokenResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(KakaoAuthService.class)
@EnableConfigurationProperties(KakaoProperties.class)
@ActiveProfiles("test")
public class KakaoAuthServiceTest {

    @TestConfiguration
    static class Config {
        @Bean
        public RestTemplate restTemplate(RestTemplateBuilder builder) {
            return builder.build();
        }
    }

    @Autowired
    private KakaoAuthService kakaoAuthService;

    @Autowired
    private MockRestServiceServer mockServer;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAccessToken_Success() throws Exception {
        String expectedToken = "test_access_token_12345";
        KakaoTokenResponse mockResponse = new KakaoTokenResponse(
                "bearer",
                expectedToken,
                43199,
                "refresh_token_abcde",
                5184000,
                "talk_message"
        );
        String mockJsonResponse = objectMapper.writeValueAsString(mockResponse);

        mockServer.expect(requestTo("https://kauth.kakao.com/oauth/token"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(mockJsonResponse, MediaType.APPLICATION_JSON));

        String accessToken = kakaoAuthService.getAccessToken("test_authorization_code");

        assertThat(accessToken).isEqualTo(expectedToken);
        mockServer.verify();
    }
}
