package gift.service;

import gift.client.KakaoClient;
import gift.dto.kakao.KakaoTokenResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class KakaoAuthServiceTest {

    @Mock
    private KakaoClient kakaoClient;

    @InjectMocks
    private KakaoAuthService kakaoAuthService;

    @Test
    void getAccessToken_Success() {
        String authorizationCode = "test_authorization_code";
        String expectedToken = "test_access_token_12345";

        ReflectionTestUtils.setField(kakaoAuthService, "clientId", "test-client-id");
        ReflectionTestUtils.setField(kakaoAuthService, "redirectUri", "http://test.uri");

        KakaoTokenResponse mockResponse = new KakaoTokenResponse(
                "bearer",
                expectedToken,
                43199,
                "refresh_token_abcde",
                5184000,
                "talk_message"
        );

        given(kakaoClient.getKakaoToken(authorizationCode, "test-client-id", "http://test.uri"))
                .willReturn(mockResponse);

        String actualToken = kakaoAuthService.getAccessToken(authorizationCode);

        assertThat(actualToken).isEqualTo(expectedToken);
    }
}

