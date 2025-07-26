package gift.config.kakao;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("kakao.api")
public record KakaoProperties(
        String clientId,
        String redirectUri
) {
}