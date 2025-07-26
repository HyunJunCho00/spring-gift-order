package gift.service;

import gift.config.kakao.KakaoProperties;
import gift.dto.kakao.KakaoTokenResponse;
import gift.exception.KakaoAuthenticationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class KakaoAuthService {

    private final KakaoProperties kakaoProperties;
    private final RestTemplate restTemplate;

    public KakaoAuthService(KakaoProperties kakaoProperties, RestTemplate restTemplate) {
        this.kakaoProperties = kakaoProperties;
        this.restTemplate = restTemplate;
    }

    public String getAccessToken(String authorizationCode) {
        String url = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoProperties.clientId());
        body.add("redirect_uri", kakaoProperties.redirectUri());
        body.add("code", authorizationCode);

        RequestEntity<MultiValueMap<String, String>> request = new RequestEntity<>(body, headers, HttpMethod.POST, URI.create(url));

        KakaoTokenResponse response = restTemplate.exchange(request, KakaoTokenResponse.class).getBody();

        if (response == null) {
            throw new KakaoAuthenticationException("카카오로부터 액세스 토큰을 받아오지 못했습니다.");
        }
        return response.accessToken();
    }
}